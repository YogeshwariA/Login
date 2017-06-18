package com.yogu.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class MainServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session != null) {
			if (session.getAttribute("username") != null) {
				resp.setContentType("text/html");
				resp.getWriter().println("Welcome " + session.getAttribute("username"));
				req.getRequestDispatcher("/WEB-INF/main.html").forward(req, resp);
			} else {
				resp.sendRedirect("/home");
			}
		}

		// req.getRequestDispatcher("/WEB-INF/main.html").forward(req, resp);

	}
}
