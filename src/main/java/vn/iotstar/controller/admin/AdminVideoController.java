package vn.iotstar.controller.admin;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.Video;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IVideoService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.VideoServiceImpl;
import vn.iotstar.utils.UploadUtils;

@WebServlet(urlPatterns = { "/admin/video/*" })
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 50, // Video poster có thể lớn hơn
    maxRequestSize = 1024 * 1024 * 100
)
public class AdminVideoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private IVideoService videoService = new VideoServiceImpl();
    // Cần CategoryService để lấy danh sách category cho dropdown box
    private ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
            showList(req, resp);
        } else if (pathInfo.equals("/create")) {
            showCreateForm(req, resp);
        } else if (pathInfo.equals("/update")) {
            showUpdateForm(req, resp);
        } else if (pathInfo.equals("/delete")) {
            deleteVideo(req, resp);
        } else if (pathInfo.equals("/search")) {
            searchVideo(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        if (pathInfo.equals("/create")) {
            createVideo(req, resp);
        } else if (pathInfo.equals("/update")) {
            updateVideo(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }
    
    // --- Helper Methods ---

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Video> list = videoService.findAll();
        req.setAttribute("videos", list);
        req.getRequestDispatcher("/views/admin/video/list.jsp").forward(req, resp);
    }
    
    // Hàm tìm kiếm
    private void searchVideo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<Video> list = videoService.searchVideo(keyword);
        req.setAttribute("videos", list);
        // Gửi lại keyword để hiển thị trên ô tìm kiếm
        req.setAttribute("currentKeyword", keyword); 
        // Dùng lại trang list.jsp để hiển thị kết quả
        req.getRequestDispatcher("/views/admin/video/list.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy tất cả category để đẩy vào <select> option
        req.setAttribute("categories", categoryService.findAll());
        req.getRequestDispatcher("/views/admin/video/form.jsp").forward(req, resp);
    }

    private void showUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String videoId = req.getParameter("id");
        Video video = videoService.findById(videoId);
        req.setAttribute("video", video);
        // Cũng cần danh sách category cho form sửa
        req.setAttribute("categories", categoryService.findAll());
        req.getRequestDispatcher("/views/admin/video/form.jsp").forward(req, resp);
    }

    private void createVideo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            Video video = new Video();
            video.setVideoId(req.getParameter("videoId")); // ID video thường là chuỗi do người dùng nhập hoặc sinh ra
            video.setTitle(req.getParameter("title"));
            video.setDescription(req.getParameter("description"));
            
            // Xử lý upload poster
            String posterPath = UploadUtils.processUploadField(req, "posterFile", null);
            video.setPoster(posterPath);

            // Lấy ID category được chọn từ dropdown
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));

            // Gọi service
            videoService.createVideo(video, categoryId);
            resp.sendRedirect(req.getContextPath() + "/admin/video/list?message=CreatedSuccess");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            // Load lại danh sách category nếu forward lại form
            req.setAttribute("categories", categoryService.findAll());
            req.getRequestDispatcher("/views/admin/video/form.jsp").forward(req, resp);
        }
    }

    private void updateVideo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String videoId = req.getParameter("videoId");
            // Load video cũ
            Video video = videoService.findById(videoId);
            
            video.setTitle(req.getParameter("title"));
            video.setDescription(req.getParameter("description"));
            boolean active = req.getParameter("active") != null; // Checkbox
            video.setActive(active);

            String oldPoster = req.getParameter("oldPoster");
            String posterPath = UploadUtils.processUploadField(req, "posterFile", oldPoster);
            video.setPoster(posterPath);
            
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));

            // Gọi service update
            videoService.updateVideo(video, categoryId);
            resp.sendRedirect(req.getContextPath() + "/admin/video/list?message=UpdatedSuccess");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/admin/video/list?error=UpdateFailed");
        }
    }

    private void deleteVideo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String videoId = req.getParameter("id");
        videoService.deleteVideo(videoId);
        resp.sendRedirect(req.getContextPath() + "/admin/video/list?message=DeletedSuccess");
    }
}