package crm_app07controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;

import crm_app07service.UserService;
import crm_app07service.RoleService;
import crm_app07service.TaskService;
import crm_app07entity.UserEntity;
import crm_app07entity.RoleEntity;
import crm_app07entity.TaskEntity;

@WebServlet(name = "UserController", urlPatterns = { "/users", "/user-add", "/user-update", "/user-detail" })
public class UserController extends HttpServlet {
	private UserService userService = new UserService();
	private RoleService roleService = new RoleService();
	private TaskService taskService = new TaskService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		if (path.equals("/user-update")) {
			updateUserForm(req, resp);
		} else if (path.equals("/users")) {
			loadUsers(req, resp);
		} else if (path.equals("/user-add")) {
			addUser(req, resp);
		} else if (path.equals("/user-detail")) {
			showUserDetail(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

		if (path.equals("/user-update")) {
			updateUser(req, resp);
		} else if (path.equals("/user-add")) {
			addUserPost(req, resp);
		}
	}

	private void addUserPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String fullname = req.getParameter("fullname");
		int roleId = Integer.parseInt(req.getParameter("role") != null ? req.getParameter("role") : "0");

		userService.insertUser(email, password, fullname, roleId);

		resp.sendRedirect(req.getContextPath() + "/users");
	}

	private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String currentUserRole = taskService.getRoleFromCookies(req);

		List<RoleEntity> listRole;

		// Kiểm tra nếu người dùng hiện tại là ROLE_MANAGER
		if ("ROLE_MANAGER".equals(currentUserRole)) {
			RoleEntity roleUser = roleService.getRoleById(3);
			listRole = List.of(roleUser);
		} else {

			listRole = roleService.getAllRoles();
		}

		req.setAttribute("listRole", listRole);
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
	}

	private void loadUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String currentUserRole = taskService.getRoleFromCookies(req);

		if (id != null) {
			try {
				int userId = Integer.parseInt(id);
				UserEntity userToDelete = userService.getUserById(userId);

				// Kiểm tra nếu userToDelete là null
				if (userToDelete == null) {
					resp.sendRedirect(req.getContextPath() + "/users");
					return;
				}

				// Kiểm tra quyền của ROLE_ADMIN và vai trò của người dùng
				if ("ROLE_ADMIN".equals(currentUserRole)) {
					// ROLE_ADMIN có thể xóa bất kỳ người dùng nào
					userService.deleteUserById(userId);
				} else if ("ROLE_MANAGER".equals(currentUserRole) && "ROLE_USER".equals(userToDelete.getRolename())) {
					// ROLE_MANAGER chỉ có thể xóa ROLE_USER
					userService.deleteUserById(userId);
				} else {
					req.getRequestDispatcher("error-403.jsp").forward(req, resp);
					return;
				}
			} catch (NumberFormatException e) {
				req.getRequestDispatcher("error-403.jsp").forward(req, resp);
				return;
			}
		}

		// Lấy danh sách người dùng và hiển thị
		List<UserEntity> listUser = userService.getAllUserTable();
		req.setAttribute("listUser", listUser);
		req.getRequestDispatcher("user-table.jsp").forward(req, resp);
	}

	private void updateUserForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String currentUserRole = taskService.getRoleFromCookies(req);
		String currentUserEmail = userService.getEmailFromCookies(req);

		if (id != null) {
			UserEntity user = userService.getUserById(Integer.parseInt(id));

			if (user == null) {
				resp.sendRedirect(req.getContextPath() + "/users");
				return;
			}

			if ("ROLE_ADMIN".equals(currentUserRole)) {
				// ROLE_ADMIN có thể sửa tất cả người dùng
				List<RoleEntity> listRole = roleService.getAllRoles();
				req.setAttribute("user", user);
				req.setAttribute("listRole", listRole);
				req.getRequestDispatcher("user-update.jsp").forward(req, resp);
			} else if ("ROLE_MANAGER".equals(currentUserRole)) {
				if (currentUserEmail.equals(user.getEmail())) {
					// Sửa chính mình: hiển thị ROLE_MANAGER và ROLE_USER
					RoleEntity roleManager = roleService.getRoleById(2);
					RoleEntity roleUser = roleService.getRoleById(3);
					List<RoleEntity> listRole = List.of(roleManager, roleUser);

					req.setAttribute("user", user);
					req.setAttribute("listRole", listRole);
					req.getRequestDispatcher("user-update.jsp").forward(req, resp);
				} else if ("ROLE_USER".equals(user.getRolename())) {
					// Sửa ROLE_USER: chỉ hiển thị ROLE_USER
					RoleEntity roleUser = roleService.getRoleById(3);
					List<RoleEntity> listRole = List.of(roleUser);

					req.setAttribute("user", user);
					req.setAttribute("listRole", listRole);
					req.getRequestDispatcher("user-update.jsp").forward(req, resp);
				} else {

					req.getRequestDispatcher("error-403.jsp").forward(req, resp);
				}
			} else {

				req.getRequestDispatcher("error-403.jsp").forward(req, resp);
			}
		}
	}

	private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String fullname = req.getParameter("fullname");
		int roleId = Integer.parseInt(req.getParameter("role") != null ? req.getParameter("role") : "0");

		// Lấy thông tin người dùng hiện tại
		String currentUserRole = taskService.getRoleFromCookies(req);
		String currentUserEmail = userService.getEmailFromCookies(req);

		// Nếu là ROLE_ADMIN, không thay đổi cookie authen
		if ("ROLE_ADMIN".equals(currentUserRole)) {
			userService.updateUser(email, password, fullname, roleId, id);
			resp.sendRedirect(req.getContextPath() + "/users");
			return;
		}

		// Nếu là ROLE_MANAGER và thay đổi vai trò của bản thân
		if ("ROLE_MANAGER".equals(currentUserRole)) {
			UserEntity currentUser = userService.getUserById(id);

			if (currentUser.getEmail().equals(currentUserEmail)) {
				userService.updateUser(email, password, fullname, roleId, id);

				// Lấy lại vai trò mới sau khi cập nhật
				UserEntity updatedUser = userService.getUserById(id);
				String newRole = updatedUser.getRolename();

				// Nếu vai trò thay đổi, cập nhật cookie
				if (!currentUserRole.equals(newRole)) {
					Cookie authCookie = new Cookie("authen", newRole);
					authCookie.setMaxAge(60);
					resp.addCookie(authCookie);
				}

				resp.sendRedirect(req.getContextPath() + "/tasks");
			} else {
				req.getRequestDispatcher("error-403.jsp").forward(req, resp);
			}
		} else {
			req.getRequestDispatcher("error-403.jsp").forward(req, resp);
		}
	}

	private void showUserDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    // Lấy id từ tham số trong request
		 String userId = req.getParameter("id");

		 System.out.println("kiem tra " + userId);
	    if (userId != null) {
	    	   List<TaskEntity> task = userService.userDetail(Integer.parseInt(userId));
	    	   Map<String, List<TaskEntity>> groupedTasks = taskService.groupTasksByStatus(task);
	    
	    List<UserEntity> listUser = taskService.getAllUserTable();
	    req.setAttribute("user", listUser.isEmpty() ? null : listUser.get(0));
	    req.setAttribute("groupedTasks", groupedTasks);
	    req.getRequestDispatcher("user-details.jsp").forward(req, resp);
	    }
	}
	
	
	
	
}
