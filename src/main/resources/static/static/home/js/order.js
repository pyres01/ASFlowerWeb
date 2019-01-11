/*
* @Author: admin
* @Date:   2018-10-15 14:42:32
* @Last Modified by:   admin
* @Last Modified time: 2018-10-15 15:16:55
*/


/*订单切换*/

$('.state li').click(function(event) {
	var index=$(this).index();
	$(this).addClass('current').siblings().removeClass('current');
	$('section>div').eq(index).addClass('current').siblings().removeClass('current');
});