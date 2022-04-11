package com.itheima.interceptor;


import com.itheima.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PrivilegeInterceptor implements HandlerInterceptor {
    //访问目标资源之前拦截 除访问login目标资源
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws IOException {
        //将直接访问系统没有进行登录操作的用户拦截，拒绝其访问目标资源
        //逻辑；判断用户是否登录成功  本质；判断session中有没有user
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(user==null){
            //没有做登录操作
            //若没有进行login登录，则没有user对象存储到session中，故session中的user参数未被赋值，为空
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return false;
        }
        //放行   访问目标资源
        return true;
    }
}
