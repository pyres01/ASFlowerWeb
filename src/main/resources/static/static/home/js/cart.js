/*
* @Author: admin
* @Date:   2018-10-13 20:27:48
* @Last Modified by:   admin
* @Last Modified time: 2018-10-15 00:16:26
*/

/*数量*/

$('.num .left').click(function(event) {
	var number=$(this).next().children().val();
	number--;
	if(number<1){
		number=1;
	}
  
    $(this).next().children().val(number);
    updateCart();
    var topP = $(this).parent().parent().parent();
    if($(topP).find('input[type=checkbox]').is(':checked')){
    	
    	var money = $(topP).find('.priceNum:first').text();
		$('#totalMoney').text(parseFloat($('#totalMoney').text())-parseFloat(money));
    }
	
});

$('.num .right').click(function(event) {
	var number=$(this).prev().children().val();
	number++;
	$(this).prev().children().val(number);
	updateCart();
	var topP = $(this).parent().parent().parent();
	if($(topP).find('input[type=checkbox]').is(':checked')){
		var money = $(topP).find('.priceNum:first').text();
		$('#totalMoney').text(parseFloat($('#totalMoney').text())+parseFloat(money));
	}
	
	
});


/*/*选中状态*/

$('.imfor .choice input').on('click', function(event) {
	if($(this).is(':checked')){
		
		$(this).next().show();
	}
});

$('.imfor .choice .bg').on('click', function(event) {
	if($(this).prev().is(':checked')){
		$(this).prev().removeAttr('checked');
		$(this).hide();
	}
});

/*全选*/
/*$('.count .choice input').on('click', function(event) {
	if($(this).is(':checked')){
		$(this).next().text('取消全选');
		$(this).next().next().show();
		$('.imfor .choice input').attr('checked', true);
		$('.imfor .choice .bg').show();
	}
});*/

$('.count .choice .bg').on('click', function(event) {
	if($(this).prev().prev().is(':checked')){
		$(this).prev().text('全选');
		$(this).prev().prev().removeAttr('checked');
		$(this).hide();
		$('.imfor .choice input').removeAttr('checked');
		$('.imfor .choice .bg').hide();
	}
});