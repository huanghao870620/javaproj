
<li class="talk-li">	
	 <#if message.levelId gte 10>
	 <img src="${basePath}/download/file/show.htm?id=${message.author.CFileId?c}"
	 <#else>
	 <img src="${basePath}/res/images/level${message.levelId}.png"
	 </#if>
	
	<#if message.levelId == 10>
	  style="width:40px;height:40px;"
	</#if>
	
	 class="user-level" />
	 <div class="li-tit"> 
	<div class="li-tit-name"> 
	
	<#if message.levelId gte 10>
	<p> ${message.author.name} <span>[ ${message.inputTime?string("yyyy-MM-dd HH:mm:ss")}] </span></p>
	 <#else>
		<p> ${message.customerAccount!} <span>[ ${message.inputTime?string("yyyy-MM-dd HH:mm:ss")}]</span></p>
	</#if>
	
	
	
    </div>
    </div>
	<div class="li-text">
	<div class="box">
	<div class="detest"><i></i>
	<p
	<#if message.levelId == 10>
		  style="color: red;font-weight: bold;font-size: 16px;" 
	</#if>
	> ${message.msg} </p>
	</div>
	</div>
	</div>
	</li>
