/*单选美化*/
$(function(){
	$(".mc").css({"padding":"0","width":"90px","height":"35px","border":"1px solid #999",
	"border-radius":"5px","position":"relative","overflow":'hidden','cursor':'pointer','margin-left':'10px'})
	$(".control").css({"margin":"0","position":"absolute","right":"0","top":"0",
		"background":"#FFF","width":"40px","height":'35px','z-index':'10'})
	$(".on").css({'margin':'0','padding':'0','position':'absolute','left':'0','top':'0','background':'#0A0',
'width':'50px','height':'35px','z-index':'8','color':'#FFF','text-align':'center','line-height':'35px','font-size':'15px',
'-moz-user-select':'none','-webkit-user-select':'none','-ms-user-select':'none','-khtml-user-select':'none','user-select':'none'})
	$(".off").css({'margin':'0','padding':'0','position':'absolute','left':'90px','top':'0','background':'#B2B2B2',
		'width':'50px','height':'35px','z-index':'8','color':'#FFF','text-align':'center','line-height':'35px','font-size':'15px',
		'-moz-user-select':'none','-webkit-user-select':'none','-ms-user-select':'none','-khtml-user-select':'none','user-select':'none'})
})
window.onload=function(){
    var mc = document.getElementsByClassName("mc");
    for(var i=0;i<mc.length;i++){
    	myMc(mc[i]);
    }
}
function myMc(mc){
	var on = mc.getElementsByClassName("on")[0];
    var control = mc.getElementsByClassName("control")[0];
    var off = mc.getElementsByClassName("off")[0];
    var sysnchronized = false;
    var input = mc.getElementsByTagName("input")[0];
    if($(mc).find(".mcControl").val()=='1'){
    	 on.style.left = "0px";
         control.style.left = "50px";
         off.style.left ="90px"
    }else{
    	on.style.left = "-50px";
        control.style.left = "0px";
        off.style.left ="40px"
    }
    mc.addEventListener("click",function(){
    	//锁方法
        if(sysnchronized){
           return;
        }
        sysnchronized = true;
     if(on.offsetLeft==0){
         var timer = window.setInterval(function(){
             if(control.offsetLeft==0) {
            	 input.value=0;
                 window.clearInterval(timer);
                 sysnchronized = false;
                return;
             }
             on.style.left = on.offsetLeft-1+"px";
             control.style.left = control.offsetLeft-1+"px";
             off.style.left = off.offsetLeft-1+"px";
         },10);
     }else{
         var timer = window.setInterval(function(){
             if(on.offsetLeft==0) {
            	/* mc.getElementsByClassName("update.state")[0].setAttribute("checked","checked");*/
            	 input.value=1;
                 window.clearInterval(timer);
                 sysnchronized = false;
                 return;
             }
             on.style.left = on.offsetLeft+1+"px";
             control.style.left = control.offsetLeft+1+"px";
             off.style.left = off.offsetLeft+1+"px";
         },10);
     }
    })
}
	