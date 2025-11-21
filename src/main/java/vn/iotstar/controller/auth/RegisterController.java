package vn.iotstar.controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Hiển thị form đăng ký
        req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String email = req.getParameter("email");
        String fullname = req.getParameter("fullname");

        // 1. Validate cơ bản ở Controller (ví dụ: mật khẩu xác nhận)
        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            // Giữ lại dữ liệu đã nhập
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.setAttribute("fullname", fullname);
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
            return;
        }

        // 2. Tạo đối tượng User
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // ⚠️ Nhắc lại: Cần mã hóa ở Service
        newUser.setEmail(email);
        newUser.setFullname(fullname);

        try {
            // 3. Gọi service đăng ký (Service sẽ kiểm tra trùng username/email)
            userService.register(newUser);
            
            // 4. Thành công -> Chuyển sang trang login với thông báo
            req.getSession().setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
            resp.sendRedirect(req.getContextPath() + "/login");

        } catch (Exception e) {
            // 5. Thất bại (ví dụ: trùng username) -> Quay lại form đăng ký và báo lỗi
            req.setAttribute("error", e.getMessage());
             // Giữ lại dữ liệu đã nhập
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.setAttribute("fullname", fullname);
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
        }
    }
}