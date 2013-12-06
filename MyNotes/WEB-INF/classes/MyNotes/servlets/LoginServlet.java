package MyNotes.servlets;

import java.util.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import MyNotes.servlets.*;
import MyNotes.utils.*;

import java.sql.*;

public class LoginServlet extends HttpServlet {

	private OracleConnect oracle = new OracleConnect();
	private Statement statement = oracle.getStatement();

	public LoginServlet() {
		super();
	}

	public void drawHeader(HttpServletRequest req, PrintWriter out) {
		out.println("<html>");
		out.println("<head>");
		out.println("<title>MyNotes logged in</title>");
		out.println("</head>");

		out.println("<body>");
		out.println("<p>");
		out.println("<center>");
		out.println("<font size=7 face=\"Arial, Helvetica, sans-serif\" color=\"#000066\">");
		out.println("<center>\n<strong>MyNotes</strong></br>");
		out.println("<font size=4>MyNotes: a UA Project Management Program");
		out.println("</center>\n<hr color=\"#000066\">");
		out.println("<br><br>");

	}

	public void drawFooter(HttpServletRequest req, PrintWriter out) {
		out.println("</center>");
		out.println("</p>");
		out.println("</body>");
		out.println("</html>");
	}

	private void drawActiveOptions(HttpServletRequest req, PrintWriter out) {

		out.println("<br>");

		out.println("<form name=\"AddCard\" action=AddCard method=get>");
		out.println("<input type=submit name=\"AddCard\" value=\"Add a Card\">");
		out.println("</form>");

		out.println("<br>");

		out.println("<form name=\"findBoards\" action=FindBoards method=get>");
		out.println("<input type=submit name=\"findBoard\" value=\"Find boards with at least a number of subscribers.\">");
		out.println("</form>");

		out.println("<br>");

		out.println("<form name=\"CardShare\" action=./JSP/SharedAssignment.jsp>");
		out.println("<input type=submit name=\"SharedAssignment\" value=\"Which users are assigned to the same card?\">");
		out.println("</form>");

		out.println("<br>");

		out.println("<form name=\"logout\" action=index.html>");
		out.println("<input type=submit name=\"logoutMyNotes\" value=\"Log out\">");
		out.println("</form>");
	}

	private void drawFailOptions(HttpServletRequest req, PrintWriter out,
			int errorFlag) {
		out.println("<font size=5 face=\"Arial,Helvetica\">");

		// error type
		if (errorFlag == 1)
			out.println("<b>Error: e-mail does not exist.</b></br>");
		else if (errorFlag == 2)
			out.println("<b>Error: Enter the correct username.</b></br>");

		out.println("<font size=4>");
		out.println("<b>MyNotes: a UA Project Management Program</b><br></font>");
		out.println("</font>");

		out.println("<hr");
		out.println("<br><br>");

		out.println("<form name=\"logout\" action=index.html>");
		out.println("<input type=submit name=\"home\" value=\"Return to Main Menu\">");
		out.println("</form>");

		out.println("<br>");
	}

	public void drawLoginSuccess(HttpServletRequest req, PrintWriter out) {
		drawHeader(req, out);
		drawActiveOptions(req, out);
		drawFooter(req, out);
	}

	public void drawLoginFail(HttpServletRequest req, PrintWriter out,
			int errorFlag) {
		drawHeader(req, out);
		drawFailOptions(req, out, errorFlag);
		drawFooter(req, out);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		String email = req.getParameter("email");
		String username = req.getParameter("username");

		try {
			String userQuery = "SELECT * FROM MyNotesUser WHERE UserEmail='"
					+ email + "'";
			ResultSet result = statement.executeQuery(userQuery);

			// user doesnt exist
			if (result.next() == false) {
				drawLoginFail(req, out, 1);
			}
			// check username
			else if (result.getString(2).compareTo(username) != 0)
				drawLoginFail(req, out, 2);

			// if login success, call the following function
			else {
				drawLoginSuccess(req, out);

				// initiate / set session
				HttpSession session = req.getSession();
				String sessionEmail = (String) session
						.getAttribute("UserEmail");

				// If attribute not found, set it into the session
				if (sessionEmail == null) {
					sessionEmail = new String(email);
					session.setAttribute("UserEmail", sessionEmail);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
