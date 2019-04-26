package com.wd.ASFlowerWeb.controller.home;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.mysql.cj.Session;
import com.wd.ASFlowerWeb.entity.ReceAddress;
import com.wd.ASFlowerWeb.entity.User;
import com.wd.ASFlowerWeb.service.ReceAddressService;
import com.wd.ASFlowerWeb.service.UserService;
import com.wd.ASFlowerWeb.service.util.MailService;

import lombok.extern.slf4j.Slf4j;

/**
 * 前台会员控制器
 * @author 韦丹
 *
 * 2019年1月10日
 *
 */
@Controller
@Slf4j
public class HomeUserInfoController {
	
	@Autowired
	private UserService userService;	//会员service
	@Autowired
	private ReceAddressService receAddressService;	//收货地址service
	
	//"我的" 页面
	@GetMapping("/home/user/userinfo")
	public ModelAndView userinfo(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home/userinfo");
		return mav;
	}
	
	//个人资料页面
	@GetMapping("/home/user/useraccount")
	public String useraccount(){
		return "home/useraccount";
	}
	
	//会员收货地址，计划是可以保存多个收货地址供选择，其中一个默认收货地址，此处，将默认收货地址设置为仅有的唯一一个收货地址
	@RequestMapping("/home/user/userAddress")
	public ModelAndView address(HttpServletRequest req){
		ModelAndView mav = null;
		if(req.getMethod().equals("POST")){
			mav = new ModelAndView(new MappingJackson2JsonView());
			String receiver = "";
			String phone = "";
			String address="";
			boolean validate = true;//数据验证标识
			
			//收货人
			if(req.getParameter("receiver")!=null && req.getParameter("receiver").trim().length()>=2){
				receiver = req.getParameter("receiver").trim();
			}else{
				validate = false;
			}
			//联系号码
			if(req.getParameter("phone")!=null && req.getParameter("phone").trim().length()==11){
				phone = req.getParameter("phone").trim();
			}else{
				validate = false;
			}
			//收货地址
			if(req.getParameter("address")!=null && req.getParameter("address").trim().length()>=10){
				address = req.getParameter("address").trim();
			}else{
				validate = false;
			}
			//验证通过
			if(validate){
				boolean saveStatus = true;
				ReceAddress addr = null;
				HttpSession session = req.getSession();
				
				//如果session中已保存有默认地址且不为空就是修改收货地址，且要更新session中的收货地址
				if(session.getAttribute("defReceAddress") != null){
					addr = (ReceAddress) session.getAttribute("defReceAddress");
					addr.setAddress(address);
					addr.setPhone(phone);
					addr.setReceiver(receiver);
					if(receAddressService.updateById(addr)){
						session.setAttribute("defReceAddress",addr);
					}else{
						saveStatus = false;
					}
				}else{//否则即使添加收货地址，并保存到session中去
					addr = new ReceAddress();
					Integer uid = Integer.valueOf(((User)session.getAttribute("member")).getId());
					addr.setUid(uid);
					addr.setReceiver(receiver);
					addr.setPhone(phone);
					addr.setAddress(address);
					addr.setDef(true);
					if(receAddressService.insert(addr)){
						ReceAddress dfAddr = receAddressService.getDefReceAddress(uid);
						session.setAttribute("defReceAddress",dfAddr);
					}else{
						saveStatus = false;
					}
				}
				if(saveStatus){
					mav.addObject("code", 200);
					mav.addObject("msg", "保存成功");
				}else{
					mav.addObject("code", 0);
					mav.addObject("msg", "保存失败");
				}
			}else{
				mav.addObject("code", 0);
				mav.addObject("msg", "数据不合法");
			}
			
		}else{
			mav = new ModelAndView("home/address");
		}
		return mav;
	}
	//显示登录页面
	@GetMapping("/home/login")
	public String login(){
		return "home/login";
	}
	
