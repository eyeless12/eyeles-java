package edu.java.repository;

import edu.java.model.Chat;
import java.util.Collection;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ChatRepository implements IChatRepository{
    private final JdbcTemplate jdbcTemplate;
    private static final ChatMapper CHAT_MAPPER = new ChatMapper();

    public ChatRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(Chat chat) {
        jdbcTemplate.update("INSERT INTO chat (id) VALUES (?)", chat.id());
    }

    @Override
    public Optional<Chat> findById(long id) {
        return jdbcTemplate.query("SELECT * FROM chat WHERE id = ?", CHAT_MAPPER, id).stream().findFirst();
    }

    @Override
    @Transactional
    public void remove(long id) {
        jdbcTemplate.update("DELETE FROM chat_link WHERE chat_id = ?", id);
        jdbcTemplate.update("DELETE FROM chat WHERE id = ?", id);
        jdbcTemplate.update("DELETE FROM link WHERE NOT EXISTS"
            + "(SELECT 1 FROM chat_link WHERE link.id = chat_link.link_id)");
    }

    @Override
    public Collection<Chat> findAllByLink(long linkId) {
        return jdbcTemplate.query(
            "SELECT c.* FROM chat c JOIN chat_link ON c.id = chat_link.chat_id where chat_link.link_id = ?",
            CHAT_MAPPER, linkId
        );
    }
}
