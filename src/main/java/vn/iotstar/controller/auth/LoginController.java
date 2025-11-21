package vn.iotstar.controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Kiểm tra nếu đã đăng nhập rồi thì chuyển hướng luôn
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("loginedUser") != null) {
             resp.sendRedirect(req.getContextPath() + "/home"); // Hoặc trang admin tùy role
             return;
        }
        // Hiển thị form login
        req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String u = req.getParameter("username");
        String p = req.getParameter("password");

        // Gọi service kiểm tra đăng nhập
        User user = userService.login(u, p);

        if (user != null) {
            // Đăng nhập thành công
            HttpSession session = req.getSession(true); // Tạo session mới
            
            // Lưu đối tượng user (hoặc chỉ cần username/role) vào session
            session.setAttribute("loginedUser", user); 
            session.setAttribute("loginedUsername", user.getUsername());
            // Lưu role để tiện kiểm tra quyền ở các trang khác
            session.setAttribute("isAdmin", user.isAdmin());

            // Điều hướng dựa trên vai trò
            if (user.isAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/admin/home");
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            // Đăng nhập thất bại
            req.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            // Giữ lại username để người dùng đỡ phải nhập lại
            req.setAttribute("username", u); 
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
        }
    }
}