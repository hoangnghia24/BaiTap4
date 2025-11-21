package vn.iotstar.dao;

import vn.iotstar.entity.User;

public interface IUserDao extends IGenericDao<User, String> {
    // Ví dụ thêm hàm kiểm tra đăng nhập (nếu cần)
    User findByUsernameAndActive(String username, boolean active);
    User findByEmail(String email);
}