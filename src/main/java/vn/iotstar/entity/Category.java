package vn.iotstar.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "Category")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private int categoryId;

    @Column(name = "Categoryname", length = 100, nullable = false)
    private String categoryname;

    @Column(name = "Categorycode", length = 50)
    private String categorycode;

    @Column(name = "Images", length = 255)
    private String images;

    @Column(name = "Status")
    private int status;

    // --- QUAN HỆ MỚI: Category N - 1 User ---
    // Category này thuộc sự quản lý của User nào.
    // Tạo cột khóa ngoại "ManagerUsername" trong bảng Category.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ManagerUsername")
    private User manager;

    // --- QUAN HỆ CŨ: Category 1 - N Video ---
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Video> videos;

    public Category() {
        this.videos = new ArrayList<>();
    }

    // --- Getters and Setters cơ bản ---
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getCategoryname() { return categoryname; }
    public void setCategoryname(String categoryname) { this.categoryname = categoryname; }
    public String getCategorycode() { return categorycode; }
    public void setCategorycode(String categorycode) { this.categorycode = categorycode; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    // --- Getters/Setters cho các quan hệ ---

    // User Manager
    public User getManager() { return manager; }
    public void setManager(User manager) { this.manager = manager; }

    // List Videos
    public List<Video> getVideos() { return videos; }
    public void setVideos(List<Video> videos) { this.videos = videos; }

    // Helper methods cho Video
    public Video addVideo(Video video) {
        getVideos().add(video);
        video.setCategory(this);
        return video;
    }

    public Video removeVideo(Video video) {
        getVideos().remove(video);
        video.setCategory(null);
        return video;
    }
}