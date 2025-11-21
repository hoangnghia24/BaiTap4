package vn.iotstar.controller.admin;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.Category;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.impl.CategoryServiceImpl;

// URL pattern dạng wildcard để bắt các request con
@WebServlet(urlPatterns = { "/admin/category/*" })
public class AdminCategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo(); // Lấy phần sau /admin/category (ví dụ: /list, /create)
        
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
            showList(req, resp);
        } else if (pathInfo.equals("/create")) {
            showCreateForm(req, resp);
        } else if (pathInfo.equals("/update")) {
            showUpdateForm(req, resp);
        } else if (pathInfo.equals("/delete")) {
            deleteCategory(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        if (pathInfo.equals("/create")) {
            createCategory(req, resp);
        } else if (pathInfo.equals("/update")) {
            updateCategory(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    // --- Các hàm helper cho doGet ---

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> list = categoryService.findAll();
        req.setAttribute("categories", list);
        req.getRequestDispatcher("/views/admin/category/list.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Forward tới trang form tạo mới
        req.getRequestDispatcher("/views/admin/category/form.jsp").forward(req, resp);
    }

    private void showUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Category category = categoryService.findById(id);
            req.setAttribute("category", category);
             // Forward tới trang form (dùng chung form với create nhưng có sẵn dữ liệu)
            req.getRequestDispatcher("/views/admin/category/form.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/admin/category/list?error=InvalidID");
        }
    }
    
    // --- Các hàm helper cho doPost và Delete ---

    private void createCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String categoryName = req.getParameter("categoryname");
            String categoryCode = req.getParameter("categorycode");
            // Lấy username admin đang đăng nhập từ session
            HttpSession session = req.getSession();
            String managerUsername = (String) session.getAttribute("loginedUsername");

            Category category = new Category();
            category.setCategoryname(categoryName);
            category.setCategorycode(categoryCode);

            // Gọi service tạo mới (service sẽ lo việc gắn manager)
            categoryService.createCategory(category, managerUsername);
            
            // Redirect về trang danh sách
            resp.sendRedirect(req.getContextPath() + "/admin/category/list?message=CreatedSuccess");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
             // Nếu lỗi, forward lại trang form để nhập lại
            req.getRequestDispatcher("/views/admin/category/form.jsp").forward(req, resp);
        }
    }

    private void updateCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            String categoryName = req.getParameter("categoryname");
            String categoryCode = req.getParameter("categorycode");
            int status = Integer.parseInt(req.getParameter("status"));

            // Load category cũ lên để giữ nguyên người quản lý (manager)
            Category category = categoryService.findById(categoryId);
            category.setCategoryname(categoryName);
            category.setCategorycode(categoryCode);
            category.setStatus(status);

            categoryService.updateCategory(category);
            resp.sendRedirect(req.getContextPath() + "/admin/category/list?message=UpdatedSuccess");
        } catch (Exception e) {
             e.printStackTrace();
             resp.sendRedirect(req.getContextPath() + "/admin/category/list?error=UpdateFailed");
        }
    }

    private void deleteCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            categoryService.deleteCategory(id);
            resp.sendRedirect(req.getContextPath() + "/admin/category/list?message=DeletedSuccess");
        } catch (Exception e) {
            // Service sẽ ném exception nếu category còn chứa video
            resp.sendRedirect(req.getContextPath() + "/admin/category/list?error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}