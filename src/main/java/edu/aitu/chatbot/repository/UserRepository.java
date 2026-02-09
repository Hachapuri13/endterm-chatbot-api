package edu.aitu.chatbot.repository;

import edu.aitu.chatbot.model.User;
import edu.aitu.chatbot.exception.DatabaseOperationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User create(User user) {
        String sql = "INSERT INTO users (name, persona, is_premium) VALUES (?, ?, ?) RETURNING id";
        try {
            Integer id = jdbcTemplate.queryForObject(sql, Integer.class,
                    user.getName(),
                    user.getPersona(),
                    user.isPremium()
            );
            if (id != null) user.setId(id);
            return user;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to create user", e);
        }
    }

    public List<User> getAll() {
        String sql = "SELECT id, name, persona, is_premium FROM users ORDER BY id";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("persona"),
                            rs.getBoolean("is_premium")
                    )
            );
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to fetch users", e);
        }
    }

    public User getById(int id) {
        String sql = "SELECT id, name, persona, is_premium FROM users WHERE id = ?";
        try {
            List<User> users = jdbcTemplate.query(sql, (rs, rowNum) ->
                    new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("persona"),
                            rs.getBoolean("is_premium")
                    ), id);
            return users.isEmpty() ? null : users.get(0);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to fetch user by id=" + id, e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            return jdbcTemplate.update(sql, id) > 0;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to delete user id=" + id, e);
        }
    }

    public boolean update(User user) {
        String sql = "UPDATE users SET name = ?, persona = ?, is_premium = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(sql,
                    user.getName(),
                    user.getPersona(),
                    user.isPremium(),
                    user.getId()
            ) > 0;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to update user id=" + user.getId(), e);
        }
    }
}
