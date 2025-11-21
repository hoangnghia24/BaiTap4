package vn.iotstar.service;

import vn.iotstar.entity.User;

public interface IUserService {
    // Lấy thông tin user theo username (khóa chính)
    User findByUsername(String username);

    // Đăng nhập (kiểm tra username và password - ví dụ đơn giản)
    User login(String username, String password);

    // Cập nhật thông tin profile (fullname, phone, images)
    void updateProfile(User user);
    
    // Có thể thêm các hàm cho admin quản lý user (khóa/mở khóa) sau này
    // void changeStatus(String username, boolean isActive);
    
 // Hàm đăng ký, ném Exception nếu username/email đã tồn tại
    void register(User user) throws Exception;
    
    // Hàm tìm theo email cho chức năng quên mật khẩu
    User findByEmail(String email);
}