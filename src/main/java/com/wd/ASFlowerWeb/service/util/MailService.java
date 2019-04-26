package com.wd.ASFlowerWeb.service.util;

import java.io.File;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 韦丹
 *
 * 2019年2月23日
 *
 */
@Service
@Slf4j
//服务层
public class MailService {
	
	@Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;
 
// 邮箱发送服务
    public void sendSimpleMail(String to,String title,String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
        log.info("邮件发送成功");
    }
    
// 暂时用不到 带附件的发送
    public void sendAttachmentsMail(String to, String title, String cotent, List<File> fileList){
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(cotent);
            String fileName = null;
            for (File file:fileList) {
                fileName = MimeUtility.encodeText(file.getName(), "utf-8", "B");
                helper.addAttachment(fileName, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(message);
        log.info("邮件发送成功");
    }
}
