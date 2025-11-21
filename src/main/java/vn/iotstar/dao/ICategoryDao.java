package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;

public interface ICategoryDao extends IGenericDao<Category, Integer> {
    // Tìm các category được quản lý bởi một user cụ thể
    List<Category> findByManager(User manager);
}