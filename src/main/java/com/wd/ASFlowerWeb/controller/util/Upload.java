package com.wd.ASFlowerWeb.controller.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 若尘
 *
 * 2019年2月15日
 *
 */
@Controller
@Slf4j
public class Upload {

	//图片上传相关代码
    @SuppressWarnings("finally")
	@RequestMapping("/admin/imgUpLoad")
    @ResponseBody
    public Map<String,Object> imgUpLoad(MultipartFile file, HttpServletRequest request) throws FileNotFoundException {
        Map<String,Object> map = new HashMap<>();
        Map<String,String> dataMap = new HashMap<String, String>();
    	if (file.isEmpty()) {
    		map.put("code", 404);
    		map.put("msg", "文件为空");
    		dataMap.put("src", "");
    		dataMap.put("title", "");
    		map.put("data", dataMap);
            return map;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
//        log.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
//        log.info("上传的后缀名为：" + suffixName);
        if(".jpg".equals(suffixName.trim())||".png".equals(suffixName.trim())){

            // 文件上传后的路径
            String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/static/images/upload/";
            // 解决中文问题，liunx下中文路径，图片显示问题
            // fileName = UUID.randomUUID() + suffixName;
            fileName=  UUID.randomUUID().toString().replace("-", "")+".png";
            File dest = new File(filePath+fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            boolean isTransfer = true;
            try {
                file.transferTo(dest);

            } catch (IllegalStateException e) {
                //e.printStackTrace();
                isTransfer = false;
            } catch (IOException e) {
                //e.printStackTrace();
                isTransfer = false;
            }finally{
            	if(isTransfer){
            		map.put("code", 0);
            		map.put("msg", "文件上传成功");
            		dataMap.put("src","/static/images/upload/"+fileName);
            		dataMap.put("title", "");
            		map.put("data", dataMap);
            	}else{
            		map.put("code", 500);
            		map.put("msg", "文件上传失败");
            		dataMap.put("src", "");
            		dataMap.put("title", "");
            		map.put("data", dataMap);
            	}
            	return map;
            }
        }else{
        	map.put("code", 500);
    		map.put("msg", "文件格式有误");
    		dataMap.put("src", "");
    		dataMap.put("title", "");
    		map.put("data", dataMap);
    		return map;
        }
    }

}
