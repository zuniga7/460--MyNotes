package MyNotes.servlets;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import MyNotes.servlets.*;
import MyNotes.utils.OracleConnect;

import java.sql.*;

public class FindBoards extends HttpServlet {
	private OracleConnect oracle = new OracleConnect();
	private Statement statement = oracle.getStatement();

	public FindBoards() {
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
		out.println("<br>");
		out.println("<hr>");
		out.println("<br>");

		out.println("<p>");
		out.println("<form name=\"MainMenu\" action=LoginServlet method=get>");
		out.println("<input type=submit name=\"MainMenu\" value=\"Main Menu\">");
		out.println("</form>");
		out.println("</p>");

		out.println("<p>");
		out.println("<form name=\"logout\" action=index.html>");
		out.println("<input type=submit name=\"logoutMyNotes\" value=\"Logout\">");
		out.println("</form>");
		out.println("</p>");

		out.println("<br><br>");
		out.println("</center>");
		out.println("</p>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

	public void drawGetUser(HttpServletRequest req, PrintWriter out) {

		System.out.println("CSC460: in drawGetUser___");

		out.println("<form name=\"userSearch\" action=FindBoards method=get>");
		out.println("Enter max number of subscribers: ");
		out.println("<input type=text size=30 name=\"numUsers\">");
		out.println("<input type=submit name=\"findBoards\" value=\"Find\" >");
		out.println("</form>");

	}

	public void drawShowInfo(HttpServletRequest req, PrintWriter out) {
		String numUsers = req.getParameter("numUsers");

		out.println("<p><b>Boards that have at most " + numUsers
				+ " users:</b>");
		/*
		 * TODO: Execute the query and print out the results rather than hard
		 * coding the results
		 */

		String query = "SELECT BoardName FROM Subscribes GROUP BY BoardName HAVING COUNT(*) <= " + numUsers;
		ResultSet result;
		try {
			result = statement.executeQuery(query);

			// if there are no rows in the table
			if (result.isBeforeFirst() == false) {
				out.println("There is no board with few enough subscribers for you.\n"
						+ "Please select another amount and try again.");
				return;
			}

			while (result.next()) {
				out.println("<p>"+result.getString("BoardName")+"</p>");
			}

		} catch (SQLException e) {
			out.println("Error:  Request could not be carried out.");
			e.printStackTrace();
		}

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		drawHeader(req, out);

		if (req.getParameter("findBoards") == null) {
			drawGetUser(req, out);
		} else {
			drawShowInfo(req, out);
			System.out.println("CSC460: inside doGet FindCommonPlaces____"
					+ req.getParameter("numUsers") + "___");
		}

		drawFooter(req, out);
	}
}
