/**
 * Created by li.jing on 7/26 0026.
 */
var demo = document.getElementById("demo");
var demo1 = document.getElementById("demo1");
var demo2 = document.getElementById("demo2");
demo2.innerHTML=document.getElementById("demo1").innerHTML;
function Marquee(){
    if(demo.scrollLeft-demo2.offsetWidth>=0){
        demo.scrollLeft-=demo1.offsetWidth;
    }
    else{
        demo.scrollLeft++;
    }
}
var myvar=setInterval(Marquee,30);
demo.onmouseout=function (){myvar=setInterval(Marquee,30);};
demo.onmouseover=function(){clearInterval(myvar);};
