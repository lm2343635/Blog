var modifyingTid;

$(document).ready(function() {
	checkAdminSession(function() {
		loadTypes();
	});

	//新增博文类型
	$("#add-type-submit").click(function() {
		var tname=$("#add-type-tname").val();
		var validate=true;
		if(tname==null||tname=="") {
			$("#add-type-tname").parent().addClass("has-error");
			validate=false;
		} else {
			$("#add-type-tname").parent().removeClass("has-error");
		}
		if(validate) {
			TypeManager.addType(tname, function(tid){
				if(tid) {
					loadTypes();
					$("#add-type-modal").modal("hide");
				}
			});
		}
	});

	$("#add-type-modal").on("hidden.bs.modal", function() {
		$("#add-type-modal .input-group input").val("");
	});

	//提交修改博文类型名称
	$("#modify-type-submit").click(function() {
		var tname=$("#modify-type-tname").val();
		var validate=true;
		if(tname==null||tname=="") {
			$("#modify-type-tname").parent().addClass("has-error");
			validate=false;
		} else {
			$("#modify-type-tname").parent().removeClass("has-error");
		}
		if(validate) {
			TypeManager.modifyType(modifyingTid, tname, function() {
				$("#"+modifyingTid+" .type-list-tname").text(tname);
				$("#modify-type-modal").modal("hide");
				$.messager.popup("Type name modified.")
			});
		}
	});
});

function loadTypes() {
	TypeManager.getAll(function(types) {
		$("#type-list").mengularClear();
		for(var i in types) {
			$("#type-list").mengular(".type-list-template", {
				tid: types[i].tid,
				tname: types[i].tname,
				date: types[i].date.format(DATE_HOUR_MINUTE_FORMAT),
				count: types[i].count
			});

			//修改博文类型名称
			$("#"+types[i].tid+" .type-list-modify").click(function() {
				modifyingTid=$(this).parent().parent().parent().attr("id");
				var tname=$("#"+modifyingTid+" .type-list-tname").text();
				$("#modify-type-tname").val(tname);
				$("#modify-type-modal").modal("show");
			});

			//删除博文类型
			$("#"+types[i].tid+" .type-list-remove").click(function() {
				var tid=$(this).parent().parent().parent().attr("id");
				var tname=$("#"+tid+" .type-list-tname").text();
				$.messager.confirm("Tip", "confirm to delete type: "+tname+
					"? <br><strong class='text-danger'>All blogs of this type will be deleted!</span>", function() {
					TypeManager.removeType(tid, function() {
						$("#"+tid).remove();
						$.messager.popup(tname+" has been deleted!");
					});
				});
			});
		}
	});
}