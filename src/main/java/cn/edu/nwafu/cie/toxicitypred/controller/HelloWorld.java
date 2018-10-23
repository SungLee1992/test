package cn.edu.nwafu.cie.toxicitypred.controller;

import cn.edu.nwafu.cie.toxicitypred.common.Result;
import cn.edu.nwafu.cie.toxicitypred.entities.User;
import cn.edu.nwafu.cie.toxicitypred.service.IRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.applet.Main;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: SungLee
 * @date: 2018-10-01 17:21
 * @description: This is a test class
 */
@RestController
@EnableAutoConfiguration
public class HelloWorld {
    @Autowired
    private IRegService regService;
    @RequestMapping("/hello")
    String home() {
        return "Hello World";
    }
    @RequestMapping("/reg")
    @ResponseBody
    Boolean reg(@RequestParam("loginPwd") String loginNum, @RequestParam("userId") String userId ){
        String pwd = creatMD5(loginNum);
        System.out.println(userId+":"+loginNum);
        regService.regUser("3", userId,pwd);
        return true;
    }
    @RequestMapping("/test")
    public void get()throws Exception {
        String[] text=new String[]{"NAME","Se","MW"};
        File file = new File("E:\\项目\\南京(新)\\服务器备份\\描述符.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        // 读取描述符标题
        String title = reader.readLine();
        List<String> titleList = Arrays.asList(title.trim().split("\\s{2,}|\t"));//将多余空格或Tab键都转为一个空格
        // 读取描述符内容
        String content = reader.readLine();
        String[] contentAry = content.trim().split("\\s{2,}|\t");
        // 获取需要的描述符
        for(String str:text){
            if(titleList.contains(str)){
                System.out.println(str+"的值为"+contentAry[titleList.indexOf(str)]);
            }else {
                System.out.println("不存在"+str);
            }
        }
    }
    private String creatMD5(String loginNum){
        // 生成一个MD5加密计算摘要
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(loginNum.getBytes());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new BigInteger(1, md.digest()).toString(16);
    }

    @RequestMapping("/result")
    public Result getResult(){
        User u = new User();
        u.setId("4");
        u.setUserid("aaa");
        u.setPwd("aaa");
        return Result.build(200,"成功",u);
    }

}
