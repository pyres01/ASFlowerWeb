/*
* @Author: admin
* @Date:   2018-10-12 14:30:44
* @Last Modified by:   admin
* @Last Modified time: 2018-10-14 22:34:55
*/

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

		$('.rotate ol li').eq(num).addClass('current').siblings('').removeClass('current');
		$('.rotate ul').animate({'left':-100*num+'%'})

	},2000);    
}

go();

	// 鼠标移入移出事件
$('.rotate').hover(function() {
	 clearInterval(timer);
}, function() {
	go();
});
 
    // 小圆圈点击部分
$('.rotate ol li').click(function(event) {
    var index=$(this).index()
    $(this).addClass('current').siblings('').removeClass('current');
    $('.rotate ul').animate({'left':-100*index+'%'});
    num=index;

});

/*主要内容*/
$('main .content>div').click(function(event) {
	$(this).toggleClass('current').siblings().removeClass('current');
});

/*商品展示*/
$('.showtitle li').click(function(event) {
	var index=$(this).index();
	$(this).addClass('current').siblings().removeClass('current');
	$('.goods>div').eq(index).addClass('current').siblings().removeClass('current');
});

/*数量*/
var number=1;
$('.num .left').click(function(event) {
	number--;
	if(number<1){
		number=1;
	}
  
    $('.num input').val(number)
	
});

$('.num .right').click(function(event) {
	number++;
	$('.num input').val(number)
	
});



