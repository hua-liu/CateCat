/**
 * Created by 甜橙六画 on 2016/6/11.
 */
$(function(){
    $(".move").bind("mouseover",function(){
        move(this);
    })
})
function move(ele){		//移动元素函数
    new Drag(ele);
}
var Drag = function(el){
    var room = document.getElementById("room");
    var drag = function(e) {
        e = e || window.event;
        !+"\v1" ? document.selection.empty() : window.getSelection().removeAllRanges();
        el.style.left = e.clientX - el.offset_x + "px";
        el.style.top = e.clientY - el.offset_y + "px";
        if(el.offsetLeft+el.offsetWidth<0||el.offsetLeft>room.offsetWidth||el.offsetTop>room.offsetHeight||el.offsetTop+el.offsetHeight<0){
           el.style.opacity=0.1;
        }else{
            el.style.opacity=el.getAttribute("data-opacity");
        }
        moveEle();
    }
    var dragend = function(){
        if(el.offsetLeft+el.offsetWidth<0||el.offsetLeft>room.offsetWidth||el.offsetTop>room.offsetHeight||el.offsetTop+el.offsetHeight<0){
            el.parentNode.removeChild(el);
        }
        document.onmouseup = null;
        document.onmousemove = null;
    }
    var dragstart = function(e){
        e = e || window.event;
        el.offset_x = e.clientX - el.offsetLeft;
        el.offset_y = e.clientY - el.offsetTop;
        document.onmouseup = dragend;
        document.onmousemove = drag;
        return false;
    }
    el.onmousedown = dragstart;
}