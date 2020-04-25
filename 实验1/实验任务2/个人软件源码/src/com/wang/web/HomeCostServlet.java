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
 * 访问地址url:localhost:8080/homeCost/manager/homeCostServlet
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
		logger.info("{}***请求方法{} ",ip, action);   
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
	
	//添加消费记录
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取IP
        String ip = request.getRemoteAddr();
		//获取网页提交的参数
		String name = request.getParameter("name");
		BigDecimal money = WebUtils.bigdecimal(request.getParameter("money"), new BigDecimal(0.00));
		//封装成类对象
		HomeCost homeCost_copy = new HomeCost(name,money,ip);
		homeCostDao.add_copy(homeCost_copy);
		HomeCost homeCost = new HomeCost(name,money);
		//执行添加操作，返回1,添加成功，反之失败
		if(homeCostService.add(homeCost) == 1) {
			//页面重定向
			response.sendRedirect(request.getContextPath()+"/manager/homeCostServlet?action=list");
		}else {
			logger.error("add failed :{} ", homeCost_copy.toString());
			//请求转发
			request.setAttribute("msg", "添加失败，联系管理员");
			request.setAttribute("homeCost", homeCost);
			request.getRequestDispatcher("/cost_edit.jsp").forward(request, response);
		}
		
		
	}
	//删除消费记录
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取id
		int id  = WebUtils.parseInt(request.getParameter("id"), 0);
		//执行删除操作，返回1,删除成功，反之失败
		if (homeCostService.delete(id) == 1) {
			//页面重定向
			response.sendRedirect(request.getContextPath()+"/manager/homeCostServlet?action=list");
		}else {
			logger.error("delete failed :{} ", id);
			//请求转发
			request.setAttribute("msg", "删除失败，联系管理员");
			request.getRequestDispatcher("/manager/homeCostServlet?action=list").forward(request, response);
		}
		
	}
	//修改消费记录
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取参数
		int id  = WebUtils.parseInt(request.getParameter("id"), 0);
		String name = request.getParameter("name");
		BigDecimal money = WebUtils.bigdecimal(request.getParameter("money"), new BigDecimal(0.00));
		String date = request.getParameter("date");
		//封装
		HomeCost homeCost = new HomeCost(id,name,money,date);
		//执行删除操作，返回1,修改成功，反之失败
		if(homeCostService.update(homeCost) == 1) {
			response.sendRedirect(request.getContextPath()+"/manager/homeCostServlet?action=list");
		}else {
			logger.error("update failed :{} ", homeCost.toString());
			request.setAttribute("msg", "修改失败，联系管理员");
			request.setAttribute("homeCost", homeCost);
			request.getRequestDispatcher("/cost_edit.jsp").forward(request, response);
		}
	}
	//查询全部消费记录
	protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<HomeCost> homeCost = homeCostService.list();
		request.setAttribute("homeCost", homeCost);
		request.getRequestDispatcher("/manager.jsp").forward(request, response);
		
	}
	//通过id查询该条消费记录
	private void getHomeCostById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id  = WebUtils.parseInt(request.getParameter("id"), 0);
		HomeCost homeCost = homeCostService.getHomeCostById(id);
		request.setAttribute("homeCost", homeCost);
		request.getRequestDispatcher("/cost_edit.jsp").forward(request, response);
	}
	//通过关键词查询
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword=request.getParameter("keyword");
		List<HomeCost> homeCost = homeCostService.query(keyword);
		request.setAttribute("homeCost", homeCost);
		request.getRequestDispatcher("/query.jsp").forward(request, response);
		
	}


}
