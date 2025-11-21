package vn.iotstar.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import vn.iotstar.dao.IUserDao;
import vn.iotstar.entity.User;

public class UserDaoImpl extends AbstractDao<User, String> implements IUserDao {

    public UserDaoImpl() {
        // Truyền class User vào constructor của lớp cha
        super(User.class);
    }

    @Override
    public User findByUsernameAndActive(String username, boolean active) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :username AND u.active = :active";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("username", username);
            query.setParameter("active", active);
            List<User> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
    
    @Override
    public User findByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            query.setParameter("email", email);
            List<User> results = query.getResultList();
            // Trả về null nếu không tìm thấy, ngược lại trả về kết quả đầu tiên
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }
}