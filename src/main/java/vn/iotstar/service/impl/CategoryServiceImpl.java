package vn.iotstar.service.impl;

import java.util.List;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.dao.IUserDao;
import vn.iotstar.dao.impl.CategoryDaoImpl;
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
import vn.iotstar.service.ICategoryService;

public class CategoryServiceImpl implements ICategoryService {

    private ICategoryDao categoryDao = new CategoryDaoImpl();
    // Cần thêm UserDao để tìm người quản lý
    private IUserDao userDao = new UserDaoImpl();

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(int id) {
        return categoryDao.findById(id);
    }

    @Override
    public void createCategory(Category category, String managerUsername) throws Exception {
        // 1. Validate dữ liệu đầu vào
        if (category.getCategoryname() == null || category.getCategoryname().isEmpty()) {
            throw new Exception("Tên danh mục không được để trống");
        }

        // 2. Tìm người quản lý dựa trên username
        User manager = userDao.findById(managerUsername);
        if (manager == null) {
             throw new Exception("Người quản lý không tồn tại: " + managerUsername);
        }

        // 3. Thiết lập quan hệ (Nghiệp vụ cốt lõi)
        category.setManager(manager);
        // status mặc định là 1 (active) nếu tạo mới
        category.setStatus(1); 

        // 4. Lưu xuống DB
        categoryDao.save(category);
    }

    @Override
    public void updateCategory(Category category) {
         // Validate...
         // Gọi DAO update
         categoryDao.update(category);
    }

    @Override
    public void deleteCategory(int id) throws Exception {
        // Nghiệp vụ: Kiểm tra xem category có chứa video không trước khi xóa?
        // (Ở mức DB đã có thể có ràng buộc khóa ngoại, nhưng check ở đây tốt hơn cho UX)
        Category category = categoryDao.findById(id);
        if (category == null) {
             throw new Exception("Category không tồn tại");
        }
        
        // Nếu dùng FetchType.LAZY, cần cẩn thận khi gọi getVideos().size() 
        // nếu session đã đóng. Tuy nhiên với AbstractDao hiện tại thì OK.
        if (category.getVideos() != null && !category.getVideos().isEmpty()) {
             throw new Exception("Không thể xóa danh mục đang chứa video!");
        }

        categoryDao.delete(id);
    }
}