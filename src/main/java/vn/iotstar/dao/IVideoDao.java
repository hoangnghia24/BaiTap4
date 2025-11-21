package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.entity.Video;

public interface IVideoDao extends IGenericDao<Video, String> {
    // Tìm kiếm video theo tiêu đề HOẶC mô tả (có chứa từ khóa)
    List<Video> searchByTitleOrDescription(String keyword);

    // Tìm video theo Category ID
    List<Video> findByCategoryId(int categoryId);
}