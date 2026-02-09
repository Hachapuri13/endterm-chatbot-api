package edu.aitu.chatbot.repository;

import edu.aitu.chatbot.exception.DatabaseOperationException;
import edu.aitu.chatbot.model.Bot;
import edu.aitu.chatbot.model.ChatSession;
import edu.aitu.chatbot.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class ChatSessionRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChatSessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ChatSession create(ChatSession session) {
        String sql = "INSERT INTO chat_sessions (bot_id, user_id, started_at, total_tokens_used) VALUES (?, ?, ?, ?) RETURNING id";
        try {
            Integer id = jdbcTemplate.queryForObject(sql, Integer.class,
                    session.getBot().getId(),
                    session.getUser().getId(),
                    new Timestamp(session.getStartedAt().getTime()),
                    session.getTotalTokensUsed()
            );
            if (id != null) session.setId(id);
            return session;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to create chat session", e);
        }
    }

    public List<ChatSession> getAll() {
        String sql = """
        SELECT
          cs.id AS cs_id,
          cs.started_at,
          cs.total_tokens_used,
          b.id AS b_id, b.name AS b_name, b.greeting AS b_greeting, b.definition AS b_definition, b.token_limit AS b_token_limit,
          u.id AS u_id, u.name AS u_name, u.persona AS u_persona, u.is_premium AS u_is_premium
        FROM chat_sessions cs
        JOIN bots b ON b.id = cs.bot_id
        JOIN users u ON u.id = cs.user_id
        ORDER BY cs.id
        """;
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowJoined(rs));
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to fetch chat sessions", e);
        }
    }

    public ChatSession getById(int id) {
        String sql = """
        SELECT
          cs.id AS cs_id,
          cs.started_at,
          cs.total_tokens_used,
          b.id AS b_id, b.name AS b_name, b.greeting AS b_greeting, b.definition AS b_definition, b.token_limit AS b_token_limit,
          u.id AS u_id, u.name AS u_name, u.persona AS u_persona, u.is_premium AS u_is_premium
        FROM chat_sessions cs
        JOIN bots b ON b.id = cs.bot_id
        JOIN users u ON u.id = cs.user_id
        WHERE cs.id = ?
        """;
        try {
            List<ChatSession> sessions = jdbcTemplate.query(sql, (rs, rowNum) -> mapRowJoined(rs), id);
            return sessions.isEmpty() ? null : sessions.get(0);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to fetch chat session id=" + id, e);
        }
    }

    public boolean updateTokens(int id, int totalTokensUsed) {
        String sql = "UPDATE chat_sessions SET total_tokens_used = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(sql, totalTokensUsed, id) > 0;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to update chat session id=" + id, e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM chat_sessions WHERE id = ?";
        try {
            return jdbcTemplate.update(sql, id) > 0;
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to delete chat session id=" + id, e);
        }
    }

    private ChatSession mapRowJoined(ResultSet rs) throws SQLException {
        Bot bot = new Bot(
                rs.getInt("b_id"),
                rs.getString("b_name"),
                rs.getString("b_greeting"),
                rs.getString("b_definition"),
                rs.getInt("b_token_limit")
        );

        User user = new User(
                rs.getInt("u_id"),
                rs.getString("u_name"),
                rs.getString("u_persona"),
                rs.getBoolean("u_is_premium")
        );

        return new ChatSession(
                rs.getInt("cs_id"),
                bot,
                user,
                rs.getTimestamp("started_at"),
                rs.getInt("total_tokens_used")
        );
    }
}
