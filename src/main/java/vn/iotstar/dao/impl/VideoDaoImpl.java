package vn.iotstar.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.iotstar.dao.IVideoDao;
import vn.iotstar.entity.Video;

public class VideoDaoImpl extends AbstractDao<Video, String> implements IVideoDao {

    public VideoDaoImpl() {
        super(Video.class);
    }

    @Override
    public List<Video> searchByTitleOrDescription(String keyword) {
        EntityManager em = getEntityManager();
        try {
            // Sử dụng LOWER() để tìm kiếm không phân biệt hoa thường
            // Sử dụng % keyword % để tìm kiếm gần đúng
            String jpql = "SELECT v FROM Video v WHERE LOWER(v.title) LIKE LOWER(:keyword) OR LOWER(v.description) LIKE LOWER(:keyword)";
            TypedQuery<Video> query = em.createQuery(jpql, Video.class);
            // Thêm dấu % vào tham số
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> findByCategoryId(int categoryId) {
         EntityManager em = getEntityManager();
        try {
            // Join ngầm định để tìm theo ID của category
            String jpql = "SELECT v FROM Video v WHERE v.category.categoryId = :catId";
            TypedQuery<Video> query = em.createQuery(jpql, Video.class);
            query.setParameter("catId", categoryId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}