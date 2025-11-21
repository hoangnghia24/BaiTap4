package vn.iotstar.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.iotstar.config.JPAConfig;
import vn.iotstar.dao.IGenericDao;

public abstract class AbstractDao<T, ID> implements IGenericDao<T, ID> {

    private Class<T> entityClass;

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return JPAConfig.getEntityManager();
    }

    @Override
    public void save(T entity) {
        EntityManager em = getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(entity);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            throw new RuntimeException("Lỗi khi save entity", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(T entity) {
        EntityManager em = getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(entity); // merge dùng để update
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            throw new RuntimeException("Lỗi khi update entity", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(ID id) {
        EntityManager em = getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            throw new RuntimeException("Lỗi khi delete entity", e);
        } finally {
            em.close();
        }
    }

    @Override
    public T findById(ID id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            // Sử dụng JPQL động dựa trên tên class
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            TypedQuery<T> query = em.createQuery(jpql, entityClass);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(jpql, Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}