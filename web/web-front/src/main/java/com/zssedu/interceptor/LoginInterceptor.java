package com.zssedu.interceptor;

import com.zssedu.result.Result;
import com.zssedu.result.ResultCodeEnum;
import com.zssedu.util.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 周书胜
 * @date 2023年03月08 10:55
 */
public class LoginInterceptor implements HandlerInterceptor {
    // 渲染页面后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
            throws Exception {

    }

    // controller处理之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object object, ModelAndView model) throws Exception {

    }

    // controller处理之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        Object userInfo = request.getSession().getAttribute("USER");
        if (userInfo == null) {
            Result result = Result.build("未登录", ResultCodeEnum.LOGIN_AUTH);
            WebUtil.writeJSON(response, result);
            return false;
        }
        return true;
    }
}
