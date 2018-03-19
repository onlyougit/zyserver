package com.zyserver.util.payment;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {
    public static final String SIGN_ALGORITHMS = "SHA-1";

    public static String sign(String content,String inputCharset)  {
        try {
            MessageDigest digest = MessageDigest.getInstance(SIGN_ALGORITHMS);
            digest.update(content.getBytes(inputCharset));
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toUpperCase();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
