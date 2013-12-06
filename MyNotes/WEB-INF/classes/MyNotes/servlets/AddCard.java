package MyNotes.servlets;

import java.util.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

import MyNotes.servlets.*;
import MyNotes.utils.OracleConnect;

public class AddCard extends HttpServlet {

	private OracleConnect oracle = new OracleConnect();
	private Statement statement = oracle.getStatement();

	public AddCard() {
		super();
	}

	public void drawUpdateMessage(HttpServletRequest req, PrintWriter out) {
		String board_name = "CS460";
		String task_name = "Complete assignment 8";
		int creationID = 1;
		int day = 10;
		String month = "December";
		int year = 2013;
		String description = "Create a really cool program. It's so cool we've started it the day we got the assignment and will finish a week early.";

		out.println("<p><b>Board Name:</b>  " + board_name + "</p>");
		out.println("<p><b>Task Name:</b>  " + task_name + "</p>");
		out.println("<p><b>CreationID:</b>  " + creationID + "</p>");
		out.println("<p><b>Description:</b>  " + description + "</p>");
		out.println("<p><b>Day:</b>  " + day + "</p>");
		out.println("<p><b>Month:</b>  " + month + "</p>");
		out.println("<p><b>Year:</b> " + year + "</p>");

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
		out.println("<title>Activity Addition</title>");
		out.println("</head>");

		out.println("<body>");
		out.println("<p>");
		out.println("<center>");
		out.println("<font face=\"Arial, Helvetica, sans-serif\" >");
		out.println("<font color=\"#000066\">");
		out.println("<center>\n<font size=7><strong>MyNotes</strong></font></br>");
		out.println("<font size=4>MyNotes: a UA Project Management Program</font>");
		out.println("</center>\n<font size=4><hr color=\"#000066\">");
		out.println("Add new card </b><br></font>");
		out.println("</font>");

		out.println("<hr>");
	}

	public void drawFooter(HttpServletRequest req, PrintWriter out) {
		out.println("</center>");
		out.println("</p>");
		out.println("</body>");
		out.println("</html>");
	}

	public void drawAddCardInformationMenu(HttpServletRequest req,
			PrintWriter out) {
		out.println("<form name=\"AddCard\" action=AddCard method=get>");
		out.println("<font size=3 face=\"Arial, Helvetica, sans-serif\" color=\"#000066\">");
		out.println("<p>");
		out.println("<b>Board Name:</b>");
		out.println("<input type=text name=\"boardname\" required>");
		out.println("<br>");
		out.println("</p>");

		out.println("<p>");
		out.println("<b>Task Name: </b>");
		out.println("<input type=text name=\"taskname\" required>");
		out.println("<br>");
		out.println("</p>");

		out.println("<p>");
		out.println("<b>Description: </b>");
		out.println("<input type=text name=\"description\" required>");
		out.println("<br>");
		out.println("</p>");

		out.println("<p>");
		out.println("<b>Day:</b>");
		out.println("<input type=int name=\"day\" required>");
		out.println("<br>");
		out.println("</p>");

		out.println("<p>");
		out.println("<b>Month:</b>");
		out.println("<input type=text name=\"month\" required>");
		out.println("<br>");
		out.println("</p>");

		out.println("<p>");
		out.println("<b>Year:</b>");
		out.println("<input type=int name=\"year\" required>");
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
		out.println("<form name=\"Cancel\" action=AddCard method=get>");
		out.println("<input type=submit name=\"Cancel\" value=\"Cancel\">&nbsp&nbsp");
		out.println("</form>");
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td>");
		out.println("<form name=\"MainMenu\" action=LoginServlet>");
		out.println("<input type=submit name=\"MainMenu\" value=\"Return to Main Menu\">");
		out.println("</form>");
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td>");
		out.println("<form name=\"logout\" action=index.html>");
		out.println("<input type=submit name=\"logoutMyNotes\" value=\"Logout\">");
		out.println("</form>");
		out.println("</p>");
		out.println("</td>");
		out.println("</tr>");

		out.println("</table>");
		out.println("<br><br><br>");
	}

	private String niceForm(){
		return "";
	}
	private String formGroup(String label) {
		String formGroup = ""
				+ "<div class='form-group'>"
				+ "<label for='input"+label+"3' class='col-sm-2 control-label'>"+label+"</label>"
				+ "<div class='col-sm-10'>"
				+ "<input type='text' class='form-control' id='input"+label+"3' name='"+label.toLowerCase()+"' placeholder='"+label+"' required>" 
				+ "</div></div>";

		return formGroup;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		drawHeader(req, out);

		if (req.getParameter("Submit") == null) {
			drawAddCardInformationMenu(req, out);
		}

		else {

			HttpSession session = req.getSession();
			String sessionEmail = (String) session.getAttribute("UserEmail");

			// check if email attribute is set...
			if (sessionEmail == null) {
				return; // not logged in
			}

			String boardName = req.getParameter("boardname");
			String taskName = req.getParameter("taskname");
			String description = req.getParameter("description");
			String day = req.getParameter("day");
			String month = req.getParameter("month");
			String year = req.getParameter("year");

			try {
				ResultSet boardResult = statement
						.executeQuery("SELECT BoardName FROM Board WHERE BoardName='"
								+ boardName + "'");

				// invalid board name
				if (boardResult.next() == false) {
					// error
					return;
				}

				// generate a new Creation ID
				ResultSet resultID = statement
						.executeQuery("SELECT MAX(CreationID) FROM Creation");
				int newCreationID = 1; // ID for possibly being the first
										// creation
				if (resultID.next()) // move cursor to 1st row if possible
					newCreationID = resultID.getInt(1) + 1; // the ID for our
															// new creation

				try {
					// insert into card
					statement.executeUpdate("INSERT INTO Card VALUES ('"
							+ boardName + "', " + taskName + "', "
							+ description + "', " + day + "', " + month + "', "
							+ year + ")");

					// insert into creates
					statement.executeUpdate("INSERT INTO Creation VALUES ('"
							+ sessionEmail + "', " + newCreationID + ")");

					out.println("\nThe card was successfully created!");

				} catch (SQLException e) {
					// there is a duplicate
					out.println("\nCould Not Create: Duplicate Card!");
					return;
				}
			}
			// duplicate info
			catch (SQLException e) {
				e.printStackTrace();
			}

			drawUpdateMessage(req, out);
		}

		drawFooter(req, out);
	}
}