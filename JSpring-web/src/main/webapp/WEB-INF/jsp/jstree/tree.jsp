 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <script src="/js/ngGrid-core.js"></script>
 <script src="/js/jquery-1.10.2.min.js"></script>
 <script src="/js/dist/jstree.min.js"></script>
 <script src="/js/jquery.cookie.js"></script>
 <link rel="stylesheet" href="/js/dist/themes/default/style.min.css" />
<script>
	grid.init({
		selectUrl : "/admin/board/listJson.do",
		deleteUrl : "/admin/board/delete.do", 
		method : "POST"
	});
</script>
 <div>
	<h1>TREE <span id="mode"></span></h1>
	<div>추가, 삭제, 이름변경, 드래그</div>
	<div class="col-md-4 col-sm-8 col-xs-8">
	
		<button type="button" class="btn btn-success btn-sm" onclick="chane_manager();"><i class="glyphicon glyphicon-asterisk"></i>관리자/사용자 모드 변경</button>
	
		<button type="button" class="btn btn-success btn-sm" onclick="demo_create_root();"><i class="glyphicon glyphicon-asterisk"></i>Root 만들기</button>
		<button type="button" class="btn btn-danger btn-sm" onclick="demo_check_delete();"><i class="glyphicon glyphicon-remove"></i>선택 삭제</button>
						<!-- <button type="button" class="btn btn-success btn-sm" onclick="demo_create();"><i class="glyphicon glyphicon-asterisk"></i> Create</button>
						<button type="button" class="btn btn-warning btn-sm" onclick="demo_rename();"><i class="glyphicon glyphicon-pencil"></i> Rename</button>
						<button type="button" class="btn btn-danger btn-sm" onclick="demo_delete();"><i class="glyphicon glyphicon-remove"></i> Delete</button>--> 
	
		<div>Search</div>
		<div>
			<input type="text" id="treeSearchWord">
		</div>
	</div> 
	
	
	<div id="jstree_demo" class="demo"></div>
</div>

