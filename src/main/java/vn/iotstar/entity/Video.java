package vn.iotstar.entity;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "Videos")
@NamedQuery(name = "Video.findAll", query = "SELECT v FROM Video v")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "VideoId", length = 50)
    private String videoId;

    // Trường này sẽ được dùng chính cho chức năng tìm kiếm
    @Column(name = "Title", length = 255, nullable = false)
    private String title;

    @Column(name = "Poster", length = 255)
    private String poster;

    @Column(name = "Views")
    private int views;

    // Trường này cũng có thể dùng để tìm kiếm nội dung
    @Lob
    @Column(name = "Description", columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "Active")
    private boolean active;

    // --- QUAN HỆ: Video N - 1 Category ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryId", nullable = false)
    private Category category;

    // (ĐÃ LOẠI BỎ QUAN HỆ VỚI USER THEO YÊU CẦU)

    public Video() {
    }

    // --- Getters and Setters ---
    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }
    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}