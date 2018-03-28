package com.qf.realm;

import com.qf.service.PasswordUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class CustomRealmMd5 extends AuthenticatingRealm {

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		//首先得到用户名和密码
		String username = (String) token.getPrincipal();//得到用户名
		//得到用户密码
		char[] credentials = (char[]) token.getCredentials();//得到密码
		String password = String.copyValueOf(credentials);//明文密码

		//把用户密码进行加密，方便和数据库密码进行比对
		String md5 = PasswordUtil.md5(password, username);

		//从数据库获取数据
		String dbUser ="syj";
		String dbPass = "2adc693d0c6968983fce705452519e29";//密码密文

		//是否从数据库找到记录
		//使用用户名进行比对
		if (!username.equals(dbUser)){
			throw new UnknownAccountException("用户不存在");
		}

		/*//使用密码进行比对
		if (!md5.equals(dbPass)){
			throw new UnknownAccountException("用户名或密码错误");
		}*/

		//得到自定义的realm
		String realmName = getName();

		//得到盐,此处将用户名作为盐，可以自定义任意字符
		String salt1 = username;
		ByteSource salt = ByteSource.Util.bytes(salt1);

		//进行信息认证
		//注意参数 第一参数为已经上面认证通过的username，
		//第二参数为数据库中密码，第三参数注意类型为ByteSource,第四参数为自定义的realm
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,dbPass,salt,realmName);

		//认证信息返回
		return info;
	}
}
