package edu.aitu.chatbot.repository;

import edu.aitu.chatbot.model.Bot;
import edu.aitu.chatbot.exception.DatabaseOperationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class BotRepository {

    private final JdbcTemplate jdbcTemplate;

    public BotRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Bot create(Bot bot) {
        String sql = "INSERT INTO bots (name, greeting, definition, token_limit) VALUES (?, ?, ?, ?) RETURNING id";

        try {
            Integer id = jdbcTemplate.queryForObject(sql, Integer.class,
                    bot.getName(),
                    bot.getGreeting(),
                    bot.getDefinition(),
                    bot.getTokenLimit()
            );
            if (id != null) bot.setId(id);
            return bot;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to create bot", e);
        }
    }

    public List<Bot> getAll() {
        String sql = "SELECT id, name, greeting, definition, token_limit FROM bots ORDER BY id";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Bot(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("greeting"),
                            rs.getString("definition"),
                            rs.getInt("token_limit")
                    )
            );
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to fetch bots", e);
        }
    }

    public Bot getById(int id) {
        String sql = "SELECT id, name, greeting, definition, token_limit FROM bots WHERE id = ?";
        try {
            List<Bot> bots = jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Bot(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("greeting"),
                            rs.getString("definition"),
                            rs.getInt("token_limit")
                    ), id);
            return bots.isEmpty() ? null : bots.get(0);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to fetch bot by id=" + id, e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM bots WHERE id = ?";
        try {
            return jdbcTemplate.update(sql, id) > 0;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to delete bot id=" + id, e);
        }
    }

    public boolean update(Bot bot) {
        String sql = "UPDATE bots SET name = ?, greeting = ?, definition = ?, token_limit = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(sql,
                    bot.getName(),
                    bot.getGreeting(),
                    bot.getDefinition(),
                    bot.getTokenLimit(),
                    bot.getId()
            ) > 0;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to update bot id=" + bot.getId(), e);
        }
    }
}
