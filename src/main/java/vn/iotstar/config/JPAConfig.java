package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");

    public static EntityManager getEntityManager() {
        // Mỗi lần gọi sẽ tạo ra một EntityManager mới để đảm bảo thread-safe
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}