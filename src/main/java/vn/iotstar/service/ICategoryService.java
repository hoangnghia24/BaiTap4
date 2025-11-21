package vn.iotstar.service;

import java.util.List;
import vn.iotstar.entity.Category;

public interface ICategoryService {
    List<Category> findAll();
    Category findById(int id);

    // Tạo mới Category, cần biết ai là người tạo (managerUsername)
    void createCategory(Category category, String managerUsername) throws Exception;

    void updateCategory(Category category);

    // Xóa category. Cần exception để báo lỗi nếu không xóa được (ví dụ: còn chứa video)
    void deleteCategory(int id) throws Exception;
}