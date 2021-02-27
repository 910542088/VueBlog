package com.blog.shiro;

import com.blog.entity.User;
import com.blog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyRealm extends AuthorizingRealm {

    @Resource
    UserService userService;

    @Resource
    JwtUtil jwtUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        Account principal = (Account) SecurityUtils.getSubject().getPrincipal();
//        //从SimpleAuthenticationInfo(认证)中取出principal用来添加权限
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        simpleAuthorizationInfo.addRole("principal.getRole()");
//        return simpleAuthorizationInfo;
        return null;
    }

    @Override//认证处理
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken token = (JwtToken) authenticationToken;
        String userID = jwtUtil.getClaimByToken((String) token.getPrincipal()).getSubject();
        //根据token判断用户存不存在
        User user = userService.getById(Long.valueOf(userID));
        if (user == null){
            throw new UnknownAccountException("账户不存在");
        }else if (user.getStatus() == -1){
            throw new LockedAccountException("账号以锁定");
        }
        //封装数据给(基本信息,权限)-->shiro-->还可以用来判断当前的登录用户
        Account account = new Account();
        account.setUsername(user.getUsername());
        account.setId(user.getId());
        account.setAvatar(user.getAvatar());

        return new SimpleAuthenticationInfo(account,token.getCredentials(),getName());
    }
}
