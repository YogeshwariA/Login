package com.yogu.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	@Override
	public void init() {
		System.out.println("LoginServlet");
	}

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String method = req.getMethod();
		if ("post".equalsIgnoreCase(method)) {
			doPost(req, resp);
		} else if("get".equalsIgnoreCase(method)) {
			System.out.println("Loging get method");
			doGet(req, resp);
		}

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//		resp.sendRedirect("/index1.html");
		//req.getRequestDispatcher("/WEB-INF/index.html")
	}
	

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		String name = req.getParameter("username");
		String password = req.getParameter("password");
		if ("Ajith".equals(name) && "Ajith".equals(password)) {

			HttpSession session = req.getSession();
			session.setAttribute("username", name);
			req.getRequestDispatcher("/WEB-INF/main.html").forward(req, resp);

		} else {

			resp.getWriter().println("Please give correct username and password.");

		}

	}

	@Override
	public void destroy() {
		System.out.println("Completed");
		super.destroy();
	}

}
