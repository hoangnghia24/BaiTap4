package vn.iotstar.dao;

import java.util.List;

public interface IGenericDao<T, ID> {
    void save(T entity);          // Tạo mới
    void update(T entity);        // Cập nhật
    void delete(ID id);           // Xóa theo ID
    T findById(ID id);            // Tìm theo ID
    List<T> findAll();            // Tìm tất cả
    long count();                 // Đếm tổng số bản ghi
}