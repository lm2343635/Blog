var config = null;
var admins;

$(document).ready(function() {
	checkAdminSession(function() {
		ConfigManager.getConfigObject(function(_config) {
			config = _config;

			for (var itemName in config) {
				var item = config[itemName];
		
				$("#config-list").mengular(".config-list-template", {
					itemName: itemName
				});
				
				for (var attributeName in item) {
					$("#" + itemName).mengular(".attribute-list-template", {
						attributeName: attributeName,
						attributeValue: item[attributeName]
					});
				}
			}
		});

		loadAdmins();
	});

	//刷新配置文件
	$("#refresh-config").click(function() {
		ConfigManager.loadConfig(function() {
			location.reload();
		});
	});

	//保存配置文件
	$("#save-config").click(function(event) {
		if (config == null) {
			return;
		}

		for (var itemName in config) {
			var item = config[itemName];
			for (var attributeName in item) {
				item[attributeName] = $("#" + attributeName + " input").val();
			}
		}

		ConfigManager.saveConfig(JSON.stringify(config), function(success) {
			if (success) {
				$.messager.popup("Save successfully！");
			} else {
				$.messager.popup("Save failed, please sign in later and try again!");
			}
		});
	});

	//新增管理员
	$("#add-admin-submit").click(function(event) {
		var username = $("#admin-username").val();
		var password = $("#admin-password").val();
		var exsit = false;
		for (var i in admins) {
			if (admins[i].username == username) {
				exsit = true;
				break;
			}
		}
		if (exsit || username == "" || username == null || password == "" || password == null) {
			$("#admin-username").parent().addClass("has-error");
			return;
		}
		$("#admin-username").parent().removeClass("has-error");

		AdminManager.addAdmin(username, password, function(success) {
			if (!success) {
				location.href = "sessionError.html";
				return;
			}
			loadAdmins();
		});
	});
});

/**
 * 加载所有管理员
 */
function loadAdmins() {
	AdminManager.getAdmins(function(_admins) {
		if (_admins == null) {
			location.href = "sessionError.html";
			return;
		}
		admins = _admins;
		$("#admin-list tbody").mengularClear();

		for (var i in admins) {
			$("#admin-list tbody").mengular(".admin-list-template", admins[i]);

			$("#" + admins[i].username + " .admin-list-remove").click(function(event) {
				if (admins.length == 1) {
					return;	
				}
				var username = $(this).mengularId();
				AdminManager.removeAdmin(username, function(success) {
					if (!success) {
						location.href = "sessionError.html";
						return;
					}
					$("#" + username).remove();
				});
			});
		}
	});
}