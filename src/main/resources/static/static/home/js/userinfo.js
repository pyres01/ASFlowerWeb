/*
* @Author: admin
* @Date:   2018-10-15 08:48:03
* @Last Modified by:   admin
* @Last Modified time: 2018-10-15 14:31:21
*/

/*退出*/
layui.use('layer',function(){
	layer = layui.layer;
	
	$('#logout').click(function() {
		layer.confirm('您是否确认要退出？', {
			  btn: ['是的','再看看'] //按钮
			  ,title:['系统提示']
			}, function(){
			  $.get('/home/user/logout',function(data,status){
				  location.href="/home/index";
			  });
			}, function(){
		});
	});
	
});
//禁止页面后退
if (window.history && window.history.pushState) {
　　$(window).on('popstate', function (){
	　　window.history.pushState('forward', null, '#');
	　　window.history.forward(1);
　　});
}
window.history.pushState('forward', null, '#'); //在IE中必须得有这两行
window.history.forward(1);