package com.full.servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
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
			Entity user = checkUserDetails(emailId);
			if (user == null) {
				resp.getWriter().println("Your not sigup.Please signup. ");
			} else {
				String dbPassword = (String) user.getProperty("Password");
				if (dbPassword.equals(password)) {
					HttpSession session = req.getSession(true);
					session.setAttribute("username", user.getProperty("FirstName"));
					req.getRequestDispatcher("/main").forward(req, resp);
				} else {
					resp.getWriter().println("Username or Password is wrong");
				}
			}

		} else {
			resp.getWriter().println("Please give correct credentials");
		}

	}

	private Entity checkUserDetails(String emailAsKey) {
		Key key = KeyFactory.createKey("Users", emailAsKey);
		try {
			return datastore.get(key);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	private void signUpUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String emailId = req.getParameter("emailId");
		String password = req.getParameter("password");
		String gender = req.getParameter("gender");
		String dateOfBirth = req.getParameter("birthDay");

		if ((firstName != null && !firstName.isEmpty()) && (lastName != null && !lastName.isEmpty())
				&& (emailId != null && !emailId.isEmpty()) && (password != null && !password.isEmpty())) {
			Entity user = checkUserDetails(emailId);

			if (user == null) {
				Entity entity = new Entity("Users", emailId);
				entity.setProperty("FirstName", firstName);
				entity.setProperty("LastName", lastName);
				entity.setProperty("EmailId", emailId);
				entity.setProperty("Password", password);
				entity.setProperty("Gender", gender);
				entity.setProperty("DateOfBirth", dateOfBirth);
				datastore.put(entity);
				HttpSession session = req.getSession();
				session.setAttribute("username", firstName + " " + lastName);
				resp.getWriter().println("Successfully signed up.");
				resp.sendRedirect("/main");
			} else {
				resp.getWriter().println("EmailId already exists");
			}
		} else {
			resp.getWriter().println("Please give correct credentials");
		}
	}

	@Override
	public void destroy() {
		System.out.println("Completed");

	}

}
