$(document).ready(function() {
	checkAdminSession(function() {
		loadTypes();
	});

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
		}
	});
}