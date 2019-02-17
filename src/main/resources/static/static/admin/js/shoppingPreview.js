
// 轮播图部分
var num=0;
var timer;

function go(){
	timer=setInterval(function(){
		num++;
		
		if(num>3)
		{
			num=0; 
			
		}

		$('#preview .rotate ol li').eq(num).addClass('current').siblings('').removeClass('current');
		$('#preview .rotate ul').animate({'left':-100*num+'%'})

	},2000);    
}

go();

	// 鼠标移入移出事件
$('#preview .rotate').hover(function() {
	 clearInterval(timer);
}, function() {
	go();
});
 
    // 小圆圈点击部分
$('#preview .rotate ol li').click(function(event) {
    var index=$(this).index()
    $(this).addClass('current').siblings('').removeClass('current');
    $('#preview .rotate ul').animate({'left':-100*index+'%'});
    num=index;

});

/*主要内容*/
$('#preview main .content>div').click(function(event) {
	$(this).toggleClass('current').siblings().removeClass('current');
});

/*商品展示*/
$('#preview .showtitle li').click(function(event) {
	var index=$(this).index();
	$(this).addClass('current').siblings().removeClass('current');
	$('#preview .goods>div').eq(index).addClass('current').siblings().removeClass('current');
});

/*数量*/
var number=1;
$('#preview .num .left').click(function(event) {
	number--;
	if(number<1){
		number=1;
	}
  
    $('#preview .num input').val(number)
	
});

$('#preview .num .right').click(function(event) {
	number++;
	$('#preview .num input').val(number)
	
});



