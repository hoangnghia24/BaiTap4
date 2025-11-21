package vn.iotstar.service;

import java.util.List;
import vn.iotstar.entity.Video;

public interface IVideoService {
    List<Video> findAll();
    Video findById(String videoId);

    // Tìm kiếm video theo từ khóa
    List<Video> searchVideo(String keyword);

    // Tạo video mới, bắt buộc phải thuộc một categoryId
    void createVideo(Video video, int categoryId) throws Exception;

    void updateVideo(Video video, int newCategoryId) throws Exception;

    void deleteVideo(String videoId);
}