package com.learningcenter.miaosha.utils;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.util.Random;

/**
 * 描述:
 * MD5加密工具类
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 17:58
 **/
public class MD5Util {

    private static final String SALT = "1a2b3c4d";


    public static String inputPwd2formPwd(String input) {
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + input + SALT.charAt(5) + SALT.charAt(4);
        return md5WithCodec(str);
    }

    public static String formPwd2dbPwd(String input, String randomSalt) {
        String str = "" + randomSalt.charAt(0) + randomSalt.charAt(2) + input + randomSalt.charAt(5) + randomSalt.charAt(4);
        return md5WithCodec(str);
    }

    public static String inputPwd2dbPwd(String input, String randomSalt) {
        return formPwd2dbPwd(inputPwd2formPwd(input), randomSalt);
    }

    /**
     * 获取指定长度的随机数
     *
     * @param length
     * @return
     */
    public static String randomSalt(int length) {

        char[] chars = {'a', 'b', 'c', 'd', 'e', 'f'};

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int n = (int) (Math.random() * 5);
            sb.append(chars[n]);
        }
        return sb.toString();
    }


    public static String md5WithJdk(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(src.getBytes("utf-8"));
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static String md5WithCodec(String src) {
        return DigestUtils.md5Hex(src);//返回加密后的字符串
    }


    public static String md5WithGuava(String text) {
        return Hashing.md5().newHasher().putString(text, Charsets.UTF_8).hash().toString();


    }

    public static void main(String[] args) {
        System.out.println(inputPwd2formPwd("1qaz2wsx"));
//        System.out.println(md5WithGuava("zhanghua"));
//        System.out.println(md5WithJdk("zhanghua"));
//        System.out.println(randomSalt(7));

    }


    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param md5  密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = md5WithGuava(text);
        if (md5Text.equalsIgnoreCase(md5)) {
            System.out.println("MD5验证通过");
            return true;
        }

        return false;
    }


    private static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
}
