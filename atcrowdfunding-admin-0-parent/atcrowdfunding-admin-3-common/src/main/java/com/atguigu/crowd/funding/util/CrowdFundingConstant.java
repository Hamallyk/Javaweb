package com.atguigu.crowd.funding.util;

import java.util.HashMap;
import java.util.Map;

public class CrowdFundingConstant {
	public static final String ATTR_NAME_MESSAGE = "MESSAGE";
	public static final String ATTR_NAME_LOGIN_ADMIN = "LOGIN-ADMIN";
	public static final String ATTR_NAME_PAGE_INFO = "PAGE_INFO";
	
	public static final String MESSAGE_LOGIN_FAILED = "��¼�˺Ż����벻��ȷ����˶Ժ��ٵ�¼��";
	public static final String MESSAGE_CODE_INVALID = "���Ĳ�����Ч�ַ�������˶Ժ��ٲ�����";
	public static final String MESSAGE_ACCESS_DENIED = "���¼���ٲ���";
	public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "�˺��Ѿ����ڣ�����������";
	
	public static final Map<String, String> EXCEPTION_MESSAGE_MAP = new HashMap<>();

	static {
		EXCEPTION_MESSAGE_MAP.put("java.lang.ArithmeticException", "ϵͳ�ڽ�����ѧ����ʱ��������");
		EXCEPTION_MESSAGE_MAP.put("java.lang.RuntimeException", "ϵͳ������ʱ��������");
		EXCEPTION_MESSAGE_MAP.put("com.atguigu.crowd.funding.exception.LoginException", "��¼���������д���");
	}
}
