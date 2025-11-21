package vn.iotstar.service.impl;

import vn.iotstar.dao.IUserDao;
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;

public class UserServiceImpl implements IUserService {

    // Khởi tạo DAO để sử dụng
    private IUserDao userDao = new UserDaoImpl();

    @Override
    public User findByUsername(String username) {
        // Gọi hàm findById của Generic DAO
        return userDao.findById(username);
    }

    @Override
    public User login(String username, String password) {
        User user = this.findByUsername(username);
        // Kiểm tra đơn giản (thực tế cần mã hóa password)
        if (user != null && user.getPassword().equals(password) && user.isActive()) {
            return user;
        }
        return null;
    }

    @Override
    public void updateProfile(User user) {
        // Trước khi update, có thể kiểm tra logic nghiệp vụ
        // Ví dụ: username không được rỗng
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username không hợp lệ");
        }
        
        // Gọi DAO để cập nhật xuống DB
        userDao.update(user);
    }
    
    @Override
    public void register(User user) throws Exception {
        // 1. Kiểm tra username đã tồn tại chưa
        if (userDao.findById(user.getUsername()) != null) {
            throw new Exception("Tên đăng nhập đã tồn tại!");
        }
        
        // 2. Kiểm tra email đã tồn tại chưa
        if (userDao.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email đã được sử dụng bởi tài khoản khác!");
        }

        // 3. Thiết lập các giá trị mặc định
        user.setAdmin(false); // Mặc định là user thường
        user.setActive(true);  // Mặc định kích hoạt

        // ⚠️ QUAN TRỌNG: Thực tế PHẢI mã hóa password ở đây trước khi save
        // String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        // user.setPassword(encodedPassword);

        // 4. Lưu xuống DB
        userDao.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}