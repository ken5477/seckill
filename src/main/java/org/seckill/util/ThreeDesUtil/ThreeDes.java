package org.seckill.util.ThreeDesUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * Created by Ken Pan on 2017/5/2.
 */
public class ThreeDes {

    private static final String keyBytes = "E8Q1002F1C98E208FF2E863AA29350BD65AE1932A821502D9E5673CDE3C720ACFE512E2103CD40ED6BEBB101B484CAE83D537806C6CB611AEE86ED2CA8C97BBE95CF8476066D420E8E833376B850172107844D394016715B2E47E0A6EECB3E83A361FA75FA44693F90D38C6F62029FCD8EA395ED868F9D718293E9C0E63195555"; // 24字节的密钥

    public static String encryptThreeDESECB(String src) throws Exception {
        DESedeKeySpec dks = new DESedeKeySpec(keyBytes.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey securekey = keyFactory.generateSecret(dks);

        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        byte[] b = cipher.doFinal(src.getBytes());
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(b).replaceAll("\r", "").replaceAll("\n", "");

    }

    //3DESECB解密,key必须是长度大于等于 3*8 = 24 位
    public static String decryptThreeDESECB(String src) throws Exception {
        //--通过base64,将字符串转成byte数组
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytesrc = decoder.decodeBuffer(src);
        //--解密的key
        DESedeKeySpec dks = new DESedeKeySpec(keyBytes.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey securekey = keyFactory.generateSecret(dks);

        //--Chipher对象解密
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        byte[] retByte = cipher.doFinal(bytesrc);

        return new String(retByte);
    }

    public static void main(String[] args) {
        try {
            String str = "abc";
            String str2 = "abc";
            String str3 = str;
            System.out.println(str.equals("abc"));
            System.out.println(str=="abc");
            System.out.println("==========================");
            System.out.println(str==str3);
            System.out.println(str.equals(str3));
            System.out.println("==========================");
            System.out.println(str==str2);
            System.out.println(str.equals(str2));
            System.out.println("==========================");
            System.out.println(str2.equals(str3));
            System.out.println(str2==str3);
           /* str.concat();
            str.contains();
            str.charAt();
            str.substring();
            str.split();
            str.compareTo();
            str.getBytes();
            str.indexOf();
            str.hashCode();
            str.length();
            str.isEmpty();
            str.replace();
            str.toLowerCase();
            str.trim();
            str.matches();*/
            System.out.println("account:test0703 = "+encryptThreeDESECB("test0703"));
            System.out.println("pwd:yc123456 = "+encryptThreeDESECB("yc123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}