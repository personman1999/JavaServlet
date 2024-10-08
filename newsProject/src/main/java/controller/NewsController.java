package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.NewsEntity;
import service.NewsService;

@WebServlet(name = "NewsController", urlPatterns = "/news")
public class NewsController extends HttpServlet {
	private NewsService newsService = new NewsService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<NewsEntity> listNews = newsService.getAllNews();
		
		req.setAttribute("ListNews", listNews);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
}
