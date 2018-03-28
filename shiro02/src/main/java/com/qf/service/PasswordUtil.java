package com.qf.service;

import org.apache.shiro.crypto.hash.SimpleHash;

public class PasswordUtil {

	public static String md5(String source,String salt,int hashIterations){

		//构造方法：参数一：散列算法，参数二：明文；参数三：盐；参数四：使用加密次数

		SimpleHash simpleHash = new SimpleHash("md5", source, salt, hashIterations);
		String md5 = simpleHash.toString();

		return md5;
	}

	public static String md5(String source,String salt){
		return md5(source,salt,1024);
	}

	public static String sha256(String source,String salt,int hashIterations){
		SimpleHash simpleHash = new SimpleHash("SHA-256",source,salt,hashIterations);
		String sha256 = simpleHash.toString();
		return sha256;
	}

	public static String sha256(String source,String salt){
		return sha256(source,salt,1024);
	}

	public static void main(String[] args) {
		String credentials = "admin";//密码

		String salt = "syj";
		int hashIterations = 1024;

		//安全增加
		//1散列加密次数，2盐不一样
		String md5 = md5(credentials,salt,hashIterations);
		System.out.println(md5);

//		String sha256 = sha256(credentials,salt,hashIterations);
//		System.out.println(sha256);
	}
}
