package com.wd.ASFlowerWeb.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.wd.ASFlowerWeb.entity.Manager;
import com.wd.ASFlowerWeb.service.ManagerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 韦丹
 *
 * @date 2019年1月9日
 * 
 * @desc 管理员控制器
 *
 */
@Controller
@Slf4j
public class ManagerController {
	
	@Autowired
	private ManagerService managerService;
	
	/**
	 * @desc 管理员列表页
	 * @return
	 */
	@GetMapping("/admin/managerNormalIndex")
	public String normalIndex(){
		return "admin/manager-normal-list";
	}
	
	/**
	 * @desc 已删除的管理员列表页
	 * @return
	 */
	@GetMapping("/admin/managerDelIndex")
	public String delIndex(){
		return "admin/manager-del-list";
	}
	
	/**
	 * @desc 编辑管理员
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/editManager")
	public ModelAndView editManager(HttpServletRequest request){
		
		/**
		 * op=null或者未设置，表示加载新增管理员页面，并置op为0，实现新增通知，ajax传入op为2实现新增
		 * op=1表示加载修改管理员页面，并置op为1，通知前端获取需修改管理员数据，实现修改通知，ajax中传入op为3实现修改
		 * op=2  实现新增
		 * op=3实现修改
		 */
		ModelAndView modelAndView = null;
		if(request.getParameter("op")==null){
			//加载新增管理员页面
			modelAndView = new ModelAndView("admin/manager-normal-edit");
			modelAndView.addObject("op",0);
		}else{
			
			Integer op = Integer.valueOf(request.getParameter("op"));
			
			if(op == 1){
				//加载修改管理员页面
				modelAndView = new ModelAndView("admin/manager-normal-edit");
				
				Integer id = 0;
				if(request.getParameter("id")!=null){
					id = Math.max(id,Integer.valueOf(request.getParameter("id")));
				}
				modelAndView.addObject("op",1);
				modelAndView.addObject("id",id);
			}else if(op==2){
				//实现新增
				modelAndView = new ModelAndView(new MappingJackson2JsonView());
				modelAndView.addObject("code", 200);
				modelAndView.addObject("msg", "add success");
				
				//获取数据
				String name = request.getParameter("username");
				String pass = request.getParameter("pass");
				String repass = request.getParameter("repass");
				Boolean sex = request.getParameter("sex")!=null?Boolean.valueOf(request.getParameter("sex")):false;
				String email = request.getParameter("email");
				String birthday_str = request.getParameter("birthday");
				String address = request.getParameter("address");
				String phone = request.getParameter("phone");
				String qq = request.getParameter("qq");
				String wechat = request.getParameter("wechat");
				Integer rank  = request.getParameter("rank")!=null?Integer.valueOf(request.getParameter("rank")):2;
				
				Boolean validateStatus = true;
				Manager manager = new Manager();
				//简单处理数据，完善项目可添加xss过滤以及白名单过滤和去空格
				
				//名称
				if(name!=null && name.trim().length()>5){
					manager.setName(name);
				}else{
		        	validateStatus = false;
		        }
				//校验密码和确认密码
				if(pass!=null && repass!=null && pass.trim().length()>=6 && pass.trim().length()<=12 && pass.equals(repass)){
					manager.setPassword(DigestUtils.md5DigestAsHex(repass.getBytes()));
				}else{
		        	validateStatus = false;
		        }
				//校验生日
				if(birthday_str!=null && birthday_str.trim().length()==10){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
					try {
						manager.setBirthday(sdf.parse(birthday_str));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
		        	validateStatus = false;
		        }
				//验证邮箱
				String regEmail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
				Pattern p;
		        Matcher m;
		        p = Pattern.compile(regEmail);
		        m = p.matcher(email);
		        if (m.matches()){
					manager.setEmail(email);
				}else{
		        	validateStatus = false;
		        }
				//校验地址
		        if(address!=null && address.trim().length()>=4){
		        	manager.setAddress(address);
		        }else{
		        	validateStatus = false;
		        }
		        
		        //phone
		        if(phone != null && phone.trim().length()==11){
		        	manager.setPhone(phone);
		        }else{
		        	validateStatus = false;
		        }
		        
		        //qq
		        if(qq!= null && qq.trim().length()>=5&&qq.trim().length()<=11){
		        	manager.setQq(qq);
		        }else{
		        	validateStatus = false;
		        }
		        //微信
		        if(wechat!= null && wechat.trim().length()>=6 && wechat.trim().length()<=20){
		        	manager.setWechat(wechat);
		        }else{
		        	validateStatus = false;
		        }
		        
		        //sex
		        manager.setSex(sex);	
		        
		        HttpSession sessoin=request.getSession();
		        Manager loginer = (Manager) sessoin.getAttribute("manager");
		        if(loginer !=null && loginer.getRank() > rank){
		        	manager.setRank(rank);
		        }else{
		        	validateStatus = false;
		        }
		        Boolean addStatus = false;
		        if(validateStatus){
		        	addStatus = managerService.addManager(manager);
		        }
		        if(!addStatus){
		        	modelAndView.addObject("code",0);
					modelAndView.addObject("msg", "添加失败");
		        }
		        return modelAndView;
				
			}else if(op==3){
				//实现修改
				modelAndView = new ModelAndView(new MappingJackson2JsonView());
				modelAndView.addObject("code",200);
				modelAndView.addObject("msg", "更新成功");
				
				//获取数据
				
				Integer id = request.getParameter("id")!=null ? Integer.valueOf(request.getParameter("id")) : null;
				String name = request.getParameter("username");
				String pass = request.getParameter("pass");
				String repass = request.getParameter("repass");
				Boolean sex = request.getParameter("sex")!=null?Boolean.valueOf(request.getParameter("sex")):false;
				String email = request.getParameter("email");
				String birthday_str = request.getParameter("birthday");
				String address = request.getParameter("address");
				String phone = request.getParameter("phone");
				String qq = request.getParameter("qq");
				String wechat = request.getParameter("wechat");
				Integer rank  = request.getParameter("rank")!=null?Integer.valueOf(request.getParameter("rank")):2;
				
				
				Manager manager = managerService.getManagerById(id);
				//简单处理数据，完善项目可添加xss过滤以及白名单过滤和去空格
				
				//名称
				if(name!=null && name.trim().length()>5){
					manager.setName(name);
				}
				//密码和确认密码
				if(pass!=null && repass!=null && pass.trim().length()>=6 && pass.trim().length()<=12 && pass.equals(repass)){
					manager.setPassword(DigestUtils.md5DigestAsHex(repass.getBytes()));
				}
				//校验生日
				if(birthday_str!=null && birthday_str.trim().length()==10){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
					try {
						manager.setBirthday(sdf.parse(birthday_str));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//验证邮箱
				String regEmail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
				Pattern p;
		        Matcher m;
		        p = Pattern.compile(regEmail);
		        m = p.matcher(email);
		        if (m.matches()){
					manager.setEmail(email);
				}
				//校验地址
		        if(address!=null && address.trim().length()>=4){
		        	manager.setAddress(address);
		        }
		        
		        //校验phone
		        if(phone != null && phone.trim().length()==11){
		        	manager.setPhone(phone);
		        }
		        
		        //校验qq
		        if(qq!= null && qq.trim().length()>=5&&qq.trim().length()<=11){
		        	manager.setQq(qq);
		        }
		        //校验微信
		        if(wechat!= null && wechat.trim().length()>=6 && wechat.trim().length()<=20){
		        	manager.setWechat(wechat);
		        }
		        
		        //校验sex
		        manager.setSex(sex);	
		        
		        HttpSession sessoin=request.getSession();
		        Manager loginer = (Manager) sessoin.getAttribute("manager");
		        if(loginer !=null && loginer.getRank() > rank){
		        	manager.setRank(rank);
		        }
		        		        
		        
				Boolean addStatus =  managerService.updateManagerById(manager);
				
				if(!addStatus){
					modelAndView.addObject("code",0);
					modelAndView.addObject("msg", "更新失败");
				}
				//返回结果
				return modelAndView;
				
			}else{
				modelAndView = new ModelAndView("admin/manager-normal-edit");
				modelAndView.addObject("op",0);
			}
			
		}
		return modelAndView;
	}
	
	/**
	 * @desc 加载管理员列表（未删除的）
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/loadManagerList")
	@ResponseBody
	public Map<String,Object> loadManagerList(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "success");
		if(request.getParameter("op") !=null && request.getParameter("op").equals("search")){
			//功能暂未开发
		}else{
			Integer managerCount = managerService.countNormalAll();
			List<Manager> managerList = managerService.findNormalManagerAll();
			map.put("managerCount", managerCount);
			map.put("managerList", managerList);
		}
		return map;
	}
	
	/**
	 * @desc 加载删除的管理员列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/loadDelManagerList")
	@ResponseBody
	public Map<String,Object> loadDelManagerList(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "success");
		Integer managerCount = managerService.countDelAll();
		
		if(managerCount!=0){
			List<Manager> managerList = managerService.findDelManagerAll();
			map.put("managerCount", managerCount);
			map.put("managerList", managerList);
		}else{
			map.put("code", 0);
			map.put("msg", "加载失败");
		}
		return map;
	}
	
	/**
	 * @desc 根据id获取管理员信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/loadManager")
	@ResponseBody
	public Map<String,Object> loadManager(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "获取成功");
		Integer id = 0;
		if(request.getParameter("id")!=null){
			id = Integer.valueOf(request.getParameter("id"));
		}
		if(id<=0){
			map.put("code", 0);
			map.put("msg", "获取失败");
		}else{
			Manager updateManager = managerService.getManagerById(id);
			map.put("updateManager", updateManager);
		}
		return map;
	}
	
	/**
	 * @desc 删除管理员
	 * @param request
	 * @return
	 */
	@PostMapping("/admin/delManager")
	@ResponseBody
	public Map<String,Object> delManager(HttpServletRequest request){
		Integer id = Integer.valueOf(request.getParameter("id"));
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "删除成功");
		log.info(id.toString());
		if(id==null ||!managerService.delManager(id)){
			map.put("code", 0);
			map.put("msg", "删除失败");
		}
		return map;
		
	}
	
	/**
	 * @desc 恢复删除的管理员
	 * @param request
	 * @return
	 */
	@PostMapping("/admin/recoverDelManager")
	@ResponseBody
	public Map<String,Object> recoverDelManager(HttpServletRequest request){
		Integer id = Integer.valueOf(request.getParameter("id"));
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "恢复成功");
		log.info(id.toString());
		if(id==null ||!managerService.recoverDelManager(id)){
			map.put("code", 0);
			map.put("msg", "恢复失败");
		}
		return map;
		
	}
	
	/**
	 * @desc 删除管理员
	 * @param request
	 * @return
	 */
	@PostMapping("/admin/relDelManager")
	@ResponseBody
	public Map<String,Object> relDelManager(HttpServletRequest request){
		Integer id = Integer.valueOf(request.getParameter("id"));
		Map<String,Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "删除成功");
		log.info(id.toString());
		if(id==null ||!managerService.relDelManager(id)){
			map.put("code", 0);
			map.put("msg", "删除失败");
		}
		return map;
		
	}
	
	/**
	 * @desc 管理员修改密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/repassword")
	public ModelAndView repassword(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("admin/manager-password");
		
		String op = "s";
		if(request.getParameter("op")!=null){
			op = request.getParameter("op");
		}
		if(op.equals("s")){//s为显示页面，u为修改密码操作
			Integer id = 0;
			if(request.getParameter("id")!=null){
				id = Integer.valueOf(request.getParameter("id"));
			}
			modelAndView.addObject("op",op);
			modelAndView.addObject("id",id);
		}else if(op.equals("u")){
			
		}
		
		return modelAndView;
	}
}
