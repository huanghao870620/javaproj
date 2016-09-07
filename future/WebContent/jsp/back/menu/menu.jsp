<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><script type="text/javascript">
$(function(){	 	$("#red").treeview({		animated: "fast",		collapsed: true,		unique: true,		persist: "cookie",		toggle: function() {			window.console && console.log("%o was toggled", this);		}	});		});
</script>${sessionScope.menuHtml}