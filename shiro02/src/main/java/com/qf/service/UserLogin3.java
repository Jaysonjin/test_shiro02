package com.qf.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class UserLogin3 {

	public void yunLonginAndPers(){
		//先做认证

		//第一步：加载配置文件
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission-realm.ini");

		//第二步：由工厂得到安全管理实例对象
		SecurityManager securityManager = factory.getInstance();

		//第三步：把安全管理对象，设置到当前的运行环境中。
		SecurityUtils.setSecurityManager(securityManager);

		//第四步：得到主题对象
		Subject subject = SecurityUtils.getSubject();

		//第五步：把用户输入的用户名和密码封装到token中
		UsernamePasswordToken token = new UsernamePasswordToken("syj", "admin");

		//此处版本已经修改，添加了一条重要语句
		System.out.println("已经纠结了两个小时了，这次一定可以弄好");


		try {
			//第六步：执行认证提交
			subject.login(token);
		} catch (UnknownAccountException uae) {
			uae.printStackTrace();
		} catch (IncorrectCredentialsException ice) {
			ice.printStackTrace();
		}
		//第七步：确认是否认证通过
		boolean authenticated = subject.isAuthenticated();
		System.out.println(authenticated);
		if(authenticated){
			//如果认证通过，才做授权
			boolean permitted = subject.isPermitted("system:qf");
			System.out.println("是否有权限"+permitted);
		}

	}

	public static void main(String[] args) {
		UserLogin3 userLogin3 = new UserLogin3();
		userLogin3.yunLonginAndPers();
	}

}
