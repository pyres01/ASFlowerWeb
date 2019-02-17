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
 * @author 若尘
 *
 * 2019年1月22日
 *
 */
@Controller
@Slf4j
public class MemberController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/admin/member/list")
	public String memberList(){
		return "/admin/member-list";
	}
	
	@RequestMapping("/admin/member/edit")
	public ModelAndView memberEdit(HttpServletRequest request) throws Exception{
		
		String op = "";
		if(request.getParameter("op")!=null && !request.getParameter("op").equals("")){
			op = request.getParameter("op");
		}
		ModelAndView modelAndView = null;
		switch (op) {
		case ""://load add
			modelAndView = new ModelAndView();
			modelAndView.addObject("astatus", "t");
			modelAndView.setViewName("/admin/member-edit");
			break;
		case "1"://load update
			Integer id = request.getParameter("id")!=null?Integer.valueOf(request.getParameter("id")):null;
			//如果id为空直接抛出异常
			if(id==null) throw new Exception();
			modelAndView = new ModelAndView();
			modelAndView.addObject("astatus", "f");
			modelAndView.addObject("id", id);
			modelAndView.setViewName("/admin/member-edit");
			break;
		case "2"://add
			modelAndView = this.memberAdd(request);
			break;
		case "3"://update
			modelAndView = this.memberUpdate(request);
			break;
		default:
			modelAndView = new ModelAndView();
			modelAndView.addObject("astatus", true);
			modelAndView.setViewName("/admin/member-edit");
			break;
		}
		log.info(modelAndView.toString());
		return modelAndView;
	}
	
	
	/**
	 * @param request
	 * @return ModelAndView
	 */
	private ModelAndView memberUpdate(HttpServletRequest request) {
		// TODO Auto-generated method stub
		ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
		
		Integer id = request.getParameter("id")!=null?Integer.valueOf(request.getParameter("id")):null;
		if(id!=null){
			User user = userService.getUserById(id);
			if(user!=null){
				//update user info by id
				Boolean validateStatus = true;
				String username = request.getParameter("username");
				if(username!=null && !username.trim().equals("")&&username.length()>=5){
					user.setMemberName(username);
				}else{
					validateStatus = false;
					log.info("username validate fail");
				}
				
				String nickname = request.getParameter("nickname");
				if(nickname!=null && !nickname.trim().equals("") && nickname.length()>=5){
					user.setNickName(nickname);
					
				}else{
					validateStatus = false;
					log.info("nickname validate fail");
				}
				
				Boolean sex = request.getParameter("sex")!=null?Boolean.valueOf(request.getParameter("sex")):false;
				user.setSex(sex);
				
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
				
				String phone = request.getParameter("phone");
				if(phone!=null && !phone.trim().equals("") && phone.length()==11){
					user.setPhone(phone);
				}else{
					validateStatus = false;
					log.info("phone validate fail");
				}
				
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
					log.info("email validate fail");
				}
				
				String qq = request.getParameter("qq");
				if(qq!=null && !qq.trim().equals("") && qq.length()>=5){
					user.setQq(qq);
				}
				
				String wechat = request.getParameter("wechat");
				if(wechat!=null && !wechat.trim().equals("") && wechat.length()>=6 && wechat.length()<=20){
					user.setWechat(wechat);
				}
				
				String password = request.getParameter("pass");
				String repass = request.getParameter("repass");
				if(password!=null && !password.trim().equals("") && password.length()>=6 && password.length()<=12 && password.equals(repass)){
					user.setPassword(password);
				}
				
				String avatar = request.getParameter("avatar");
				if(avatar!=null){
					
				}else{
					
				}
				
				Integer rank = request.getParameter("rank")!=null?Integer.valueOf(request.getParameter("rank")):1;
				user.setRankId(rank);
				if(validateStatus){
					Boolean updateStatus = userService.updateUser(user);
					if(updateStatus){
						modelAndView.addObject("code",200);
						modelAndView.addObject("msg","update success");
					}else{
						modelAndView.addObject("code",0);
						modelAndView.addObject("msg","update fail");
					}
				}else{
					modelAndView.addObject("code",0);
					modelAndView.addObject("msg","param fail");
				}
			}else{
				modelAndView.addObject("code",0);
				modelAndView.addObject("msg","param error");
			}
		}else{
			modelAndView.addObject("code",0);
			modelAndView.addObject("msg","param error");
		}
		return modelAndView;
	}

	private ModelAndView  memberAdd(HttpServletRequest request){
		
		User user = new User();
		Boolean validateStatus = true;
		
		ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
		
		
		String username = request.getParameter("username");
		if(username!=null && !username.trim().equals("")&&username.length()>=5){
			user.setMemberName(username);
		}else{
			validateStatus = false;
		}
		
		String nickname = request.getParameter("nickname");
		if(nickname!=null && !nickname.trim().equals("") && nickname.length()>=5){
			user.setNickName(nickname);
		}
		
		Boolean sex = request.getParameter("sex")!=null?Boolean.valueOf(request.getParameter("sex")):false;
		user.setSex(sex);
		
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
		
		String phone = request.getParameter("phone");
		if(phone!=null && !phone.trim().equals("") && phone.length()==11){
			user.setPhone(phone);
		}else{
			validateStatus = false;
		}
		
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
		
		String qq = request.getParameter("qq");
		if(qq!=null && !qq.trim().equals("") && qq.length()>=5){
			user.setQq(qq);
		}
		
		String wechat = request.getParameter("wechat");
		if(wechat!=null && !wechat.trim().equals("") && wechat.length()>=6 && wechat.length()<=20){
			user.setWechat(wechat);
		}
		
		String password = request.getParameter("pass");
		String repass = request.getParameter("repass");
		if(password!=null && !password.trim().equals("") && password.length()>=6 && password.length()<=12 && password.equals(repass)){
			user.setPassword(password);
		}else{
			validateStatus = false;
		}
		
		String avatar = request.getParameter("avatar");
		if(avatar!=null){
			
		}else{
			
		}
		
		Integer rank = request.getParameter("rank")!=null?Integer.valueOf(request.getParameter("rank")):1;
		user.setRankId(rank);
		
		user.setJoinTime(new Timestamp(System.currentTimeMillis()));
		if(validateStatus){
			Boolean addStatus = userService.addUser(user);
			if(addStatus){
				modelAndView.addObject("code",200);
				modelAndView.addObject("msg","add success");
			}else{
				modelAndView.addObject("code",0);
				modelAndView.addObject("msg","add fail");
			}
		}else{
			modelAndView.addObject("code",0);
			modelAndView.addObject("msg","param fail");
		}
		return modelAndView;
	}
	
	@GetMapping("/admin/member/trashList")
	public ModelAndView memberTrashList(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/admin/member-del");
		return modelAndView;
	}
	
	@PostMapping("/admin/member/searchListPage")
	@ResponseBody
	public Map<String,Object> searchListPage(HttpServletRequest request){
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
	
	@GetMapping("/admin/member/del")
	@ResponseBody
	public Map<String,Object> delMember(@RequestParam("id") Integer id){
		Map<String,Object> map = new HashMap<>();
		
		if(id!=null && id>0){
			if(userService.setDelete(id)){
				map.put("code", 200);
				map.put("msg", "delete success");
			}else{
				map.put("code", 0);
				map.put("msg", "delete fail");
			}
			
		}else{
			map.put("code", 0);
			map.put("msg", "param error");
			
		}
		return map;
	}
	
	
	@PostMapping("/admin/member/getMember")
	@ResponseBody
	public Map<String,Object> getMemberById(@RequestParam("id") Integer id){
		Map<String,Object> map = new HashMap<>();
		if(id!=null && id>0){
			User user = userService.getUserById(id);
			if(user!=null){
				map.put("code", 200);
				map.put("msg", "get success");
				map.put("member", user);
			}else{
				map.put("code", 0);
				map.put("msg", "get fail");
			}
		}else{
			map.put("code", 0);
			map.put("msg", "param error");
		}
		
		return map;
	}

	
}
