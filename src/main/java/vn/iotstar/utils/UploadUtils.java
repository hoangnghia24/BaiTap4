package vn.iotstar.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class UploadUtils {
    // Định nghĩa thư mục lưu trữ file ảnh. 
    // LƯU Ý: Trên môi trường thật, nên dùng đường dẫn tuyệt đối ngoài thư mục project.
    // Ở đây dùng đường dẫn tương đối để dễ chạy demo.
    private static final String UPLOAD_DIRECTORY = "uploads";

    public static String processUploadField(HttpServletRequest request, String fieldName, String existingFileName) 
            throws IOException, ServletException {
        
        Part filePart = request.getPart(fieldName); // Lấy phần file từ request
        
        // Kiểm tra xem người dùng có chọn file mới không
        if (filePart == null || filePart.getSize() == 0 || filePart.getSubmittedFileName().isEmpty()) {
            // Nếu không chọn file mới, trả về tên file cũ để giữ nguyên ảnh cũ
            return existingFileName; 
        }

        // Xử lý file mới
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        // Thêm timestamp để tránh trùng tên file
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        
        // Xác định đường dẫn thực trên server
        String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir(); // Tạo thư mục nếu chưa tồn tại
        }

        // Lưu file
        String filePath = uploadPath + File.separator + uniqueFileName;
        filePart.write(filePath);

        // Trả về đường dẫn tương đối để lưu vào database (ví dụ: uploads/anh.jpg)
        return UPLOAD_DIRECTORY + "/" + uniqueFileName;
    }
}