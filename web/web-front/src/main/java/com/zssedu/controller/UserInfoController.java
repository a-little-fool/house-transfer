package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zssedu.base.controller.BaseController;
import com.zssedu.entity.UserInfo;
import com.zssedu.result.Result;
import com.zssedu.result.ResultCodeEnum;
import com.zssedu.service.UserInfoService;
import com.zssedu.util.MD5;
import com.zssedu.vo.LoginVo;
import com.zssedu.vo.RegisterVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 周书胜
 * @date 2023年03月07 17:04
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {
    @Reference
    private UserInfoService userInfoService;

    /**
     * 会员注册
     * @param registerVo
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpServletRequest request){
        String nickName = registerVo.getNickName();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        //验证码
        String currentCode = (String)request.getSession().getAttribute("CODE");
        if(!code.equals(currentCode)) {
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }

        UserInfo userInfo = userInfoService.getByPhone(phone);
        if(null != userInfo) {
            return Result.build(null, ResultCodeEnum.PHONE_REGISTER_ERROR);
        }

        userInfo = new UserInfo();
        userInfo.setNickName(nickName);
        userInfo.setPhone(phone);
        userInfo.setPassword(MD5.encrypt(password));
        userInfo.setStatus(1);
        userInfoService.insert(userInfo);
        return Result.ok();
    }

    @GetMapping("/sendCode/{mobile}")
    public Result sendCode(@PathVariable String mobile, HttpServletRequest request) {
        String code = "1111";
        request.getSession().setAttribute("CODE", code);
        return Result.ok(code);
    }

    /**
     * 会员登录
     * @param loginVo
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();

        //校验参数
        if(StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password)) {
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }

        UserInfo userInfo = userInfoService.getByPhone(phone);
        if(null == userInfo) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_ERROR);
        }

        //校验密码
        if(!MD5.encrypt(password).equals(userInfo.getPassword())) {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }

        //校验是否被禁用
        if(userInfo.getStatus() == 0) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        request.getSession().setAttribute("USER", userInfo);

        Map<String, Object> map = new HashMap<>();
        map.put("phone", userInfo.getPhone());
        map.put("nickName", userInfo.getNickName());
        return Result.ok(map);
    }

    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        request.getSession().removeAttribute("USER");
        return Result.ok();
    }
}
