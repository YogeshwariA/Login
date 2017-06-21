package com.yogu.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class HomeServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("/home".equals(req.getRequestURI())) {
			goToLoginHtml(req, resp);
		} else if ("/main".equals(req.getRequestURI())) {
			welcomeUser(req, resp);
		} else if ("/gotosignup".equals(req.getRequestURI())) {
			req.getRequestDispatcher("/WEB-INF/signup.html").forward(req, resp);
		}

	}

	private void welcomeUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("username") == null) {
			req.getRequestDispatcher("/home").forward(req, resp);
		} else {
			resp.getWriter().println("Welcome!" + session.getAttribute("username"));

			req.getRequestDispatcher("/WEB-INF/main.html").forward(req, resp);
		}
	}

	private void goToLoginHtml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/login.html").forward(req, resp);
	}

	
}
