package vn.iotstar.controller.user;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.impl.UserServiceImpl;
import vn.iotstar.utils.UploadUtils;

@WebServlet(urlPatterns = { "/user/profile" }) // URL để truy cập
@MultipartConfig( // Cấu hình để nhận file upload
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class UserProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Gọi Service
    private IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Lấy thông tin user đang đăng nhập từ Session
        HttpSession session = req.getSession();
        // Giả sử khi login thành công, bạn đã lưu username vào session với key "loginedUsername"
        String username = (String) session.getAttribute("loginedUsername");

        if (username == null) {
            // Chưa đăng nhập -> chuyển về trang login
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 2. Lấy thông tin user mới nhất từ DB
        User user = userService.findByUsername(username);
        
        // 3. Đẩy user vào request attribute để hiển thị bên JSP
        req.setAttribute("user", user);
        
        // 4. Forward sang trang JSP profile
        req.getRequestDispatcher("/views/user/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // Xử lý tiếng Việt
        
        try {
            // 1. Lấy thông tin từ form
            String username = req.getParameter("username"); // Trường hidden trong form
            String fullname = req.getParameter("fullname");
            String phone = req.getParameter("phone");
            String oldImage = req.getParameter("oldImage"); // Tên ảnh cũ (hidden field)

            // 2. Xử lý upload ảnh mới (nếu có) dùng tiện ích
            String imagePath = UploadUtils.processUploadField(req, "imageFile", oldImage);

            // 3. Load user từ DB và cập nhật thông tin mới
            User userToUpdate = userService.findByUsername(username);
            userToUpdate.setFullname(fullname);
            userToUpdate.setPhone(phone);
            userToUpdate.setImages(imagePath);

            // 4. Gọi service để lưu xuống DB
            userService.updateProfile(userToUpdate);

            // 5. Cập nhật lại thông tin trong session nếu cần thiết (ví dụ fullname thay đổi trên header)
            // req.getSession().setAttribute("loginedUserFullname", fullname);

            // 6. Thông báo thành công và load lại trang
            req.setAttribute("message", "Cập nhật hồ sơ thành công!");
            // Gọi lại doGet để load lại dữ liệu mới nhất
            doGet(req, resp); 

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi cập nhật: " + e.getMessage());
            doGet(req, resp);
        }
    }
}