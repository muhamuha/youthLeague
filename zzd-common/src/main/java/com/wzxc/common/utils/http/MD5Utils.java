package com.wzxc.common.utils.http;

import java.security.MessageDigest;

public class MD5Utils {

	public static String EncoderByMd5(String buf) {  
	    try {  
	        MessageDigest digist = MessageDigest.getInstance("MD5");  
	        byte[] rs = digist.digest(buf.getBytes("UTF-8"));  
	        StringBuffer digestHexStr = new StringBuffer();  
	        for (int i = 0; i < 16; i++) {  
	            digestHexStr.append(byteHEX(rs[i]));  
	        }  
	        return digestHexStr.toString();  
	    } catch (Exception e) {  
	       // logger.error(e.getMessage(), e);  
	    
	    }  
	    return null;  
	  
	}  
	
	public static String byteHEX(byte ib) {  
	    char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
	    char[] ob = new char[2];  
	    ob[0] = Digit[(ib >>> 4) & 0X0F];  
	    ob[1] = Digit[ib & 0X0F];  
	    String s = new String(ob);  
	    return s;  
	}  
	
	
}
