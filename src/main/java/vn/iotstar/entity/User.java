package vn.iotstar.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "Users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Username", length = 50, nullable = false)
    private String username;

    @Column(name = "Password", length = 255, nullable = false)
    private String password;

    // --- Các trường phục vụ chức năng Update Profile ---
    @Column(name = "Fullname", length = 100) // Tên đầy đủ
    private String fullname;

    @Column(name = "Phone", length = 20) // Số điện thoại
    private String phone;

    @Column(name = "Images", length = 255) // Đường dẫn ảnh avatar (upload multipart)
    private String images;
    // --------------------------------------------------

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "Admin")
    private boolean admin;

    @Column(name = "Active")
    private boolean active;

    // --- QUAN HỆ MỚI: User 1 - N Category ---
    // Một User (Admin) quản lý nhiều Category.
    // "mappedBy" trỏ tới tên biến "manager" bên entity Category.
    // CascadeType.ALL: Nếu xóa User, có thể xóa luôn các Category do họ quản lý (tùy nghiệp vụ).
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> managedCategories;

    public User() {
        this.managedCategories = new ArrayList<>();
    }

    // --- Getters and Setters cơ bản ---
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    // --- Getters/Setters và Helper cho quan hệ với Category ---
    public List<Category> getManagedCategories() {
        return managedCategories;
    }

    public void setManagedCategories(List<Category> managedCategories) {
        this.managedCategories = managedCategories;
    }

    // Helper method để đồng bộ 2 chiều khi thêm Category cho User
    public Category addManagedCategory(Category category) {
        getManagedCategories().add(category);
        category.setManager(this);
        return category;
    }

    public Category removeManagedCategory(Category category) {
        getManagedCategories().remove(category);
        category.setManager(null);
        return category;
    }
}