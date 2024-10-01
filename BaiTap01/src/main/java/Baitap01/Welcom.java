package Baitap01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "welcom07", urlPatterns =("/welcom"))
public class Welcom extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException {
    	PrintWriter writer = resp.getWriter();
		writer.append("Welcom !!");
		writer.close();
    }
}
