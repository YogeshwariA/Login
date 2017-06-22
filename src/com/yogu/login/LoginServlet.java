package com.yogu.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.full.model.UserDetails;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	private static List<UserDetails> users = new ArrayList<>();

	@Override
	public void init() {
		System.out.println("LoginServlet");
	}

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String method = req.getMethod();
		if ("post".equalsIgnoreCase(method)) {
			doPost(req, resp);
		} else if ("get".equalsIgnoreCase(method)) {
			doGet(req, resp);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		if ("/login".equals(req.getRequestURI())) {
			loginUser(req, resp);
		} else if ("/signup".equals(req.getRequestURI())) {
			signUpUser(req, resp);
		}

	}

	private void loginUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String emailId = req.getParameter("emailId");
		String password = req.getParameter("password");

		if ((emailId != null && !emailId.isEmpty()) && (password != null && !password.isEmpty())) {
			HttpSession session = req.getSession();
			for (UserDetails user : users) {
				if (user.getEmailId().equalsIgnoreCase(emailId)) {
					if (password != null && password.equals(user.getPassword())) {

						session.setAttribute("username", user.getFirstName() + " " + user.getLastName());
						req.getRequestDispatcher("/main").forward(req, resp);
					} else {
						resp.getWriter().println("Username or Password is wrong");
					}
					break;
				}
			}

			if (session.getAttribute("username") == null) {
				resp.getWriter().println("Your not sigup.Please singup. ");
			}
		} else {
			resp.getWriter().println("Please give correct credentials");
		}

	}

	private void signUpUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");

		String emailId = req.getParameter("emailId");

		String password = req.getParameter("password");
		String gender = req.getParameter("gender");
		String dateOfBirth = req.getParameter("birthDay");

		UserDetails user = new UserDetails();
		if (firstName != null && !firstName.isEmpty()) {
			user.setFirstName(firstName);
		} else {
			resp.getWriter().println("First name is mandatory.");
		}

		if (lastName != null && !lastName.isEmpty()) {
			user.setLastName(lastName);
		} else {
			resp.getWriter().println("Last name is mandatory.");
		}

		if ((emailId != null && !emailId.isEmpty())) {
			user.setEmailId(emailId);
		} else {
			resp.getWriter().println("Email id is mandatory.");
		}

		if (password != null && !password.isEmpty()) {
			user.setPassword(password);
		} else {
			resp.getWriter().println("password is mandatory.");
		}

		user.setGender(gender);
		user.setDateOfBirth(dateOfBirth);
		boolean isUserExists = false;
		for (UserDetails userDetail : users) {
			if (userDetail.getEmailId().equals(emailId)) {
				resp.getWriter().println("Email id is already exists ");
				isUserExists = true;
				break;
			}
		}

		if (!isUserExists && (firstName != null && !firstName.isEmpty()) && (lastName != null && !lastName.isEmpty())
				&& (emailId != null && !emailId.isEmpty()) && (password != null && !password.isEmpty())) {

			users.add(user);
			HttpSession session = req.getSession();
			session.setAttribute("username", firstName + " " + lastName);
			resp.getWriter().println("Successfully signed up.");
			resp.sendRedirect("/main");
		}
	}

	@Override
	public void destroy() {
		System.out.println("Completed");

	}

}