	//会员退出
	@GetMapping("/home/user/logout")
	@ResponseBody
	public void logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute("member")!=null){
			session.removeAttribute("member");
		}
	}
	
	//显示注册页面
	@GetMapping("/home/register")
	public String register(){
		return "home/register";
	}
	
	//修改密码
	@RequestMapping("/home/user/repassword")
	public ModelAndView rePassWord(HttpServletRequest request){
		ModelAndView mav = null;
		if(request.getMethod().equals("POST")){
			mav = new ModelAndView(new MappingJackson2JsonView());
			String oldpass = request.getParameter("password");
			String newpass = request.getParameter("newPassword");
			String repass = request.getParameter("repassword");
			if(this.validatePassword(oldpass) && this.validatePassword(newpass) && this.validateRePassword(newpass, repass)){
				User u = (User) request.getSession().getAttribute("member");
				if(u.getPassword().equals(oldpass)){
					u.setPassword(newpass);
					if(userService.updateUser(u)){
						request.getSession().setAttribute("member",u);
						mav.addObject("code", 200);
						mav.addObject("msg", "修改成功");
					}else{
						mav.addObject("code", 0);
						mav.addObject("msg", "修改失败");
					}
				}else{
					mav.addObject("code", 0);
					mav.addObject("msg", "密码错误");
				}
			}else{
				mav.addObject("code", 0);
				mav.addObject("msg", "数据有误");
			}
			
		}else{
			mav = new ModelAndView("home/repassword");
		}
		return mav;
	}
	
	//找回密码
	@RequestMapping("/home/findPass")
	public ModelAndView findPass(HttpServletRequest req){
		ModelAndView mav = null;
		if(req.getMethod().equals("POST")){
			String op  = req.getParameter("op");
			mav = new ModelAndView(new MappingJackson2JsonView());
			if(op!=null&&op.trim().length()>0){
				HttpSession sessoin=req.getSession();
				if(op.equals("v")){//验证邮箱部分
					String fEmail = req.getParameter("email");
					String vcode = req.getParameter("code");
					if(this.validateFindPassCaptcha(req, fEmail, vcode)){
						sessoin.setAttribute("fpassEmail",fEmail);
						sessoin.setAttribute("fpassStatus", "ready");
						mav.addObject("code", 200);
						mav.addObject("msg", "验证成功");
					}else{
						mav.addObject("code", 0);
						mav.addObject("msg", "验证失败");
					}
				}else if(op.equals("u")){//更新密码部分
					mav = new ModelAndView(new MappingJackson2JsonView());
					String password = req.getParameter("password");
					String repassword = req.getParameter("repassword");
					if(this.validatePassword(password)&& this.validateRePassword(password, repassword)){
						String fpassStatus =  (String) sessoin.getAttribute("fpassStatus");
						if(fpassStatus.equals("ready")){
							String email = (String) sessoin.getAttribute("fpassEmail");
							User member = userService.getUserByEmail(email);
							member.setPassword(password);
							userService.updateUser(member);
							mav.addObject("code", 200);
							mav.addObject("msg", "找回成功");
						}else{
							mav.addObject("code", 0);
							mav.addObject("msg", "您还没有通过验证");
						}
					}else{
						mav.addObject("code", 0);
						mav.addObject("msg", "密码不一致");
					}
					
				}else{
					mav.addObject("code", 0);
					mav.addObject("msg", "参数错误");
				}
			}else{
				mav.addObject("code", 0);
				mav.addObject("msg", "参数错误");
			}
		}else{
			mav = new ModelAndView();
			mav.setViewName("home/find-pass");
		}
		return mav;
	}
	
	//验证登录，如果登录成功，同时保存收货地址到session
	@PostMapping("/home/validateLogin")
	@ResponseBody
	public Map<String,Object> validateLogin(HttpServletRequest req){
		Map<String,Object> map = new HashMap<>();
		
		String username = "";
		String password = "";
		boolean checkStatus = true;
		if(req.getParameter("username")!=null){
			username = req.getParameter("username").trim();
			if(!this.validateUsername(username)){
				checkStatus = false;
			}
		}
		if(req.getParameter("userpass")!=null){
			password = req.getParameter("userpass");
			if(!this.validatePassword(password)){
				checkStatus = false;
			}
		}
		User member = userService.getUserByMName(username);
		if(member.getStatus()==0 || member==null || !member.getPassword().equals(password)){
			checkStatus = false;
		}
		if(!checkStatus){
			map.put("code", 0);
			map.put("msg", "验证失败");
		}else{
			ReceAddress DefReceAddress = receAddressService.getDefReceAddress(1);
			HttpSession sessoin=req.getSession();
			sessoin.setAttribute("member",member);
			sessoin.setAttribute("defReceAddress",DefReceAddress);
			map.put("code", 200);
		}
		return map;
	}
	
	//验证注册
	@PostMapping("/home/validateRegister")
	@ResponseBody
	public Map<String,Object> validateRegister(HttpServletRequest req){
		
		Map<String,Object> map = new HashMap<>();
		
		String username = "";
		String password = "";
		String repassword = "";
		String email = "";
		String captcha = "";
		
		boolean checkStatus = true;
		if(req.getParameter("email")!=null){
			email = req.getParameter("email");
			if(!this.validateEmail(email)){
				checkStatus = false;
			}else{
				if(req.getParameter("captcha")!=null){
					captcha = req.getParameter("captcha");
					if(!this.validateRegCaptcha(req, email, captcha)){
						checkStatus = false;
					}
				}else{
					checkStatus = false;
				}
			}
		}
		
		
		if(req.getParameter("username")!=null){
			username = req.getParameter("username").trim();
			if(!this.validateUsername(username) || isExistUserByMName(username)){
				checkStatus = false;
			}else{
				User member = userService.getUserByMName(username);
				if(member!=null){
					checkStatus = false;
				}
			}
		}
		
		if(req.getParameter("password")!=null){
			password = req.getParameter("password").trim();
			if(!this.validatePassword(password)){
				checkStatus = false;
			}
		}
		if(req.getParameter("repassword")!=null){
			repassword = req.getParameter("repassword").trim();
			if(!(this.validatePassword(repassword) && this.validateRePassword(password, repassword))){
				checkStatus = false;
			}
		}
		
		if(!checkStatus){
			map.put("code",0);
			map.put("msg","注册失败");
		}else{
			User u = new User();
			u.setMemberName(username);
			u.setPassword(password);
			u.setEmail(email);
			u.setJoinTime(new Timestamp(System.currentTimeMillis()));
			u.setAvatar("/static/home/images/logo_new.png");
			if(userService.addUser(u)){
				map.put("code",200);
				map.put("msg","注册成功");
			}else{
				map.put("code",0);
				map.put("msg","注册失败");
			}
		}
		return map;
	}
	
	//更新会员资料
	@PostMapping("/user/updateInfo")
	@ResponseBody
	public Map<String,Object> updateInfo(HttpServletRequest req){
		Map<String,Object> map = new HashMap<>();
		
		HttpSession session = req.getSession();
		User member = (User) session.getAttribute("member");
		Integer uid = member.getId();
		member = userService.getUserById(uid);
		
		String sex = req.getParameter("sex");
		String birthday = req.getParameter("birthday");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		if(sex.trim().length()==1){
			Boolean _sex = Integer.valueOf(sex)==1?true:false;
			member.setSex(_sex);
		}
		try {
			Date _birthday = sdf.parse(birthday);
			member.setBirthday(_birthday);
		}catch(Exception e){}
		log.info(member.toString());
		if(userService.updateUser(member)){
			session.setAttribute("member",member);
			map.put("code", 200);
			map.put("msg", "修改成功");
		}else{
			map.put("code", 0);
			map.put("msg", "修改失败");
		}
		
		return map;
	}
	
	
	
	
	//验证会员名
	private boolean validateUsername(String username){
		if(username!=null && username.trim().length()>=5 && username.trim().length()<=10){
			return true;
		}
		return false;
	}
	//验证密码
	private boolean validatePassword(String password){
		if(password == null || password.trim().length()<6 || password.trim().length()>12){
			return false;
		}
		return true;
	}
	//验证确认密码
	private boolean validateRePassword(String password,String repassword){
		return password.equals(repassword);
	}
	//验证邮箱格式
	private boolean validateEmail(String email){
		String REGEX="^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$";
		Pattern p = Pattern.compile(REGEX);    
		Matcher matcher=p.matcher(email);    
		if(matcher.matches() && userService.getUserByEmail(email)==null){
			return true;
		}
		return false;
	}
	//判断注册验证码是否正确
	private boolean validateRegCaptcha(HttpServletRequest request,String email,String captcha){
		HttpSession session = request.getSession();
		return (session.getAttribute("reg"+email)!=null && session.getAttribute("reg"+email).equals(captcha))?true:false;
	}
	//判断找回密码验证码是否正确
	private boolean validateFindPassCaptcha(HttpServletRequest request,String email,String captcha){
		HttpSession session = request.getSession();
		return (session.getAttribute("fpass"+email)!=null && session.getAttribute("fpass"+email).equals(captcha))?true:false;
	}
	
	//根据会员名判断会员是否存在
	private boolean isExistUserByMName(String memberName){
		return userService.getUserByMName(memberName)!=null?true:false;
	}
	
	
}
