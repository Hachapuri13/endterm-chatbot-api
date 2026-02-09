package edu.aitu.chatbot.repository.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T> {
    void create(T entity) throws SQLException;
    List<T> getAll() throws SQLException;
    T getById(int id) throws SQLException;
    boolean update(T entity) throws SQLException;
    boolean delete(int id) throws SQLException;
}