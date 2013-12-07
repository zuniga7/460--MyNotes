<%@ page language="java" contentType="text/html" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="MyNotes.utils.OracleConnect" %>

<html>

    <head>
        <title>MyNotes: cards shared by multiple users</title>
        <link href="bootstrap3/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body link=#f0f0ff alink vlink=#f0f0ff>
        <p>
        <div class='container'>
    
        <div class="jumbotron">
            <h1>MyNotes</h1>
            <p>MyNotes: a UA Project Management Program</p>
        </div> 
            <center>

 

            <hr>
            <br>

            <b>Users who share the same card:</b>
            <br>

	    <table border="1">
	      <tr> 
	      <td> <b> Card Task Name </b> </td>
	      <td> <b> User Name </b> </td>
        <%

          OracleConnect oracle = new OracleConnect();
          Statement statement = oracle.getStatement();

          String query = "SELECT a1.TaskName, UserName "
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
        </div>
        </p>
    </body>
</html>


