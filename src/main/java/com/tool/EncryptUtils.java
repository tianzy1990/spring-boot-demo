package com.tool;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加解密工具类
 * 
 * @Title: EncryptUtil.java
 * @Package mall.common.utils
 * @Description: 加解密工具类
 * @author tianzy
 * @date 2017年7月14日下午3:00:52
 */
public class EncryptUtils {

	private static final String UTF8 = "utf-8";
	// 定义 加密算法,可用 DES,DESede,Blowfish
	private static final String ALGORITHM_DESEDE = "DESede";

	/**
	 * MD5数字签名
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String md5Digest(String src, int bit) throws Exception {
		// 定义数字签名方法, 可用：MD5, SHA-1
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(src.getBytes(UTF8));
		if (bit == 16) {
			return byte2HexStr(b).substring(8, 24);
		} else {
			return byte2HexStr(b);
		}
	}

	/**
	 * BASE64 加密
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String base64Encoder(String src) throws Exception {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(src.getBytes(UTF8));
	}

	/**
	 * BASE64解密
	 * 
	 * @param dest
	 * @return
	 * @throws Exception
	 */
	public static String base64Decoder(String dest) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		return new String(decoder.decodeBuffer(dest), UTF8);
	}

	/**
	 * 3DES加密
	 * 
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String desedeEncoder(String src, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(build3DesKey(key), ALGORITHM_DESEDE);
		Cipher cipher = Cipher.getInstance(ALGORITHM_DESEDE);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] b = cipher.doFinal(src.getBytes(UTF8));

		return byte2HexStr(b);
	}

	/**
	 * 3DES解密
	 * 
	 * @param dest
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String desedeDecoder(String dest, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(build3DesKey(key), ALGORITHM_DESEDE);
		Cipher cipher = Cipher.getInstance(ALGORITHM_DESEDE);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] b = cipher.doFinal(str2ByteArray(dest));

		return new String(b, UTF8);

	}

	/**
	 * 字节数组转化为大写16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2HexStr(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String s = Integer.toHexString(b[i] & 0xFF);
			if (s.length() == 1) {
				sb.append("0");
			}

			sb.append(s.toUpperCase());
		}

		return sb.toString();
	}

	/**
	 * 字符串转字节数组
	 * 
	 * @param s
	 * @return
	 */
	private static byte[] str2ByteArray(String s) {
		int byteArrayLength = s.length() / 2;
		byte[] b = new byte[byteArrayLength];
		for (int i = 0; i < byteArrayLength; i++) {
			byte b0 = (byte) Integer.valueOf(s.substring(i * 2, i * 2 + 2), 16).intValue();
			b[i] = b0;
		}

		return b;
	}

	/**
	 * 构造3DES加解密方法key
	 * 
	 * @param keyStr
	 * @return
	 * @throws Exception
	 */
	private static byte[] build3DesKey(String keyStr) throws Exception {
		byte[] key = new byte[24];
		byte[] temp = keyStr.getBytes(UTF8);
		if (key.length > temp.length) {
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			System.arraycopy(temp, 0, key, 0, key.length);
		}

		return key;
	}

	/**
	 * AES 加密
	 * 
	 * @param content
	 *            明文
	 * @param password
	 *            生成秘钥的关键字
	 * @return
	 */

	public static String aesEncrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();// 分组秘钥
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 生成秘钥
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(byteContent);
			return byte2HexStr(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AES 解密
	 * 
	 * @param content
	 *            密文
	 * @param password
	 *            生成秘钥的关键字
	 * @return
	 */

	public static String aesDecrypt(String content, String password) {
		try {
			byte[] temp = str2ByteArray(content);
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(password.getBytes());
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();// 分组秘钥
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 生成秘钥
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(temp);
			return new String(result, "utf-8");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String shaEncode(String inStr, String key) {
		String shaStr = "";
		try {
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(secretKey);
			byte[] byteArray = inStr.getBytes("UTF-8");
			byte[] shaBytes = mac.doFinal(byteArray);
			shaStr = byte2HexStr(shaBytes);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return shaStr;
	}
}
