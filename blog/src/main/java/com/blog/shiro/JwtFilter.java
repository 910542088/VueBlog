package com.blog.shiro;

import cn.hutool.json.JSONUtil;
import com.blog.common.Result;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends AuthenticatingFilter {

    /**
     * 创建生成jwtToken的过滤器,并过滤过期token
     */
    @Resource
    JwtUtil jwtUtil;//token生成&判断工具

    @Override//创建token
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("jwt");
        if (StringUtils.isEmpty(jwt)){
            return null;
        }
        return new JwtToken(jwt);
    }

    @Override//过滤无效jwt
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("jwt");
        if (StringUtils.isEmpty(jwt)){
            return true;//放行让注解拦截->登录
        }
        Claims claim = jwtUtil.getClaimByToken(jwt);
        if (claim == null || jwtUtil.isTokenExpired(claim.getExpiration())){
            throw new ExpiredCredentialsException("token失效,清从新登录!");
        }
        return executeLogin(servletRequest,servletResponse);
        //执行登录-->会经过MyRealm处理-->onLoginSuccess(出现的异常处理一下给前端)
    }

    @Override//登录异常处理返回前端
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse resp = (HttpServletResponse) response;

        Throwable throwable = e.getCause() == null ? e : e.getCause();
        String json = JSONUtil.toJsonStr(Result.fail(throwable.getMessage()));

        try {
            resp.getWriter().print(json);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }
}