<script>
	$(document).ready(function() {
		$.cookie('mode');
		
		if($.cookie('mode') == undefined || $.cookie('mode') == "user"){
			$.cookie('mode', 'user', { expires: 7, path: '/', secure: false });
			$("#mode").html("사용자 모드");
		}else{
			$.cookie('mode', 'admin', { expires: 7, path: '/', secure: false });
			$("#mode").html("관리자 모드");
		}
		
		
		drawTree();
	});
	
	var tree =  $('#jstree_demo').jstree(true);
	
	var drawTree = function(){
		if($.cookie('mode') == "user"){
			$('#jstree_demo').jstree({
				"core" : {
					"animation" : 0,
					"check_callback" : true,
					'force_text' : true,
					"themes" : { "stripes" : true },
					'data' : {
						'url' : function (node) {
							return node.id === '#' ? '/ajax/treeList.do?node=tree_0' : '/ajax/treeList.do?node='+node.id;
						},
						//'url' :'/ajax/listJson.do',
						'data' : function (node) {
							return { 'id' : node.id };
						}
					}
				},
				"plugins" : ["search"],
				"contextmenu": {items: context_menu}
			}).on('changed.jstree', function(e,data) { 
		        //alert(1);
		        console.log(data);
		        if(data.event != undefined){
		        	 window.open(data.node.original.href);
		        }
		    });
		}else{
			$('#jstree_demo').jstree({
				"core" : {
					"animation" : 0,
					"check_callback" : true,
					'force_text' : true,
					"themes" : { "stripes" : true },
					'data' : {
						'url' : function (node) {
							return node.id === '#' ? '/ajax/treeList.do?node=tree_0' : '/ajax/treeList.do?node='+node.id;
						},
						//'url' :'/ajax/listJson.do',
						'data' : function (node) {
							return { 'id' : node.id };
						}
					}
				},
				"plugins" : [ "contextmenu", "dnd", "search", "state","checkbox"],
				"contextmenu": {items: context_menu}
			}).bind("move_node.jstree", function (event, data) {
		        treeControl("move",data);
		    }).bind("rename_node.jstree", function (event, data) {
		    	treeControl("renameC",data);
		    }).bind("dblclick.jstree", function (e, data) {
		    	demo_rename($(e.target).closest("li").attr("id"));
		    });
		}
		
		tree =  $('#jstree_demo').jstree(true);
	};
	
	var to = false;
	$('#treeSearchWord').keyup(function () {
		tree.open_all();
	    if(to) {
	    	clearTimeout(to); 
	    }
	    to = setTimeout(function () {
			var v = $('#treeSearchWord').val();
			$('#jstree_demo').jstree(true).search(v);
		}, 250);
	  });
	
	function context_menu(node){
	 	//tree = $('#jstree_demo').jstree(true);
        return {
            "Create": {
                "separator_before": false,
                "separator_after": false,
                "label": "Create",
                "action": function (obj) { 
                	demo_create(obj.reference.attr("id"));
                }
            },
            "Rename": {
                "separator_before": false,
                "separator_after": false,
                "label": "Rename",
                "action": function (obj) { 
                	//treeControl('rename');
                	demo_rename(obj.reference.attr("id"));
                }
            },                         
            "Remove": {
                "separator_before": false,
                "separator_after": false,
                "label": "Remove",
                "action": function (obj,data) {
                    //tree.delete_node(obj.reference.attr("id"));
                  	//treeControl('remove');
                  	demo_delete(obj.reference.attr("id"));
                }
            }
        };
	    return items;
	}
	
	var chane_manager = function(){
		$("#jstree_demo").jstree("destroy");
		if($.cookie('mode') == undefined || $.cookie('mode') == "user"){
			$.cookie('mode', 'admin', { expires: 7, path: '/', secure: false });
			$("#mode").html("관리자 모드");
		}else{
			$.cookie('mode', 'user', { expires: 7, path: '/', secure: false });
			$("#mode").html("사용자 모드");
		}
		
		drawTree();
	}
	
	var demo_create_root = function(){
		treeControl('create',"tree_0");
	};
	
	var demo_create = function(id) {
		var sel = id.replace("_anchor","");
		
		treeControl('create',sel);
	};
	
	var demo_rename = function(id) {
		var sel = id.toString().replace("_anchor","");
		tree.edit(sel);
	};
	
	var demo_delete = function(id) {
		var sel = id.replace("_anchor","");
		
		tree.delete_node(sel);
		treeControl('remove',sel);
	};
	
	var demo_check_delete = function(){
		var sel = tree.get_selected();
		console.log(sel);
	}
	
	var treeControl = function(flag,data){
		var param = {
				type : flag
		};
		
		if(flag=='rename'){
			//ref.edit(sel);		
		}else if(flag=='renameC' || flag=='move'){
			param["id"] = data.node.id;
			if(flag=='renameC'){
				param["parent_id"] = data.node.parent;
				param["old_text"] = data.old;
				param["new_text"] = data.text;
			}else{
				param["parent_id"] = data.parent;
				param["old_parent"] = data.old_parent;
			}
			jsonCommon(flag,param);
		}else{
			param["data"] = data;
			jsonCommon(flag,param);
		}
	};
	
	var tmpSel = "";
	var jsonCommon = function(flag,param){
		$.ajax({
			type:"POST",
			url : "/ajax/treeControl.do",
			data: param,
			contentType: 'application/x-www-form-urlencoded; charset=utf-8',
			dataType : "json",
			success : function(data) {
				if(data.type == "create"){
					tmpSel = tree.create_node(data.parent_id, {"type":"file"});
					
					var ref = $('#jstree_demo').jstree(true);
					ref.open_node(data.parent_id);
					$.jstree.reference('#jstree_demo').refresh();
					//$('#jstree_demo').jstree(true).redraw(true);
					setTimeout(function(){
						demo_rename("tree_"+data.seq);
					},500);
				}
			//	$.jstree.reference('#jstree_demo').refresh();
			},
			error : function(e) {
				console.log(e);
			}
		});
	};

	</script>
