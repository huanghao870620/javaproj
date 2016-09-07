
<ul>   
    <li class="name"> ${message.author.account} </li>  
    <li class="time"> ${message.inputTime?string("yyyy-MM-dd HH:mm:ss")} </li>  
    <li class="state">
     <button type="button" onclick="auditMsg('${message.id}', this.parentNode.parentNode)" value="">ÉóºË
        </button>
        </li> 
    <li class="text"> ${message.msg} </li> 
</ul> 