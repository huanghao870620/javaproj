
function Emo (id) {
	if (typeof jQuery !== "function" || typeof $ !== "function") {
		throw new Error("jqery not found");
	}
	this.$content = $(document.getElementById(id));
};

Emo.prototype.init = function () {
	var _this = this;
	//禁止http://之类自动转换为链接
	document.execCommand("AutoUrlDetect", false, false);
	//粘贴
	_this.paste();
	//表情点击
	$(".emoji-field ul li img").click(function (e) {
		var status = false;				
		var notIE = false;
		var sel = null;
		var range = null;
		var url = this.getAttribute('src');
		var str = "";
		//检测insertHtml命令是否被支持
		notIE = document.queryCommandSupported("insertHtml");
		//获取选中区域
		sel = document.getSelection();
		//表情对象
		str = '<img ondragstart="return false" onresize="return false" src="'+url+'"/>';
		//获得range对象个数
		if (sel.rangeCount == 0) {
			console.log("setCaretEnd called..");
			_this.setCaretEnd();
		} 
		
		//内容为空时firefox会自动补<br>
		if (_this.$content[0].innerHTML == "<br>") {
			_this.$content[0].innerHTML = "";
		}
		
		if (notIE) {	//chrome,firefox
			status = document.execCommand('insertHTML', false, str);
			if (status) {
				_this.setCaretAfter();
			} else {
				_this.setCaretEnd();
				status = document.execCommand('insertHTML', false, str);
			}
		} else {	//ie
			status = document.execCommand('insertImage', false, url);
			if (status) {
			} else {
				_this.setCaretEnd();
				status = document.execCommand('insertImage', false, url);
			}
			_this.setCaretAfter();
		}
	});
};
//IE
//插入表情之后将光标定位到当前表情之后
Emo.prototype.setCaretAfter = function () {
	var obj = this.$content[0];
	var chdLen = obj.childNodes.length;
	var lastNode = obj.childNodes[chdLen-1];
	
	
	var sel = document.getSelection();
	var next = null;			
	var range = document.createRange();
	
	//直接插入图片、图片后插入图片
	if (sel.focusNode == obj) {
		range.setStart(obj, sel.focusOffset);
		range.setEnd(obj, sel.focusOffset);
		sel.removeAllRanges();
		sel.addRange(range);
		this.$content[0].focus();
	} else {				
		next = sel.anchorNode.nextSibling;
		
		//文字后插入图片
		if (next && next.nodeName == "IMG") {
			range.setStartAfter(next);
			range.setEndAfter(next);				
			
		//文字中插入图片
		} else if (sel.focusNode.nodeName == "P") {
			range.setStart(sel.focusNode, sel.focusNode.childNodes.length);
			range.setEnd(sel.focusNode, sel.focusNode.childNodes.length);
		
		//图片之间插入图片
		} else {
			range.setStartBefore(sel.focusNode);
			range.setEndBefore(sel.focusNode);		
		}	
		sel.removeAllRanges();
		sel.addRange(range);
		this.$content[0].focus();				
	}
};
//光标设置到末尾
Emo.prototype.setCaretEnd = function () {
	var obj = this.$content[0];
	var chdLen = obj.childNodes.length;
	var lastNode = obj.childNodes[chdLen-1];
	
	var range = document.createRange();
	
	if (chdLen == 0) {
		range.setStart(obj, 0);
		range.setEnd(obj, 0);
	} else {
		if (lastNode.nodeType == 3) {
			range.setStart(lastNode, lastNode.length);
			range.setEnd(lastNode, lastNode.length);
		} else {
			range.setStart(obj, chdLen);
			range.setEnd(obj, chdLen);
		}
	}
	
	var sel = window.getSelection();
	sel.removeAllRanges();
	sel.addRange(range);
	this.$content[0].focus();
};
//输入长度计算，表情按1个长度计算
Emo.prototype.getLen = function () {
	var obj = this.$content[0];
	var chdLen = obj.childNodes.length;
	var len = 0;
	for (var i = 0; i < chdLen; i++) {
		if (obj.childNodes[i].nodeType == 3) {
			len += obj.childNodes[i].length;
		}
		if (obj.childNodes[i].childNodes.length != 0 && obj.childNodes[i].childNodes[0].nodeType == 3) {
			len += obj.childNodes[i].childNodes[0].length;
		}
		if (obj.childNodes[i].nodeName == "IMG") {
			len += 1;
		}
	}
};
//获取修剪后的html
Emo.prototype.getTrimHtml = function () {
	var str = this.$content.html();
	if (str.match(/<div><br><\/div>$/)) {
		str = str.replace(/(<div><br><\/div>)+$/,"");
	} else if (str.match(/<p><br><\/p>$/)) {
		str = str.replace(/(<p><br><\/p>)+$/,"");
	}
	str = str.replace(/<br>$/,"");
	str = str.replace(/&nbsp;/ig,"");
	str = str.replace(/(^\s*)|(\s*$)/g, "");
	return str;
};
Emo.prototype.paste = function(){
	this.$content.on('paste', function(e) {
        e.preventDefault();
        var text = null;
    
        if(window.clipboardData && clipboardData.setData) {
            // IE
            text = window.clipboardData.getData('text');
        } else {
            text = (e.originalEvent || e).clipboardData.getData('text/plain') || prompt('在这里输入文本');
        }
        if (document.body.createTextRange) {    
            if (document.selection) {
                textRange = document.selection.createRange();
            } else if (window.getSelection) {
                sel = window.getSelection();
                var range = sel.getRangeAt(0);
                
                // 创建临时元素，使得TextRange可以移动到正确的位置
                var tempEl = document.createElement("span");
                tempEl.innerHTML = "&#FEFF;";
                range.deleteContents();
                range.insertNode(tempEl);
                textRange = document.body.createTextRange();
                textRange.moveToElementText(tempEl);
                tempEl.parentNode.removeChild(tempEl);
            }
            textRange.text = text;
            textRange.collapse(false);
            textRange.select();
        } else {
            // Chrome之类浏览器
            document.execCommand("insertText", false, text);
        }
    });
}



function send(){
	var topWin = window.top.document.getElementById("iframepage5").contentWindow;
	var emo = new Emo('writeMsg1');
	var msg =emo.getTrimHtml();
	if(!msg||msg.length<=0){
		alert("您输入的消息不合法,请重新输入!");
		$('#writeMsg1').empty().focus();
		return;
	}
	if(levelId=='0'){//游客级别发言控制
		if(topWin.document.getElementById("wait").value == "no"){
			waitNo();
		     return;
	    }else{
	    	waitOk();
	   }
	}
	var param = {ip : window.ip, msg : msg};
	var toStr = JSON.stringify(param);
	window.webSocket.send(toStr);
	$('#writeMsg1').empty();
	
}
//游客发言控制
var timeOut1;
var timeOut2;
var timer=8;

function waitNo(){
	$(window.frames['iframepage5'].document).find("#numberCount").html(timer);
	var topWin = window.top.document.getElementById("iframepage5").contentWindow;
	topWin.startCountDown();
	$(".online-10-wrap").show();
}
function waitOk(){
	window.clearTimeout(timeOut2);
	window.clearInterval(timeOut1);
	timer=8;
	var topWin = window.top.document.getElementById("iframepage5").contentWindow;
	topWin.document.getElementById("wait").value = "no";
	timeOut2=　 window.setTimeout(function(){ topWin.document.getElementById("wait").value = "ok"; }, 8000); 
    timeOut1=window.setInterval(function(){timer-- },1000);
}

