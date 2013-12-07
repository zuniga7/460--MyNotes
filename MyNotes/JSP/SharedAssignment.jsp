<%@ page language="java" contentType="text/html" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="MyNotes.utils.OracleConnect" %>

<html>

    <head><title>MyNotes: cards shared by multiple users</title></head>
    <body link=#f0f0ff alink vlink=#f0f0ff>
        <p>
            <center>
            <font size=7 face="Arial, Helvetica, sans-serif" color="#000066">
            <b>MyNotes</b><br>
            <font size=4>
                MyNotes: a UA Project Management Program</br>
            </font>
            </font>
            <hr>
            <br>

            <b>Users who share the same card:</b>
            <br>
        <%
                String name1 = "Jean-Pierre";
                String name2 = "Rick Snodgrass";
		String card1 = "Learn French";
                String name3 = "Bill Gates";
                String name4 = "Steve Jobs";
		String card2 = "Make the Big Bucks";
        %>

	    <table border="1">
	      <tr> 
	      <td> <b> Card Task Name </b> </td>
	      <td> <b> User Name </b> </td>
        <%
	      // Card 1
	      out.println("<tr>");
	      out.println("<td> " + card1 + "</td>");
	      out.println("<td> " + name1 + " </td>");

	      out.println("<tr>");
	      out.println("<td> </td>");
	      out.println("<td> " + name2 + " </td>");

	      // Card 2
	      out.println("<tr>");
	      out.println("<td> " + card2 + "</td>");
	      out.println("<td> " + name3 + " </td>");

	      out.println("<tr>");
	      out.println("<td> </td>");
	      out.println("<td> " + name4 + " </td>");

          OracleConnect oracle = new OracleConnect();
          Statement statement = oracle.getStatement();

          String query = "SELECT a1.TaskName, User "
               + "FROM AssignedTo a1, MyNotesUser u1, "
               + "AssignedTo a2, MyNotesUser u2 "
               + "WHERE a1.UserEmail = u1.UserEmail "
               + "AND a2.UserEmail = u2.UserEmail "
               + "AND a1.TaskName = a2.TaskName "
               + "AND a1.UserEmail <> a2.UserEmail";
          ResultSet result = statement.executeQuery(query);

          String prev = "";
          while(result.next()){
            String card = result.getString(1);
            String username = result.getString(2);
            out.println("<tr>");
            if(username.compareTo(prev) != 0){
                out.println("<td> " + card + "</td>");
            }
            else{
                out.println("<td></td>");
            }
            out.println("<td> " + username + " </td>");
            prev = username;
        }




        %>
	      </table>	

                <hr>
                <br><br>

                <table>
                <tr>
                <td>
                <form name="mainmenu" action=../LoginServlet method=get>
                <input type=submit name="MainMenu" value="Main Menu">
                </form>
                </td>
                </tr>
                <tr>
                <td>
                <form name="logout" action=../index.html>
                <input type=submit name="logoutMyNotes" value="Logout">
                </form>
                </td>
                </tr>
                </table>


            </center>
        </p>
    </body>
</html>


