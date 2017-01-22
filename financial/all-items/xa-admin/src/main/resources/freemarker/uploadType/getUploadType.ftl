

<#--
 <#list msgs as message>
 
  <li class="talk-li">	
  
   
  
	 <#if message.levelId gte 10>
	 
	    <#if message.cfileId?? >
	     <img src="${basePath}/download/file/show.htm?id=${message.cfileId?c}"
	     <#else>
	     <img src="${basePath}/download/file/show.htm?id=22"
	    </#if>
	 
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
	<p> 
	   
	   ${message.customerName!}
	   
	  <span>[ 
	    
	     <#if (message.inputTime)?? >
	     ${message.inputTime?string("yyyy-MM-dd HH:mm:ss")}
	       <#else>
	        
	     </#if>
	    ] </span></p>
	 <#else>
		<p> 
		 ${message.customerName!}
		
		 <span>[ 
		 
		 
		 <#if (message.inputTime)?? >
		 ${message.inputTime?string("yyyy-MM-dd HH:mm:ss")}
		   <#else>
		   youke
		 </#if>
		 
		 ]</span></p>
	</#if>
	
    </div>
    </div>
	<div class="li-text">
	<div class="box">
	<div class="detest"><i></i>
	<p
	<#if message.levelId == 10>
		  style="color: red; font-weight: bold; font-size: 16px;" 
	</#if>
	> ${message.msg} </p>
	</div>
	</div>
	</div>
	</li>
 
 </#list>

-->


<#list ups as up>
<option value="${up.id }">${up.name }</option>
</#list>
