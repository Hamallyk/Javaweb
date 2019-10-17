package com.atguigu.crowd.funding.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Map;

public class CrowdFundingUtils {
		public static <K, V> boolean mapEffective(Map<K, V> map) {
			return map!=null && map.size()>0;
		}
		public static <E>boolean collectionEffective(Collection<E> conllection) {
			return conllection !=null && conllection.size()>0;
		}
		public static boolean stringEffective(String source) {
			return source!=null && source.length()>0;
		}
		public static String md5(String source) {
			if (!stringEffective(source)) {
				throw new RuntimeException( CrowdFundingConstant.MESSAGE_CODE_INVALID);
			}
			StringBuilder builder = new StringBuilder();
			char[] characters = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
			String algorithm ="MD5";
			try {
				MessageDigest digest = MessageDigest.getInstance(algorithm);
				byte[] inputBytes =source.getBytes();
				byte[] outputBytes =digest.digest(inputBytes);
				for (int i = 0; i < outputBytes.length; i++) {
					byte b =outputBytes[i];
					int lowValue =b & 15;
					int highValue =(b>>4)&15;
					char highCharacter = characters[highValue];
					char lowCharacter = characters[lowValue];	
					builder.append(highCharacter).append(lowCharacter);
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
			return builder.toString();
		}
}
