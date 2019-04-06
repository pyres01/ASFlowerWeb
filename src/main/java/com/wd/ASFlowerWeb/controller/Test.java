package com.wd.ASFlowerWeb.controller;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wd.ASFlowerWeb.config.AlipayConfig;
import com.wd.ASFlowerWeb.entity.Manager;
import com.wd.ASFlowerWeb.entity.User;
import com.wd.ASFlowerWeb.mapper.ManagerMapper;
import com.wd.ASFlowerWeb.service.UserService;
import com.wd.ASFlowerWeb.service.util.MailService;

import lombok.extern.slf4j.Slf4j;

import com.alipay.api.*;

/**
 * @author 韦丹
 *
 * 2019年1月7日
 *
 */
@Controller
@Slf4j
public class Test {
	
	@Autowired
    private MailService mailService;
	@Autowired
    private UserService userService;
	@Autowired
	private ManagerMapper managerMapper;
	@RequestMapping("/test")
	@ResponseBody
	private Map<String,Object> test(HttpServletRequest request,String email) throws FileNotFoundException{
		
		//String path = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/";
		/*List<Integer> idList =  new ArrayList<Integer>();
		idList.add(1);
		idList.add(2);
		idList.add(3);
		System.out.println(StringUtils.strip(idList.toString(),"[]"));*/
		
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "");
		User user = userService.getUserByEmail(email);
		map.put("data", user);
		return map;
		
	}
	
	@GetMapping("/admin/test2")
	@ResponseBody
	private String test2(){
		String name = "pyres";
		List<Manager> managerList = managerMapper.findDelManagerByName(name,0,1);
		return managerList.toString();
	}
	
	@GetMapping("/admin/test3")
	@ResponseBody
	private Integer test3(){
		Manager manager = new Manager();
		manager.setName("test1");
		manager.setPassword("123456");
		manager.setBirthday(new Timestamp(System.currentTimeMillis()));
		return managerMapper.insert(manager);
	}
	
	@GetMapping("/admin/test4")
	@ResponseBody
	public Timestamp test4(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳


		Timestamp time= new Timestamp(System.currentTimeMillis());//获取系统当前时间 
		return  time;
	}
	
	@GetMapping("/alipay/test")
	public void alipaytest(HttpServletRequest request,HttpServletResponse response){
		// 商户订单号，商户网站订单系统中唯一订单号，必填
	    String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		// 订单名称，必填
	    String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
		System.out.println(subject);
	    // 付款金额，必填
	    String total_amount=new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
	    // 商品描述，可空
	    String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
	    // 超时时间 可空
	   String timeout_express="2m";
	    // 销售产品码 必填
	    String product_code="QUICK_WAP_WAY";
	    /**********************/
	    // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签     
	    //调用RSA签名方式
	    AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
	    AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
	    
	    // 封装请求支付信息
	    AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
	    model.setOutTradeNo(out_trade_no);
	    model.setSubject(subject);
	    model.setTotalAmount(total_amount);
	    model.setBody(body);
	    model.setTimeoutExpress(timeout_express);
	    model.setProductCode(product_code);
	    alipay_request.setBizModel(model);
	    // 设置异步通知地址
	    alipay_request.setNotifyUrl(AlipayConfig.notify_url);
	    // 设置同步地址
	    alipay_request.setReturnUrl(AlipayConfig.return_url);   
	    
	    // form表单生产
	    String form = "";
		try {
			// 调用SDK生成表单
			form = client.pageExecute(alipay_request).getBody();
			response.setContentType("text/html;charset=" + AlipayConfig.CHARSET); 
		    response.getWriter().write(form);//直接将完整的表单html输出到页面 
		    response.getWriter().flush(); 
		    response.getWriter().close();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}
