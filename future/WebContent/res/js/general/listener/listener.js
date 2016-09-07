

/**
 * 监听者
 * @param o
 * @returns
 */
Listener = function(o){
	this.url = o.url;
	this.node = o.node;
	this.deal = o.deal;
}



// 监听
Listener.prototype.listen = function(){
	 var liste = this;
	// liste.deal();

	 setInterval(function(){
		 liste.deal();
	 },1000); 
}
