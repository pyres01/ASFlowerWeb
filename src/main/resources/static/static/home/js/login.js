/*
* @Author: admin
* @Date:   2018-10-13 13:50:13
* @Last Modified by:   admin
* @Last Modified time: 2018-10-13 15:35:28
*/

/*登录验证弹出框*/
$('.modal .close').click(function(event) {
	$('.bg').slideUp();
	$('.modal').slideUp();
});

$('.modal .sure').click(function(event) {
	$('.bg').slideUp();
	$('.modal').slideUp();
});

/*表单验证*/

myform.onsubmit=function(b){
	var tel=/^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$/
	var flag=true;


	// 用户名

	if(username.value==''){
		tip.innerHTML="手机号不能为空";
		$('.bg').slideDown();
		$('.modal').slideDown();
		flag=false;
	}else if(!tel.test(username.value)){

		tip.innerHTML="手机号格式不正确";
		$('.bg').slideDown();
		$('.modal').slideDown();
		flag=false;

	}
	if(pwd.value==''){
		tip.innerHTML="密码不能为空";
		$('.bg').slideDown();
		$('.modal').slideDown();
		flag=false;

	}









	if(!flag){
	    			// 阻止默认行为；
	    			b.preventDefault();
	    		}

	    		


	    	}