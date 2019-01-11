




// 轮播图部分
var timer;
var num=0;    
function go(){

	timer=setInterval(function(){
		num++;
		if(num>1){
			num=0
			$('.rotate ul').css('left', -100*num+'%');
		}
		
		$('.rotate ul').animate({'left':-100*num+'%'})
		$('.rotate ol li').eq(num).addClass('current').siblings('').removeClass('current');

	},2000);    
}

go();