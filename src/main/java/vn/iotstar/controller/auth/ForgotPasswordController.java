package vn.iotstar.controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = { "/forgot-password" })
public class ForgotPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Hiển thị form nhập email
        req.getRequestDispatcher("/views/web/forgot-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");

        // 1. Kiểm tra email có tồn tại trong hệ thống không
        User user = userService.findByEmail(email);

        if (user == null) {
            req.setAttribute("error", "Email không tồn tại trong hệ thống!");
            req.getRequestDispatcher("/views/web/forgot-password.jsp").forward(req, resp);
        } else {
            // 2. Email tồn tại -> Giả lập gửi mail
            // THỰC TẾ:
            // - Tạo một token ngẫu nhiên, lưu vào DB kèm thời gian hết hạn.
            // - Gửi email chứa link: /reset-password?token=xyz...
            
            // GIẢ LẬP: In ra console và thông báo thành công
            System.out.println("--- SIMULATION SEND EMAIL ---");
            System.out.println("To: " + user.getEmail());
            System.out.println("Subject: Reset Password");
            System.out.println("Body: Click here to reset: http://localhost:8080/reset-password?user=" + user.getUsername());
            System.out.println("-----------------------------");

            // Vì lý do bảo mật, dù email có tồn tại hay không, thông báo hiển thị nên giống nhau
            // để tránh hacker dò email. Nhưng để dễ debug lúc học, ta cứ báo thành công.
            req.setAttribute("message", "Nếu email hợp lệ, chúng tôi đã gửi hướng dẫn đặt lại mật khẩu.");
            req.getRequestDispatcher("/views/web/forgot-password.jsp").forward(req, resp);
        }
    }
}