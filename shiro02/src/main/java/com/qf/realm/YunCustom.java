package com.qf.realm;

import com.qf.service.PasswordUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.List;

public class YunCustom extends AuthorizingRealm {


	//认证是否登录
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		//得到用户输入的用户名
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();
		char[] password = usernamePasswordToken.getPassword();
		String pass = String.copyValueOf(password);

		String md5 = PasswordUtil.md5(pass, username);

		//从数据库得到信息
		String dbUser = "syj";
		String dbPassword = "2adc693d0c6968983fce705452519e29";// name:syj pass:admin

		//是否从数据库中找到记录
		if (!md5.equals(dbPassword)){
			throw new UnknownAccountException("用户名或密码错误");
		}

		//得到自定义的realm
		String realm = getName();

		//得到加密盐
		String salt1 = username;

		//开始认证
		//加工盐
		ByteSource salt = ByteSource.Util.bytes(salt1);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,dbPassword,salt,realm);

		return info;
	}

	//授权
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		String principal = (String) principals.getPrimaryPrincipal();//获取用户，用户名
		System.out.println(principal);

		//根据用户principal从数据库得到权限信息


		//基于资源
		List<String> power = new ArrayList<String>();
		for (String s : power) {
			power.add("system:user");
			power.add("system:menu");
			power.add("system:admin");
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(power);
		return info;
	}
}
