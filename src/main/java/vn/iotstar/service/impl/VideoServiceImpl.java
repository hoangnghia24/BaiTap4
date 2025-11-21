package vn.iotstar.service.impl;

import java.util.List;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.dao.IVideoDao;
import vn.iotstar.dao.impl.CategoryDaoImpl;
import vn.iotstar.dao.impl.VideoDaoImpl;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Video;
import vn.iotstar.service.IVideoService;

public class VideoServiceImpl implements IVideoService {

    private IVideoDao videoDao = new VideoDaoImpl();
    // Cần CategoryDao để kiểm tra category tồn tại
    private ICategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Video> findAll() {
        return videoDao.findAll();
    }

    @Override
    public Video findById(String videoId) {
        return videoDao.findById(videoId);
    }

    @Override
    public List<Video> searchVideo(String keyword) {
        // Nếu từ khóa rỗng thì trả về tất cả
        if (keyword == null || keyword.trim().isEmpty()) {
            return this.findAll();
        }
        return videoDao.searchByTitleOrDescription(keyword);
    }

    @Override
    public void createVideo(Video video, int categoryId) throws Exception {
        // 1. Validate
        if (video.getVideoId() == null || video.getVideoId().isEmpty()) {
             throw new Exception("Video ID cannot be null");
        }
        // Kiểm tra xem ID đã tồn tại chưa
        if (videoDao.findById(video.getVideoId()) != null) {
             throw new Exception("Video ID đã tồn tại: " + video.getVideoId());
        }

        // 2. Tìm Category
        Category category = categoryDao.findById(categoryId);
        if (category == null) {
            throw new Exception("Category ID không hợp lệ: " + categoryId);
        }

        // 3. Thiết lập quan hệ
        video.setCategory(category);
        video.setViews(0); // Mặc định view = 0
        video.setActive(true); // Mặc định active

        // 4. Lưu
        videoDao.save(video);
    }

    @Override
    public void updateVideo(Video video, int newCategoryId) throws Exception {
        // 1. Kiểm tra category mới có hợp lệ không
        Category category = categoryDao.findById(newCategoryId);
        if (category == null) {
            throw new Exception("Category ID mới không hợp lệ: " + newCategoryId);
        }
        
        // 2. Cập nhật lại quan hệ
        video.setCategory(category);
        
        // 3. Gọi DAO update
        videoDao.update(video);
    }

    @Override
    public void deleteVideo(String videoId) {
        videoDao.delete(videoId);
    }
}