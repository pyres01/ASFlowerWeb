/*
* @Author: admin
* @Date:   2018-10-16 19:33:45
* @Last Modified by:   admin
* @Last Modified time: 2018-10-16 19:41:05
*/

$('.edit').click(function(event) {
	$('.title .save').show();
	$('input[name=sex]').attr('disabled',false);
	$('input[name=birthday]').attr('disabled',false);
});