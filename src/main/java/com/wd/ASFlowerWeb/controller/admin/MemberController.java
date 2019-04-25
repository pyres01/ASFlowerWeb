package com.wd.ASFlowerWeb.controller.admin;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.wd.ASFlowerWeb.entity.User;
import com.wd.ASFlowerWeb.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 韦丹
 *
 * 2019年1月22日
 * 
 * 会员/用户控制器
 *
 */
@Controller
@Slf4j


public class MemberController {
	
	@Autowired
	UserService userService;
	
	/**
	 * 会员列表
	 * @return
	 */
	@GetMapping("/admin/member/list")
	public String memberList(){
		return "admin/member-list";
	}
	
	/**
	 * 编辑会员信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/member/edit")
	public ModelAndView memberEdit(HttpServletRequest request) throws Exception{
		
		String op = "";	//根据op判断操作
		if(request.getParameter("op")!=null && !request.getParameter("op").equals("")){
			op = request.getParameter("op");
		}
		ModelAndView modelAndView = null;
		switch (op) {
		case ""://加载添加窗口
			modelAndView = new ModelAndView();
			modelAndView.addObject("astatus", "t");
			modelAndView.setViewName("admin/member-edit");
			break;
		case "1"://加载修改窗口
			Integer id = request.getParameter("id")!=null?Integer.valueOf(request.getParameter("id")):null;
			//如果id为空直接抛出异常
			if(id==null) throw new Exception();
			modelAndView = new ModelAndView();
			modelAndView.addObject("astatus", "f");
			modelAndView.addObject("id", id);
			modelAndView.setViewName("admin/member-edit");
			break;
		case "2"://实现会员添加
			modelAndView = this.memberAdd(request);
			break;
		case "3"://会员更新
			modelAndView = this.memberUpdate(request);
			break;
		default://默认加载添加窗口
			modelAndView = new ModelAndView();
			modelAndView.addObject("astatus", true);
			modelAndView.setViewName("admin/member-edit");
			break;
		}
		return modelAndView;
	}
	
	
	/**
	 * 更新会员信息
	 * @param request
	 * @return ModelAndView
	 */
	private ModelAndView memberUpdate(HttpServletRequest request) {
		// TODO Auto-generated method stub
		ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
		
		Integer id = request.getParameter("id")!=null?Integer.valueOf(request.getParameter("id")):null;
		if(id!=null){//会员id不能为空
			//先根据id获取会员信息，如果有修改再改变信息数据，否则保留原有
			User user = userService.getUserById(id);
			if(user!=null){//会员存在
				
				Boolean validateStatus = true;	//标识验证要更新的数据是否合法
				
				//会员名
				String username = request.getParameter("username");
				if(username!=null && !username.trim().equals("")&&username.length()>=5){
					user.setMemberName(username);
				}else{
					validateStatus = false;
				}
				
				//昵称
				String nickname = request.getParameter("nickname");
				if(nickname!=null && !nickname.trim().equals("") && nickname.length()>=5){
					user.setNickName(nickname);
					
				}else{
					validateStatus = false;
				}
				
				//性别
				Boolean sex = request.getParameter("sex")!=null?Boolean.valueOf(request.getParameter("sex")):false;
				user.setSex(sex);
				
				//生日
				if(request.getParameter("birthday")!=null && !request.getParameter("birthday").trim().equals("")){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
					try {
						Date birthday = sdf.parse(request.getParameter("birthday").trim().toString());
						user.setBirthday(birthday);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//号码
				String phone = request.getParameter("phone");
				if(phone!=null && !phone.trim().equals("") && phone.length()==11){
					user.setPhone(phone);
				}else{
					validateStatus = false;
				}
				
				//邮箱
				String email = request.getParameter("email");
				if(email!=null){
					String regEmail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
					Pattern p;
			        Matcher m;
			        p = Pattern.compile(regEmail);
			        m = p.matcher(email);
			        if (m.matches()){
						user.setEmail(email);
					}
				}else{
					validateStatus =false;
				}
				
				//QQ
				String qq = request.getParameter("qq");
				if(qq!=null && !qq.trim().equals("") && qq.length()>=5){
					user.setQq(qq);
				}
				
				//微信
				String wechat = request.getParameter("wechat");
				if(wechat!=null && !wechat.trim().equals("") && wechat.length()>=6 && wechat.length()<=20){
					user.setWechat(wechat);
				}
				
				//密码和确认密码
				String password = request.getParameter("pass");
				String repass = request.getParameter("repass");
				if(password!=null && !password.trim().equals("") && password.length()>=6 && password.length()<=12 && password.equals(repass)){
					user.setPassword(password);
				}
				
				//头像
				int avatar =request.getParameter("avatar")!=null?Integer.valueOf(request.getParameter("avatar")):0;
				if(avatar==1){//男
					user.setAvatar("/static/common/images/man_logo.png");
				}else{//女
					user.setAvatar("/static/common/images/female_logo.png");
				}
				
				//等级
				Integer rank = request.getParameter("rank")!=null?Integer.valueOf(request.getParameter("rank")):1;
				user.setRankId(rank);
				
				
				if(validateStatus){//验证通过
					Boolean updateStatus = userService.updateUser(user);
					if(updateStatus){
						modelAndView.addObject("code",200);
						modelAndView.addObject("msg","更新成功！");
					}else{
						modelAndView.addObject("code",0);
						modelAndView.addObject("msg","更新失败！");
					}
				}else{
					modelAndView.addObject("code",0);
					modelAndView.addObject("msg","数据不合法");
				}
			}else{
				modelAndView.addObject("code",0);
				modelAndView.addObject("msg","非法请求");
			}
		}else{
			modelAndView.addObject("code",0);
			modelAndView.addObject("msg","非法请求");
		}
		return modelAndView;
	}

	//实现会员添加
	private ModelAndView  memberAdd(HttpServletRequest request){
		
		User user = new User();
		Boolean validateStatus = true;//表示验证数据是否合法
		
		ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
		
		//会员名
		String username = request.getParameter("username");
		if(username!=null && !username.trim().equals("")&&username.length()>=5){
			user.setMemberName(username);
		}else{
			validateStatus = false;
		}
		
		//昵称（非必填）
		String nickname = request.getParameter("nickname");
		if(nickname!=null && !nickname.trim().equals("") && nickname.length()>=5){
			user.setNickName(nickname);
		}
		
		//性别
		Boolean sex = request.getParameter("sex")!=null?Boolean.valueOf(request.getParameter("sex")):false;
		user.setSex(sex);
		
		//生日
		if(request.getParameter("birthday")!=null && !request.getParameter("birthday").trim().equals("")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			try {
				Date birthday = sdf.parse(request.getParameter("birthday").trim().toString());
				user.setBirthday(birthday);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//号码
		String phone = request.getParameter("phone");
		if(phone!=null && !phone.trim().equals("") && phone.length()==11){
			user.setPhone(phone);
		}else{
			validateStatus = false;
		}
		
		//邮箱
		String email = request.getParameter("email");
		if(email!=null){
			String regEmail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern p;
	        Matcher m;
	        p = Pattern.compile(regEmail);
	        m = p.matcher(email);
	        if (m.matches()){
				user.setEmail(email);
			}
		}else{
			validateStatus =false;
		}
		
		//QQ
		String qq = request.getParameter("qq");
		if(qq!=null && !qq.trim().equals("") && qq.length()>=5){
			user.setQq(qq);
		}
		//微信
		String wechat = request.getParameter("wechat");
		if(wechat!=null && !wechat.trim().equals("") && wechat.length()>=6 && wechat.length()<=20){
			user.setWechat(wechat);
		}
		
		//密码和确认密码
		String password = request.getParameter("pass");
		String repass = request.getParameter("repass");
		if(password!=null && !password.trim().equals("") && password.length()>=6 && password.length()<=12 && password.equals(repass)){
			user.setPassword(password);
		}else{
			validateStatus = false;
		}
		
		//头像
		int avatar =request.getParameter("avatar")!=null?Integer.valueOf(request.getParameter("avatar")):0;
		if(avatar==1){//男
			user.setAvatar("/static/common/images/man_logo.png");
		}else{//女
			user.setAvatar("/static/common/images/female_logo.png");
		}
		
		//等级
		Integer rank = request.getParameter("rank")!=null?Integer.valueOf(request.getParameter("rank")):1;
		user.setRankId(rank);
		
		//设置加入时间
		user.setJoinTime(new Timestamp(System.currentTimeMillis()));
		//验证通过
		if(validateStatus){
			Boolean addStatus = userService.addUser(user);
			if(addStatus){
				modelAndView.addObject("code",200);
				modelAndView.addObject("msg","添加成功");
			}else{
				modelAndView.addObject("code",0);
				modelAndView.addObject("msg","添加失败");
			}
		}else{
			modelAndView.addObject("code",0);
			modelAndView.addObject("msg","数据不合法");
		}
		return modelAndView;
	}
	
	//查询会员
	@PostMapping("/admin/member/searchListPage")
	@ResponseBody
	public Map<String,Object> searchListPage(HttpServletRequest request){
		
		//分页数
		int pageSize = 15;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDateStr = request.getParameter("start")!=null && !request.getParameter("start").trim().isEmpty()?request.getParameter("start").trim():"2019-01-01";
		String endDateStr = request.getParameter("end")!=null && !request.getParameter("end").trim().isEmpty()?request.getParameter("end").trim():sdf.format(new Date());
		String username = request.getParameter("username")!=null && !request.getParameter("username").trim().isEmpty()?request.getParameter("username").trim():"";
		int page = request.getParameter("page")!=null?Integer.valueOf(request.getParameter("page")):1;
		
		
		int pageCount = 0;
		Map<String,Object> map = new HashMap<>();
		//只有加入日期查询
		if(username.equals("")){
			int count = userService.getSearchListCountByJT(startDateStr, endDateStr);
			pageCount = (int) Math.ceil((double)count/pageSize);
			if(pageCount !=0){
				page = Math.max(Math.min(page, pageCount), 1);
				List<User> userList = userService.getSearchListByJT(startDateStr, endDateStr, (page-1)*pageSize, pageSize);
				map.put("code", 200);
				map.put("msg", "search with date success");
				map.put("userList", userList);
				map.put("userCount", count);
				map.put("pageCount", pageCount);
			}else{
				map.put("code", 0);
				map.put("msg", "search with date fail,no result");
			}
		}else{//加入日期和用户名模糊查询
			int count = userService.getSearchListCountByJTN(startDateStr, endDateStr, username);
			pageCount = (int) Math.ceil((double)count/pageSize);
			if(pageCount!=0){
				page = Math.max(Math.min(page, pageCount), 1);
				List<User> userList = userService.getSearchListByJTN(startDateStr, endDateStr,username, (page-1)*pageSize, pageSize);
				map.put("code", 200);
				map.put("msg", "search with date ane name success");
				map.put("userList", userList);
				map.put("userCount", count);
				map.put("pageCount", pageCount);
			}else{
				map.put("code", 0);
				map.put("msg", "search with date and name fail,no result");
			}
		}
		
		return map;
	}
	
	//会员删除
	@PostMapping("/admin/member/del")
	@ResponseBody
	public Map<String,Object> delMember(@RequestParam("id") Integer id){
		Map<String,Object> map = new HashMap<>();
		
		if(id!=null && id>0){
			if(userService.delete(id)){
				map.put("code", 200);
				map.put("msg", "删除成功");
			}else{
				map.put("code", 0);
				map.put("msg", "删除失败");
			}
			
		}else{
			map.put("code", 0);
			map.put("msg", "非法操作");
			
		}
		return map;
	}
	
	//根据id获取会员信息
	@PostMapping("/admin/member/getMember")
	@ResponseBody
	public Map<String,Object> getMemberById(@RequestParam("id") Integer id){
		Map<String,Object> map = new HashMap<>();
		if(id!=null && id>0){
			User user = userService.getUserById(id);
			if(user!=null){
				map.put("code", 200);
				map.put("msg", "获取成功");
				map.put("member", user);
			}else{
				map.put("code", 0);
				map.put("msg", "获取失败");
			}
		}else{
			map.put("code", 0);
			map.put("msg", "参数有误");
		}
		
		return map;
	}

	
}
