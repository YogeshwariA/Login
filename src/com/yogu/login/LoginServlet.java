package com.yogu.login;

import java.io.IOException;
import java.util.Enumeration;

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
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().println("Succeesully logout.");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		System.out.println("getContentType: " + req.getContentType());
		System.out.println("getHeaders: " + req.getHeaderNames());

		/*
		 * ServletConfig config = getServletConfig();
		 * System.out.println(config.getInitParameter("user"));
		 */
		Enumeration<?> enumerator = req.getHeaderNames();

		while (enumerator.hasMoreElements()) {
			String headerName = (String) enumerator.nextElement();
			System.out.println(headerName + ": " + req.getHeader(headerName));
		}

		System.out.println("PathInfo: " + req.getPathInfo());
		System.out.println("QueryString: " + req.getQueryString());

		System.out.println("Parameter Names:" + req.getParameterNames());
		System.out.println("Parameter Values: " + req.getParameterValues("username"));
		System.out.println("Parameter Map: " + req.getParameterMap());
		resp.setContentType("text/html");

		String name = req.getParameter("username");
		String password = req.getParameter("password");
		if ("Ajith".equals(name) && "Ajith".equals(password)) {

			HttpSession session = req.getSession();
			session.setAttribute("username", name);
			req.getRequestDispatcher("/main").forward(req, resp);

			// resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
			resp.getWriter().println("Please give correct username and password.");

		}

	}

	/*
	 * @Override protected void doGet(HttpServletRequest req,
	 * HttpServletResponse resp) throws ServletException, IOException {
	 * 
	 * // req.getRequestDispatcher("WEB-INF/index1.html").forward(req, resp); //
	 * resp.sendRedirect("/index.html");
	 * 
	 * }
	 */
	@Override
	public void destroy() {
		System.out.println("Completed");
		super.destroy();
	}

}
