/**
 * Created by uiokjnkj on 2016/5/13.
 */
$(function(){
    var _index=0;
    $(".grmx-tabs li").click(function(){
        _index=$(this).index();
        $(this).addClass("active").siblings().removeClass("active");
        $(".grmx-pannels .grmx-tab-con").eq(_index).show().siblings().hide();
    });
    $(".go_top").click(function(){
        $("body,html").animate({scrollTop:0},'300');
    })

})
