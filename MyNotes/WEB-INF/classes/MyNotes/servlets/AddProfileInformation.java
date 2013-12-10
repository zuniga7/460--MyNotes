package MyNotes.servlets;

import java.util.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

import MyNotes.servlets.*;
import MyNotes.utils.OracleConnect;

public class AddProfileInformation extends HttpServlet {

	private OracleConnect oracle = new OracleConnect();
	private Statement statement = oracle.getStatement();

	public AddProfileInformation() {
		super();
	}

	public void drawUpdateMessage(HttpServletRequest req, PrintWriter out,
			String email, String username) {

		out.println("New user added!");

		out.println("<br>");

		out.println("<p><b>Email:</b>  " + email + "</p>");
		out.println("<p><b>Username:</b>  " + username + "</p>");

		out.println("<br>");

		out.println("<form name=\"MainMenu\" action=LoginServlet>");
		out.println("<input type=submit name=\"MainMenu\" value=\"MainMenu\">");
		out.println("</form>");

		out.println("<br>");

		out.println("<form name=\"logout\" action=index.html>");
		out.println("<input type=submit name=\"logoutMyNotes\" value=\"Logout\">");
		out.println("</form>");
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

	public void drawAddProfileInformationMenu(HttpServletRequest req,
			PrintWriter out) {
		out.println("<div class='panel panel-primary'>");
		out.println("<div class='panel-heading'>");
		out.println("<span class='glyphicon glyphicon-user'></span> New User!");
		out.println("</div>");
		out.println("<div class='panel-body'>");
		
		out.println("<form name=\"AddProfileInformation\" action=AddProfileInformation method=get>");
		out.println("<font size=3 face=\"Arial, Helvetica, sans-serif\" color=\"#000066\">");
		out.println("<p>");
		out.println("<b>Email Address:</b>");
		out.println("<input type=text name=\"email\">");
		out.println("<br>");
		out.println("</p>");

		out.println("<p>");
		out.println("<b>User: </b>");
		out.println("<input type=text name=\"user\">");
		out.println("<br>");
		out.println("</p>");

		out.println("<table>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<input type=submit name=\"Submit\" value=\"Insert\">&nbsp&nbsp");
		out.println("</td>");
		out.println("</tr>");

		out.println("</form>");

		out.println("<tr>");
		out.println("<td>");
		out.println("<form name=\"Cancel\" action=index.html method=get>");
		out.println("<input type=submit name=\"Cancel\" value=\"Cancel\">&nbsp&nbsp");
		out.println("</form>");
		out.println("</td>");
		out.println("</tr>");

		out.println("</table>");
		
		out.println("</div>");
	}

	private void drawErrorDuplicate(HttpServletRequest req, PrintWriter out) {
		out.println("<font size=5 face=\"Arial,Helvetica\">");

		out.println("<div class='alert alert-warning'><h4>Oh snap! You got an error!</h4>");
		out.println("<p>Error: User already exists!</p>");
		out.println("</div>");

		drawAddProfileInformationMenu(req, out);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		drawHeader(req, out);

		if (req.getParameter("Submit") == null) {
			drawAddProfileInformationMenu(req, out);
		}
		// submit form
		else {

			String email = req.getParameter("email");
			String username = req.getParameter("user");

			try {
				statement.executeUpdate("INSERT INTO MyNotesUser VALUES('"
						+ email + "', '" + username + "')");

				drawUpdateMessage(req, out, email, username);

			} catch (SQLException e) {
				drawErrorDuplicate(req, out);
				drawFooter(req, out);
			}

		}

		drawFooter(req, out);
	}

}
