 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <script src="/js/ngGrid-core.js"></script>
 <script src="/js/jquery-1.10.2.min.js"></script>
 <script src="/js/dist/jstree.min.js"></script>
 <link rel="stylesheet" href="/js/dist/themes/default/style.min.css" />
<script>
	grid.init({
		selectUrl : "/admin/board/listJson.do",
		deleteUrl : "/admin/board/delete.do", 
		method : "POST"
	});
</script>
 <div>
	<h1>TREE</h1>
	 <!-- <div class="col-md-4 col-sm-8 col-xs-8">
						<button type="button" class="btn btn-success btn-sm" onclick="demo_create();"><i class="glyphicon glyphicon-asterisk"></i> Create</button>
						<button type="button" class="btn btn-warning btn-sm" onclick="demo_rename();"><i class="glyphicon glyphicon-pencil"></i> Rename</button>
						<button type="button" class="btn btn-danger btn-sm" onclick="demo_delete();"><i class="glyphicon glyphicon-remove"></i> Delete</button>
					</div> --> 
	<div>추가, 삭제, 이름변경, 드래그</div>
	<div id="jstree_demo" class="demo"></div>
</div>

<script>
	

	// jstree_demo demo
	function demo_create() {
		var ref = $('#jstree_demo').jstree(true),
			sel = ref.get_selected();
		if(!sel.length) { return false; }
		sel = sel[0];
		sel = ref.create_node(sel, {"type":"file"});
		if(sel) {
			ref.edit(sel);
		}
	};
	function demo_rename() {
		var ref = $('#jstree_demo').jstree(true),
			sel = ref.get_selected();
		if(!sel.length) { return false; }
		sel = sel[0];
		ref.edit(sel);
	};
	function demo_delete() {
		var ref = $('#jstree_demo').jstree(true),
			sel = ref.get_selected();
		if(!sel.length) { return false; }
		ref.delete_node(sel);
	};
	
	$('#jstree_demo')
	.jstree({
		"core" : {
			"animation" : 0,
			"check_callback" : true,
			'force_text' : true,
			"themes" : { "stripes" : true },
			'data' : {
				'url' : function (node) {
					return node.id === '#' ? '/ajax/listJson.do?node=tree_0' : '/ajax/listJson.do?node='+node.id;
				},
				//'url' :'/ajax/listJson.do',
				'data' : function (node) {
					return { 'id' : node.id };
				}
			}
		},
		"types" : {
			"#" : { "max_children" : 1, "max_depth" : 4, "valid_children" : ["root"] },
			"root" : { "icon" : "/static/3.2.1/assets/images/tree_icon.png", "valid_children" : ["default"] },
			"default" : { "valid_children" : ["default","file"] },
			"file" : { "icon" : "glyphicon glyphicon-file", "valid_children" : [] }
		},
		"plugins" : [ "contextmenu", "dnd", "search", "state", "types", "wholerow" ],
		"contextmenu": {items: context_menu}
	}).bind("move_node.jstree", function (event, data) {
        treeControl("move",data);
    }).bind("rename_node.jstree", function (event, data) {
    	treeControl("renameC",data);
    }.bind("remove_node.jstree", function (event, data) {
    	console.log(data)
    }));
	
	function context_menu(node){
		var tree = $('#jstree_demo').jstree(true);
		
        return {
            "Create": {
                "separator_before": false,
                "separator_after": false,
                "label": "Create",
                "action": function (obj) { 
                    treeControl('create');
                }
            },
            "Rename": {
                "separator_before": false,
                "separator_after": false,
                "label": "Rename",
                "action": function (obj) { 
                	treeControl('rename');
                }
            },                         
            "Remove": {
                "separator_before": false,
                "separator_after": false,
                "label": "Remove",
                "action": function (obj,data) { 
                    tree.delete_node(obj);
                  	treeControl('remove');
                }
            }
        };
	    return items;
	}
	
	var treeControl = function(flag,data){
		
		var ref = $('#jstree_demo').jstree(true),
		sel = ref.get_selected();
		if(!sel.length) { 
			return false; 
		}
		sel = sel[0];
		
		var param = {
			type : flag
		};
		
		if(flag=='rename'){
			ref.edit(sel);		
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
			param["data"] = sel;
			jsonCommon(flag,param);
		}
	};
	
	var jsonCommon = function(flag,param){
		console.log(param);
		$.ajax({
			type:"POST",
			url : "/ajax/treeControl.do",
			data: param,
			contentType: 'application/x-www-form-urlencoded; charset=utf-8',
			dataType : "json",
			success : function(data) {
				$.jstree.reference('#jstree_demo').refresh();
			},
			error : function(e) {
				console.log(e);
			}
		});
	};

	</script>
