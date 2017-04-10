package cn.catecat.user.userUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * 密码加密
 * @author 刘华
 *
 */
public class Encryption {
	//加密规则，字符串(密码+安全ID)+851860021
	public static String encryption(String password){
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] md5 = md.digest((password+501502).getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(md5);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
	}
	
	
}
