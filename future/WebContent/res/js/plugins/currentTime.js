var currentDate = new Date(time);
var weekArr = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
function run() {
	var week = new Date().getDay();
	currentDate.setSeconds(currentDate.getSeconds() + 1);
	var time = "";
	var year = currentDate.getFullYear();
	var month = format(currentDate.getMonth() + 1);
	var day = format(currentDate.getDate());
	var hour = format(currentDate.getHours());
	var minute = format(currentDate.getMinutes());
	var second = format(currentDate.getSeconds());
	$("#currentTime").text(year+"-"+month+"-"+day+"	" +hour+':'+minute+':'+second+'	'+weekArr[week]);
}
function format(dateStr){
	if(dateStr<10){
		return '0'+dateStr;
	}
	return dateStr
}
window.setInterval("run();", 1000);