/*
* @Author: admin
* @Date:   2018-10-15 08:48:03
* @Last Modified by:   admin
* @Last Modified time: 2018-10-15 14:31:21
*/


/*退出*/

$('#logout').click(function(event) {
	$('.bg').slideDown();
	$('.modal').slideDown();
});

$('.nosure').click(function(event) {
	$('.bg').slideUp();
	$('.modal').slideUp();
});