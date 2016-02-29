 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <script src="/js/ngGrid-core.js"></script>
 <script src="/js/jquery-1.10.2.min.js"></script>
 <script src="/js/dist/jstree.min.js"></script>
 <script src="/js/jquery-ui.min.js"></script>
 <script src="/js/jquery.cookie.js"></script>
 <script src="/js/hashMaps.js"></script>
 <link rel="stylesheet" href="/js/dist/themes/default/style.min.css" />
<script>
	grid.init({
		selectUrl : "/admin/board/listJson.do",
		deleteUrl : "/admin/board/delete.do", 
		method : "POST"
	});
</script>
<style>
  label {
    display: inline-block;
    width: 5em;
  }
  </style>
 <div>
	<h1>TREE <span id="mode"></span></h1>
	<div>추가, 삭제, 이름변경, 드래그</div>
	<div class="col-md-4 col-sm-8 col-xs-8">
	
		<button type="button" class="btn btn-info btn-sm" onclick="chane_manager();"><i class="glyphicon glyphicon-asterisk"></i>관리자/사용자 모드 변경</button>
	
		<button type="button" class="btn btn-success btn-sm" onclick="demo_create_root();" id="root_make"><i class="glyphicon glyphicon-asterisk"></i>Root 만들기</button>
		<button type="button" class="btn btn-danger btn-sm" onclick="demo_check_delete();" id="check_delete"><i class="glyphicon glyphicon-remove"></i>선택 삭제</button>
						<!-- <button type="button" class="btn btn-success btn-sm" onclick="demo_create();"><i class="glyphicon glyphicon-asterisk"></i> Create</button>
						<button type="button" class="btn btn-warning btn-sm" onclick="demo_rename();"><i class="glyphicon glyphicon-pencil"></i> Rename</button>
						<button type="button" class="btn btn-danger btn-sm" onclick="demo_delete();"><i class="glyphicon glyphicon-remove"></i> Delete</button>--> 
	
		<div>Search</div>
		<div>
			<input type="text" id="treeSearchWord">
		</div>
		
		<div id="showUrl">
			<div>선택된 node의 URL</div>
			<div>
				<input type="text" id="selectNodeUrl">
				<input type="button" value="변경" onclick="demo_changeUrl(this)">
			</div>
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
			$("#showUrl").hide();
			$("#root_make").hide();
			$("#check_delete").hide();
		}else{
			$.cookie('mode', 'admin', { expires: 7, path: '/', secure: false });
			$("#mode").html("관리자 모드");
			$("#showUrl").show();
			$("#root_make").show();
			$("#check_delete").show();
		}
		drawTree();
	});
	
	var tree =  $('#jstree_demo').jstree(true);
	var selectedNode = "";
	var copyncut = "";
	var copyncutNode = "";
	var copyncutParentNode = "";
	var copyncutPnode = "";
	
	//트리 그리기 및 트리에 해당 되는 바인드 이벤트 생성
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
		        if(data.event != undefined){
		        	 window.open(data.node.original.href);
		        }
		    });
		}else{
			$('#jstree_demo').jstree({
				"core" : {
					"animation" : 2,
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
				"plugins" : [ "contextmenu", "dnd", "search","checkbox"],
				"contextmenu": {items: context_menu}
			}).bind("move_node.jstree", function (event, data) {
		        treeControl("move",data);
		    }).bind("rename_node.jstree", function (event, data) {
		    	treeControl("renameC",data);
		    }).bind("dblclick.jstree", function (e, data) {
		    	demo_rename($(e.target).closest("li").attr("id"));
		    }).on('changed.jstree', function(e,data) { 
		    	if(data.event != undefined){
		    		$("#selectNodeUrl").val(data.node.original.href);
		    		selectedNode = data.node.id;
		        }
		    });
		}
		
		tree =  $('#jstree_demo').jstree(true);
	};
	
	//트리 검색 
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
	
	//트리 메뉴 생성
	function context_menu(node){
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
                	demo_rename(obj.reference.attr("id"));
                }
            },                         
            "Remove": {
                "separator_before": false,
                "separator_after": false,
                "label": "Remove",
                "action": function (obj,data) {
                  	demo_delete(obj.reference.attr("id"));
                }
            },
            "Etc": {
                "separator_before": false,
                "separator_after": false,
                "label": "Etc",
                "submenu" :  {
                	 "Copy": {
                         "separator_before": false,
                         "separator_after": false,
                         "label": "Copy",
                         "action": function (obj,data) {
                        	 console.log(data);
                        	 copyncut = "copy";
                        	 copyncutNode = obj.reference.attr("id");
                         }
                     },
                     "Paste": {
                         "separator_before": false,
                         "separator_after": false,
                         "label": "Paste",
                         "action": function (obj,data) {
                        	 
                       		copyncutParentNode = obj.reference.attr("id");
                       		if(copyncutParentNode == copyncutNode && copyncut == "cut"){
                       			return;
                       		}
                       		treeControl(copyncut,obj.reference.attr("id"));
                         }
                     },
                     "Cut": {
                         "separator_before": false,
                         "separator_after": false,
                         "label": "Cut",
                         "action": function (obj,data) {
							copyncut = "cut";
                        	copyncutNode = obj.reference.attr("id");
                        	copyncutPnode = $("#"+copyncutNode.replace("_anchor","")).parent().parent().attr("id");
                         }
                     },
                }
            }
        };
	    return items;
	}
	
	//모드 변경
	var chane_manager = function(){
		$("#jstree_demo").jstree("destroy");
		if($.cookie('mode') == undefined || $.cookie('mode') == "user"){
			$.cookie('mode', 'admin', { expires: 7, path: '/', secure: false });
			$("#mode").html("관리자 모드");
			$("#showUrl").show();
			$("#root_make").show();
			$("#check_delete").show();
		}else{
			$.cookie('mode', 'user', { expires: 7, path: '/', secure: false });
			$("#mode").html("사용자 모드");
			$("#showUrl").hide();
			$("#root_make").hide();
			$("#check_delete").hide();
		}
		
		drawTree();
	}
	
	//루트 생성
	var demo_create_root = function(){
		treeControl('create',"tree_0");
	};
	
	//노드 추가
	var demo_create = function(id) {
		var sel = id.replace("_anchor","");
		
		treeControl('create',sel);
	};
	
	//노드 이름 변경
	var demo_rename = function(id) {
		var sel = id.toString().replace("_anchor","");
		tree.edit(sel);
	};
	
	//노드 삭제
	var demo_delete = function(id) {
		var sel = id.replace("_anchor","");
		
		tree.delete_node(sel);
		var delKeys = [];
		delKeys[0] = sel;
		
		treeControl('remove',delKeys);
	};
	
	//노드 url 변경
	var demo_changeUrl = function(){
		treeControl('urlChange');
	}
	
	//체크박스 선택된 노드 삭제
	var demo_check_delete = function(){
		var msg = confirm("if children all checked that you may parents node delete.");
		if (msg == true) {
			var sel = tree.get_selected();
			tree.delete_node(sel);
			treeControl('remove',sel);
		}
	}
	
	//트리 컨트롤
	var treeControl = function(flag,data){
		var param = {
			type : flag
		};
		
		if(flag=='rename'){
			
		}else if(flag=='renameC' || flag=='move'){
			param["id"] = data.node.id;
			if(flag=='renameC'){
				param["parent_id"] = data.node.parent;
				param["old_text"] = data.old;
				param["new_text"] = data.text;
			}else{
				param["parent_id"] = data.parent;
				param["old_parent"] = data.old_parent;
				var orderId = "";
				var checkLevel = 0;
				
				if(data.parent == "#"){
					checkLevel = "1";
				}else{
					checkLevel = parseInt($("#"+data.parent).attr("aria-level"))+1
				}
				
				for(var i=0;i<$("#"+data.node.id).parent().find("li").length;i++){
					
					if(checkLevel.toString() == $("#"+data.node.id).parent().find("li").eq(i).attr("aria-level")){
						orderId += $("#"+data.node.id).parent().find("li").eq(i).attr("id")+",";
					}
					
				}
				
				orderId = orderId.substring(0,orderId.length-1);
				
				param["orderNum"] = orderId;
			}
			
		}else if(flag == "urlChange"){
			param["data"] = selectedNode;
			param["url"] = $("#selectNodeUrl").val();
		}else if(flag == "copy"){
			param["new_parent"] = data.replace("_anchor","");
			param["copy_seq"] = copyncutNode.replace("_anchor","");
		}else if(flag == "cut"){
			param["parent_id"] = data.replace("_anchor","");
			param["old_parent"] = copyncutNode.replace("_anchor","");
		}else{
			param["data"] = data;
		}
		
		jsonCommon(flag,param);
	};
	
	//json 통신
	var tmpSel = "";
	var jsonCommon = function(flag,param){
		$.ajax({
			type:"POST",
			url : "/ajax/treeControl.do",
			data: param,
			contentType: 'application/x-www-form-urlencoded; charset=utf-8',
			dataType : "json",
			success : function(data) {
				if(data.result == "ok"){
					var ref = $('#jstree_demo').jstree(true);
					if(data.type == "create"){
						
						if(data.root_make == true){
							ref.refresh(true);
						}else{
							tmpSel = tree.create_node(data.parent_id, {"type":"file"});
							ref.open_node(data.parent_id);
							ref.refresh_node(data.parent_id);
							setTimeout(function(){
								demo_rename("tree_"+data.seq);
							},500);
						}
					}else if(data.type == "urlChange"){
						setTimeout(function(){
							ref.refresh_node($("#"+selectedNode).parent().parent("li").attr("id"));
							setTimeout(function(){
								$("#jstree_demo").jstree("select_node",selectedNode);
							},500);
						},500);
					}else if(data.type == "cut" || data.type == "copy"){
						setTimeout(function(){
							ref.refresh_node($("#"+copyncutParentNode.replace("_anchor","")).attr("id"));
							
							ref.refresh_node(copyncutPnode);
							initCopynCut();
							
						},500);
					}
				}else{
					if(data.type == "cut"){
						alert("now node is parent");
						initCopynCut();
					}
				}
			},
			error : function(e) {
				console.log(e);
			}
		});
	};
	
	//copy, cut, paste가 끝난후 변수 초기화
	var initCopynCut = function(){
		copyncut = "";
		copyncutNode = "";
		copyncutParentNode = "";
		copyncutPnode = "";	
	};

	</script>
