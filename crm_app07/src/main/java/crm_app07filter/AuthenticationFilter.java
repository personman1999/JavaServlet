package crm_app07filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

@WebFilter(filterName = "authenFilter", urlPatterns = { "/users" })
public class AuthenticationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        boolean isAuthenticated = false;
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {       
                if ("authen".equals(cookie.getName()) && "ROLE_ADMIN".equals(cookie.getValue())) {
                    isAuthenticated = true; 
                    break;
                }
            }
        }

        if (!isAuthenticated) {     
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            chain.doFilter(req, res);
        }
    }
}
