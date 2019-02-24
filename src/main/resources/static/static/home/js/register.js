/*
* @Author: admin
* @Date:   2018-10-13 13:50:13
* @Last Modified by:   admin
* @Last Modified time: 2018-10-13 16:08:57
*/
var layer;
var sendCodeH = 0;
layui.use('layer',function(){
	layer = layui.layer;
});
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

$('#regBtn').click(function(){
/*	var tel=/^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$/
*/	
    var username = $.trim($('input#username').val());
    var password = $.trim($('input#password').val());
    var repass = $.trim($('input#repassword').val());
    var email = $.trim($('#email').val());
    var captcha = $.trim($('#captcha').val());
    
	if(validateUsername(username) && validatePass(password) && validateRePass(password,repass) && validateEmail(email) && validateCaptcha(captcha) && checkSendH()){
		var loadI;
		$.ajax({
			url:'/home/validateRegister',
			type:'POST',
			dataType:'json',
			data:$('#myform').serialize(),
			beforeSend:function(){
				loadI = layer.load();
			},
			success:function(res){
				
			},complete:function(){
				layer.close(loadI);
			}
		});
	}
});

$('#getCaptchaBtn').click(function(){
	var that = $(this);
	var email = $.trim($('input#email').val());
	if(validateEmail(email)){
		settime(that);
		/*$.ajax({
			url:'/sendCheckCode'
			,type:'POST'
			,dataType:'json'
			,data:{type:'register',email:email}
			,success:function(res){
				if(res.code == 20005){
					layer.msg('邮箱已占用！',{icon:5});
				}else if(res.code == 200){
					sendCodeH++;
				}
			}
		});*/
	}
	
});

function validateUsername(username){
	
	if(username==''){
		tip.innerHTML="会员名不能为空";
		$('.bg').slideDown();
		$('.modal').slideDown();
		return false;

	}else if(username.length<5||username.length>10){

		tip.innerHTML="会员名需为5-10个长度";
		$('.bg').slideDown();
		$('.modal').slideDown();
		return false;

	}
	return true;
}

function validatePass(password){
	if(password==''){
		tip.innerHTML="密码不能为空";
		$('.bg').slideDown();
		$('.modal').slideDown();
		return false;
	}else if(password.length<6 || password.length>12){
		tip.innerHTML="密码需为6-12个长度";
		$('.bg').slideDown();
		$('.modal').slideDown();
		return false;
	}
	return true;
}
function validateRePass(pass,repass){
	if(pass!=repass){
		tip.innerHTML="密码不一致";
		$('.bg').slideDown();
		$('.modal').slideDown();
		return false;
	}
	return true;
}

function validateEmail(email){
	var emailReg = /^\w+((-\w+)|(\.\w+))*@\w+(\.\w{2,3}){1,3}$/;
	if(!emailReg.test(email)){
		tip.innerHTML="请输入正确邮箱";
		 $('.bg').slideDown();
		 $('.modal').slideDown();
		 return false;
	}
	return true;
}

function validateCaptcha(captcha){
	if(captcha.length!=6){
		tip.innerHTML="邮箱验证码格式有误";
		 $('.bg').slideDown();
		 $('.modal').slideDown();
		 return false;
	}
	return true;
}

var countdown=60; 
function settime(obj) {
    if (countdown == 0) { 
        $(obj).removeAttr("disabled");    
        $(obj).val("点 击 获 取"); 
        countdown = 60; 
        return;
    }else{ 
    	$(obj).attr("disabled", true); 
    	$(obj).val("重新发送(" + countdown + ")"); 
        countdown--; 
    } 
	setTimeout(function() { 
	    settime($(obj)) 
	   },1000); 
}
function checkSendH(){
	if(!sendCodeH){
		tip.innerHTML="请按常规操作进行注册，谢谢！";
		$('.bg').slideDown();
		$('.modal').slideDown();
		return false;
	}
	return true;
}