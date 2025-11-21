package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
import vn.iotstar.entity.Video;

public class Test {

    public static void main(String[] args) {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();

        try {
            trans.begin();

            // 1. Tạo User Admin mẫu để đăng nhập
            // Kiểm tra xem user đã tồn tại chưa để tránh lỗi trùng khóa chính khi chạy lại
            if (enma.find(User.class, "admin") == null) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword("123456"); // Lưu ý: Mật khẩu plain-text cho mục đích test
                adminUser.setEmail("admin@iotstar.vn");
                adminUser.setFullname("Quản trị viên hệ thống");
                adminUser.setPhone("0987654321");
                adminUser.setAdmin(true); // Đặt quyền admin
                adminUser.setActive(true); // Kích hoạt tài khoản
                
                enma.persist(adminUser);
                System.out.println(">>> Đã tạo User admin thành công!");
            } else {
                 System.out.println(">>> User admin đã tồn tại, bỏ qua.");
            }

            // 2. Tạo Category mẫu
            Category cate = new Category();
            cate.setCategoryname("Công nghệ thông tin");
            cate.setCategorycode("IT"); // Nếu entity của bạn có trường này
            cate.setImages("it-banner.jpg");
            cate.setStatus(1);
            // Không set ID vì nó là tự tăng (IDENTITY)
            
            // Nếu bạn muốn gán category này cho admin quản lý (theo quan hệ mới nhất)
            // cate.setManager(adminUser); 

            enma.persist(cate);
            System.out.println(">>> Đã tạo Category thành công! ID: " + cate.getCategoryId());

            // 3. Tạo Video mẫu
            // Kiểm tra video tồn tại chưa
            if (enma.find(Video.class, "v01") == null) {
                Video video = new Video();
                video.setVideoId("v01"); // ID chuỗi thủ công
                video.setTitle("Giới thiệu khóa học Lập trình Web Java");
                video.setDescription("Video giới thiệu tổng quan về Servlet, JSP và JPA.");
                video.setPoster("java-poster.jpg");
                video.setViews(100);
                video.setActive(true);
                
                // Quan trọng: Gắn video vào category vừa tạo
                video.setCategory(cate);

                enma.persist(video);
                 System.out.println(">>> Đã tạo Video v01 thành công!");
            } else {
                 System.out.println(">>> Video v01 đã tồn tại, bỏ qua.");
            }

            trans.commit();
            System.out.println("========================================");
            System.out.println("KHỞI TẠO DỮ LIỆU MẪU THÀNH CÔNG!");
            System.out.println("Bạn có thể đăng nhập với: admin / 123456");
            System.out.println("========================================");

        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) {
                trans.rollback();
            }
            System.err.println(">>> CÓ LỖI XẢY RA KHI KHỞI TẠO DỮ LIỆU!");
            throw e;
        } finally {
            enma.close();
        }
    }
}