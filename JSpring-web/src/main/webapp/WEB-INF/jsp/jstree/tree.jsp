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
 <h1>HTML demo</h1>
	<h1>jstree_demo loading demo</h1>
	<div class="col-md-4 col-sm-8 col-xs-8">
						<button type="button" class="btn btn-success btn-sm" onclick="demo_create();"><i class="glyphicon glyphicon-asterisk"></i> Create</button>
						<button type="button" class="btn btn-warning btn-sm" onclick="demo_rename();"><i class="glyphicon glyphicon-pencil"></i> Rename</button>
						<button type="button" class="btn btn-danger btn-sm" onclick="demo_delete();"><i class="glyphicon glyphicon-remove"></i> Delete</button>
					</div>
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
	
	$('#jstree_demo').jstree({
		'core' : {
			'data' : {
				"animation" : 0,
				"check_callback" : true,
				'force_text' : true,
				"url" : "/ajax/listJson.do",
				"data" : function (node) {
					return { "id" : node.id };
				}
			}
		},
		"types" : {
			"#" : { "max_children" : 1, "max_depth" : 4, "valid_children" : ["root"] },
			"root" : { "icon" : "/static/3.2.1/assets/images/tree_icon.png", "valid_children" : ["default"] },
			"default" : { "valid_children" : ["default","file"] },
			"file" : { "icon" : "glyphicon glyphicon-file", "valid_children" : [] }
		},
		"plugins" : [ "contextmenu", "dnd", "search", "state", "types", "wholerow"],
		
		contextmenu: {items: context_menu}
	});
	
	function context_menu(node){
		var tree = $('#jstree_demo').jstree(true);
	 
		// The default set of all items
        return {
            "Create": {
                "separator_before": false,
                "separator_after": false,
                "label": "Create",
                "action": function (obj) { 
                	var ref = $('#jstree_demo').jstree(true),
        			sel = ref.get_selected();
	        		if(!sel.length) { return false; }
	        		sel = sel[0];
                    $node = tree.create_node(obj);
                    tree.edit($node);
                    
                    treeCreate();
                    
                }
            },
            "Rename": {
                "separator_before": false,
                "separator_after": false,
                "label": "Rename",
                "action": function (obj) { 
                    tree.edit(obj);
                }
            },                         
            "Remove": {
                "separator_before": false,
                "separator_after": false,
                "label": "Remove",
                "action": function (obj) { 
                    tree.delete_node(obj);
                }
            }
        };
	 
	 
	    return items;
	}
	
	var treeCreate = function(){
		jsonCommon();
	};
	
	var jsonCommon = function(){
		var param = "test=hi";
		
		$.ajax({
			type:"POST",
			url : "/ajax/treeControl.do",
			data: param,
			contentType: 'application/x-www-form-urlencoded; charset=utf-8',
			dataType : "json",
			success : function(data) {
				$('#jstree_demo').jstree(true).redraw(true);
			},
			error : function(e) {
				console.log(e);
			}
		});
	};

	</script>
