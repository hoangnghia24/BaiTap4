package vn.iotstar.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;

public class CategoryDaoImpl extends AbstractDao<Category, Integer> implements ICategoryDao {

    public CategoryDaoImpl() {
        super(Category.class);
    }

    @Override
    public List<Category> findByManager(User manager) {
        EntityManager em = getEntityManager();
        try {
            // Tìm kiếm dựa trên quan hệ ManyToOne
            String jpql = "SELECT c FROM Category c WHERE c.manager = :manager";
            TypedQuery<Category> query = em.createQuery(jpql, Category.class);
            query.setParameter("manager", manager);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}