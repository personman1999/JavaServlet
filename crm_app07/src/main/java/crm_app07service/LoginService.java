package crm_app07service;

import crm_app07entity.LoginEntity;
import crm_app07repository.LoginRepository;
import utils.MD5;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class LoginService {
    private static LoginRepository loginRepository = new LoginRepository();

    public static boolean checkLogin(String email, String password, String remember, HttpServletResponse resp, HttpServletRequest req) {
        String passwordEncode = MD5.getMd5(password);
        List<LoginEntity> listUsers = loginRepository.findByEmailAndPassword(email, passwordEncode);

        boolean isSuccess = listUsers.size() > 0;

        if (isSuccess) {
            // Lưu thông tin vào cookie
            LoginEntity user = listUsers.get(0);
            String role = user.getRole_name();

            // Tạo cookie cho xác thực
            Cookie authCookie = new Cookie("authen", role);
            resp.addCookie(authCookie);

        }

        if (remember != null && isSuccess) {
            Cookie ckEmail = new Cookie("ckEmail", email);
            Cookie ckPassword = new Cookie("ckPassword", password);
            resp.addCookie(ckEmail);
            resp.addCookie(ckPassword);
        }

        return isSuccess;
    }
}
