package com.wang.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wang.dao.HomeCostDao;
import com.wang.pojo.HomeCost;
import com.wang.service.HomeCostService;
import com.wang.utils.WebUtils;

/**
 * ���ʵ�ַurl:localhost:8080/homeCost/manager/homeCostServlet
 * Servlet implementation class HomeCostServlet
 */
@WebServlet("/manager/homeCostServlet")
public class HomeCostServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private HomeCostService homeCostService = new HomeCostService();
	private static final Logger logger = LoggerFactory.getLogger(HomeCostServlet.class);
	
	HomeCostDao homeCostDao = new HomeCostDao ();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String ip = request.getRemoteAddr();
		logger.info("{}***���󷽷�{} ",ip, action);   
		if("add".equals(action)) {
			add(request, response);
		}else if("delete".equals(action)) {
			delete(request, response);
		}else if("update".equals(action)) {
			update(request, response);
		}else if("list".equals(action)) {
			list(request, response);
		}else if("getHomeCostById".equals(action)) {
			getHomeCostById(request, response);
		}else if("query".equals(action)) {
			query(request, response);
		}
	}
	
	//������Ѽ�¼
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ��ȡIP
        String ip = request.getRemoteAddr();
		//��ȡ��ҳ�ύ�Ĳ���
		String name = request.getParameter("name");
		BigDecimal money = WebUtils.bigdecimal(request.getParameter("money"), new BigDecimal(0.00));
		//��װ�������
		HomeCost homeCost_copy = new HomeCost(name,money,ip);
		homeCostDao.add_copy(homeCost_copy);
		HomeCost homeCost = new HomeCost(name,money);
		//ִ����Ӳ���������1,��ӳɹ�����֮ʧ��
		if(homeCostService.add(homeCost) == 1) {
			//ҳ���ض���
			response.sendRedirect(request.getContextPath()+"/manager/homeCostServlet?action=list");
		}else {
			logger.error("add failed :{} ", homeCost_copy.toString());
			//����ת��
			request.setAttribute("msg", "���ʧ�ܣ���ϵ����Ա");
			request.setAttribute("homeCost", homeCost);
			request.getRequestDispatcher("/cost_edit.jsp").forward(request, response);
		}
		
		
	}
	//ɾ�����Ѽ�¼
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡid
		int id  = WebUtils.parseInt(request.getParameter("id"), 0);
		//ִ��ɾ������������1,ɾ���ɹ�����֮ʧ��
		if (homeCostService.delete(id) == 1) {
			//ҳ���ض���
			response.sendRedirect(request.getContextPath()+"/manager/homeCostServlet?action=list");
		}else {
			logger.error("delete failed :{} ", id);
			//����ת��
			request.setAttribute("msg", "ɾ��ʧ�ܣ���ϵ����Ա");
			request.getRequestDispatcher("/manager/homeCostServlet?action=list").forward(request, response);
		}
		
	}
	//�޸����Ѽ�¼
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ����
		int id  = WebUtils.parseInt(request.getParameter("id"), 0);
		String name = request.getParameter("name");
		BigDecimal money = WebUtils.bigdecimal(request.getParameter("money"), new BigDecimal(0.00));
		String date = request.getParameter("date");
		//��װ
		HomeCost homeCost = new HomeCost(id,name,money,date);
		//ִ��ɾ������������1,�޸ĳɹ�����֮ʧ��
		if(homeCostService.update(homeCost) == 1) {
			response.sendRedirect(request.getContextPath()+"/manager/homeCostServlet?action=list");
		}else {
			logger.error("update failed :{} ", homeCost.toString());
			request.setAttribute("msg", "�޸�ʧ�ܣ���ϵ����Ա");
			request.setAttribute("homeCost", homeCost);
			request.getRequestDispatcher("/cost_edit.jsp").forward(request, response);
		}
	}
	//��ѯȫ�����Ѽ�¼
	protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<HomeCost> homeCost = homeCostService.list();
		request.setAttribute("homeCost", homeCost);
		request.getRequestDispatcher("/manager.jsp").forward(request, response);
		
	}
	//ͨ��id��ѯ�������Ѽ�¼
	private void getHomeCostById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id  = WebUtils.parseInt(request.getParameter("id"), 0);
		HomeCost homeCost = homeCostService.getHomeCostById(id);
		request.setAttribute("homeCost", homeCost);
		request.getRequestDispatcher("/cost_edit.jsp").forward(request, response);
	}
	//ͨ���ؼ��ʲ�ѯ
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword=request.getParameter("keyword");
		List<HomeCost> homeCost = homeCostService.query(keyword);
		request.setAttribute("homeCost", homeCost);
		request.getRequestDispatcher("/query.jsp").forward(request, response);
		
	}


}
