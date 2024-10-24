package crm_app07controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import crm_app07service.LoginService;

@WebServlet(name = "loginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {
    private LoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = "";
        String password = "";

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie item : cookies) {
                String name = item.getName();
                String value = item.getValue();
                if ("ckEmail".equals(name)) {
                    email = value;
                }

                if ("ckPassword".equals(name)) {
                    password = value;
                }
            }
        }

        req.setAttribute("email", email);
        req.setAttribute("password", password);
        System.out.println("Loaded email: " + email);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        boolean isSuccess = loginService.checkLogin(email, password, remember, resp, req);

        if (!isSuccess) {

            resp.sendRedirect(req.getContextPath() + "/login"); 
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/users"); 

    }
}
