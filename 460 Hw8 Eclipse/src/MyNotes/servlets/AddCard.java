package MyNotes.servlets;

import java.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import MyNotes.servlets.*;
import MyNotes.utils.OracleConnect;

public class AddCard extends HttpServlet
{

	private OracleConnect oracle = new OracleConnect();
	private Statement statement = oracle.getStatement();

	public AddCard()
	{
		super();
	}

	public void drawUpdateMessage(HttpServletRequest req, PrintWriter out,
			int newID)
	{
		String board_name = (String) req.getAttribute("boardname");
		String task_name = (String) req.getAttribute("taskname");
		int creationID = newID;
		int day = (int) req.getAttribute("day");
		String month = (String) req.getAttribute("month");
		int year = (int) req.getAttribute("year");
		String description = (String) req.getAttribute("description");

		out.println("<div class='panel panel-primary'>");
		out.println("<div class='panel-heading'>");
		out.println("<span class='glyphicon glyphicon-credit-card'></span> Add a new Card");
		out.println("</div>");
		out.println("<div class='panel-body'>");

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

		out.println("</div>");
	}

	public void drawHeader(HttpServletRequest req, PrintWriter out)
	{
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

	public void drawFooter(HttpServletRequest req, PrintWriter out)
	{
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

	public void drawAddCardInformationMenu(HttpServletRequest req,
			PrintWriter out)
	{

		out.println("<div class='panel panel-primary'>");
		out.println("<div class='panel-heading'>");
		out.println("<span class='glyphicon glyphicon-credit-card'></span> Add a new Card");
		out.println("</div>");
		out.println("<div class='panel-body'>");
		out.println("<form class='form-horizontal' role='form' name='login' action='AddCard' method='get'>");

		out.println(niceForm());

		out.println("<div class='form-group'>");
		out.println("<div class='col-sm-offset-2 col-sm-10'>");
		out.println("<button type='submit' name='Submit' class='btn btn-primary'>Insert</button>");
		out.println("<form name='Cancel' action='AddCard' method='get'>");
		out.println("<button class='btn btn-danger' type='submit' name='Cancel'>Cancel</button>");
		out.println("</form>");
		out.println("</div>");
		out.println("</div>");
		out.println("</form>");

		out.println("<hr>");
		out.println("<table>");
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
		out.println("</td>");
		out.println("</tr>");

		out.println("</table>");

		out.println("</div>");
	}

	private String niceForm()
	{
		String form = "";

		form += formGroup("BoardName");
		form += formGroup("TaskName");
		form += formGroup("Description");
		form += formGroup("Day");
		form += formGroup("Month");
		form += formGroup("Year");

		return form;
	}

	private String formGroup(String label)
	{
		String formGroup = "" + "<div class='form-group'>"
				+ "<label for='input" + label
				+ "3' class='col-sm-2 control-label'>" + label + "</label>"
				+ "<div class='col-sm-10'>"
				+ "<input type='text' class='form-control' id='input" + label
				+ "3' name='" + label.toLowerCase() + "' placeholder='" + label
				+ "' required>" + "</div></div>";

		return formGroup;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		drawHeader(req, out);

		if (req.getParameter("Submit") == null)
		{
			drawAddCardInformationMenu(req, out);
		}

		else
		{

			HttpSession session = req.getSession();
			String sessionEmail = (String) session.getAttribute("UserEmail");

			// check if email attribute is set...
			if (sessionEmail == null)
			{
				return; // not logged in
			}

			String boardName = req.getParameter("boardname");
			String taskName = req.getParameter("taskname");
			String description = req.getParameter("description");
			String dayString = req.getParameter("day");
			String monthString = req.getParameter("month");
			String yearString = req.getParameter("year");

			try
			{
				ResultSet boardResult = statement
						.executeQuery("SELECT BoardName FROM Board WHERE BoardName='"
								+ boardName + "'");

				// invalid board name
				if (boardResult.next() == false)
				{
					out.println("<div class='alert alert-warning'><h4>Oh snap! You got an error!</h4>");
					out.println("<p>Invalid Board Name</p>"); // error
					out.println("</div>");
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

				try
				{
					int day = Integer.parseInt(dayString);
					int month = Integer.parseInt(monthString);
					int year = Integer.parseInt(yearString);

					// insert into card
					statement.executeUpdate("INSERT INTO Card VALUES ('"
							+ boardName + "', '" + taskName + "', "
							+ newCreationID + ", '" + description + "', " + day
							+ ", " + month + ", " + year + ")");

					// insert into creates
					statement.executeUpdate("INSERT INTO Creation VALUES ('"
							+ sessionEmail + "', " + newCreationID + ")");

					out.println("<br><p>The card was successfully created!</p>");

					drawUpdateMessage(req, out, newCreationID);

				} catch (SQLException e)
				{
					// there is a duplicate
					out.println("<div class='alert alert-warning'><h4>Oh snap! You got an error!</h4>");
					out.println("<p>Could Not Create: Duplicate Card TaskName!</p>");
					out.println("</div>");
					return;
				} catch (NumberFormatException e)
				{
					out.println("<div class='alert alert-warning'><h4>Oh snap! You got an error!</h4>");
					out.println("<p>Could Not Create: Inserted a string where an integer was was asked!</p>");
					out.println("</div>");
					return;
				}
			}
			// duplicate info
			catch (SQLException e)
			{
				e.printStackTrace();
			}

		}

		drawFooter(req, out);
	}
}