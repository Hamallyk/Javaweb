package com.atguigu.crowd.funding.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import com.google.gson.Gson;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// ͨ��request�����ȡHttpSession����
		HttpSession session = request.getSession();
		
		// ��Session���Ի�ȡ�ѵ�¼�û�����
		Admin admin = (Admin) session.getAttribute(CrowdFundingConstant.ATTR_NAME_LOGIN_ADMIN);
		
		// ���û�л�ȡ��Admin����
		if(admin == null) {
			
			// ��һ���жϵ�ǰ�����Ƿ����첽����
			boolean checkAsyncRequestResult = CrowdFundingUtils.checkAsyncRequest(request);
			if(checkAsyncRequestResult) {
				
				// Ϊ�첽�������Ӧ����ResultEntity����
				ResultEntity<String> resultEntity = ResultEntity.failed(ResultEntity.NO_DATA, CrowdFundingConstant.MESSAGE_ACCESS_DENIED);
				
				// ����Gson����
				Gson gson = new Gson();
				
				// ��ResultEntity����ת��ΪJSON�ַ���
				String json = gson.toJson(resultEntity);
				
				// ������Ӧ����������
				response.setContentType("application/json;charset=UTF-8");
				
				// ��JSON�ַ�����Ϊ��Ӧ���ݷ���
				response.getWriter().write(json);
				
				// ��ʾ���ܷ��У�����������ִ��
				return false;
				
			}
			
			// ����ʾ��Ϣ����request��
			request.setAttribute(CrowdFundingConstant.ATTR_NAME_MESSAGE, CrowdFundingConstant.MESSAGE_ACCESS_DENIED);
			
			// ת������¼ҳ��
			request.getRequestDispatcher("/WEB-INF/admin-login.jsp").forward(request, response);
			
			return false;
		}
		
		// ���admin������Ч������м���ִ�к�������
		return true;
	}

}
