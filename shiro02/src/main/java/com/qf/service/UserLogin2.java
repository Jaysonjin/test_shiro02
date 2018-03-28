package com.qf.service;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.util.Arrays;

/**
 * 这是基于用户角色授权
 */
public class UserLogin2 {

	public void loginAndPemission(){

		//首先做认证

		//第一步，加载配置文件，得到安全管理工厂
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");

		//第二步 由工厂得到安全管理实例对象
		SecurityManager securityManager = factory.getInstance();

		//第三步：把安全管理对象，设置到当前的运行环境中
		SecurityUtils.setSecurityManager(securityManager);

		//第四步：得到主题对象
		Subject subject = SecurityUtils.getSubject();

		//第五步:把用户输入的用户名和密码封装到token中
		UsernamePasswordToken token = new UsernamePasswordToken("lisi","123456");

		try{
			//第六步：执行认证提交
			subject.login(token);
		}catch (UnknownAccountException uae){
			uae.printStackTrace();
		}catch(IncorrectCredentialsException ice){
			ice.printStackTrace();
		}

		//第七步：确认是否认证通过
		boolean authenticated = subject.isAuthenticated();
		System.out.println("认证是否通过:" + authenticated);

		//如果认证通过，才做授权
		if (authenticated){
			//基于角色授权
			boolean role1 = subject.hasRole("role1");
			System.out.println("角色判断" + role1);

			//多角色判断
			boolean[] booleans = subject.hasRoles(Arrays.asList("role1", "role2"));
			for (boolean aBoolean : booleans) {
				System.out.println(aBoolean);
			}


			//基于资源授权
			boolean permitted = subject.isPermitted("system:user:create");
			System.out.println("资源权限判断:" + permitted);

			//多个资源权限的判断
			//如果只要有一个权限没有，那么全都失败
			boolean permittedAll = subject.isPermittedAll("system:admin", "system:config:list");
			System.out.println(permittedAll);


		}else {
			System.out.println("认证失败");
		}
	}

	public static void main(String[] args) {
		UserLogin2 userLogin2 = new UserLogin2();
		userLogin2.loginAndPemission();
	}
}
