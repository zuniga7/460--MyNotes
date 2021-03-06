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
		out.println("<title>User Addition</title>");
		out.println("<link href='bootstrap3/css/bootstrap.min.css' rel='stylesheet'>");
		out.println("<link rel='stylesheet' type='text/css' href='style.css'>");
		out.println("</head>");

		out.println("<div class='container'>");
		out.println("");
		out.println("<div class='jumbotron'>");
		out.println("	<h1>MyNotes</h1>");
		out.println("	<p>MyNotes: a UA Project Management Program</p>");
		out.println("</div>	");

		out.println("<hr>");
	}

	public void drawFooter(HttpServletRequest req, PrintWriter out) {
		out.println("</center>");
		out.println("</p>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

	private void drawActiveOptions(HttpServletRequest req, PrintWriter out) {

		out.println("<div class='panel panel-primary'>");
		out.println("<div class='panel-heading'>");
		out.println("<span class='glyphicon glyphicon-bookmark'></span> Quick Shortcuts");
		out.println("</div>");
		out.println("<div class='panel-body'>");

		out.println("<div class='row'>");
		out.println("<div class='col-xs-2 col-md-2'></div>");
		out.println("<div class='col-xs-10 col-md-10'>");
		out.println("<a href='AddCard' class='btn btn-danger btn-lg' role='button'><span class='glyphicon glyphicon-credit-card'></span> <br/>Add a Card</a>");
		out.println("<a href='FindBoards' class='btn btn-warning btn-lg' role='button'><span class='glyphicon glyphicon-folder-close'></span> <br/>Find Boards</a>");
		out.println("<a href='./JSP/SharedAssignment.jsp' class='btn btn-primary btn-lg' role='button'><span class='glyphicon glyphicon-signal'></span> <br/>User Reports</a>");
		out.println("<a href='index.html' class='btn btn-success btn-lg' role='button'><span class='glyphicon glyphicon-log-out'></span> <br/>Logout</a>");
		out.println("</div>");
		out.println("<div class='col-md-2'></div>");
		out.println("</div>    ");
		out.println("</div>");
	}

	private void drawFailOptions(HttpServletRequest req, PrintWriter out,
			int errorFlag) {
		out.println("<font size=5 face=\"Arial,Helvetica\">");
		out.println("<div class='alert alert-warning'><h4>Oh snap! You got an error!</h4>");


		// error type
		if (errorFlag == 1)
			out.println("<b>Error: e-mail does not exist.</b></br>");
		else if (errorFlag == 2)
			out.println("<b>Error: Enter the correct username.</b></br></br>");

		out.println("<font size=4>");
		out.println("<b>MyNotes: a UA Project Management Program</b><br></font>");
		out.println("</font>");

		out.println("</div>");			

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

		// get session / already logged in
		HttpSession session = req.getSession();
		String sessionEmail = (String) session.getAttribute("UserEmail");
		if (req.getParameter("MainMenu") != null) {
			// sessionEmail != null
			drawLoginSuccess(req, out);
			return;
		}

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
