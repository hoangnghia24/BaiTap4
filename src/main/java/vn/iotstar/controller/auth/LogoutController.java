package vn.iotstar.controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = { "/logout" })
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy session hiện tại, nếu không có thì trả về null (false)
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate(); // Hủy toàn bộ session
        }
        // Chuyển hướng về trang login hoặc trang chủ
        resp.sendRedirect(req.getContextPath() + "/login?message=LogoutSuccess");
    }
}