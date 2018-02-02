package com.cwp.util.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomUtil {

	/**
	 * 获取随机验证码
	 * 
	 * @author Lixiaobao
	 *
	 * @param length
	 * @return
	 *
	 * @date 2017年2月20日下午3:35:11
	 */
	public static String getRandom(Integer length) {
		if (length == 6) {
			return getRandom6();
		} else if (length == 12) {
			return getRandom12();
		}
		return getRandom6();
	}

	/**
	 * 生成随机字符串
	 * 
	 * @author Lixiaobao
	 *
	 * @param length
	 * @return
	 *
	 * @date 2017年3月7日下午8:56:52
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static Set<Integer> getRandom(int length, int max) {
		Random ran = new Random();
		Set<Integer> set = new HashSet<Integer>();
		while (set.size() < length) {
			int num = ran.nextInt(max) + 1;
			set.add(num);
		}
		return set;
	}

	static String getRandom6() {
		Random random = new Random();
		return random.nextInt(899999) + 100000 + "";
	}

	static String getRandom12() {
		SimpleDateFormat sf = new SimpleDateFormat("yyMMdd");
		return sf.format(new Date()) + getRandom6();
	}
}
