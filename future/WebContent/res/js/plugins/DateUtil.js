var DateUtil = function(){
	this.formatDate=function (date,fmt) {
		if(date){
			var o = {
					"M+": formatDateStr(date.month+1), // 月份
					"d+": formatDateStr(date.date), // 日
					"h+": formatDateStr(date.hours), // 小时
					"m+": formatDateStr(date.minutes), // 分
					"s+": formatDateStr(date.seconds), // 秒
					"q+": Math.floor((date.month + 3) / 3), // 季度
					"S": date.time // 毫秒
			};
			if (/(y+)/.test(fmt)){
				fmt = fmt.replace(RegExp.$1, (date.year+1900 + "").substr(4 - RegExp.$1.length));
			}
			for (var k in o){
				if (new RegExp("(" + k + ")").test(fmt)){
					fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
				}
			}
		}else{
			fmt = '';
		}
	    return fmt;
	}
	// 日期格式化
	var formatDateStr = function(dateStr){
		if(dateStr<10){
			return '0'+dateStr;
		}
		return dateStr
	}
}