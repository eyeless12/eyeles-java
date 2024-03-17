package edu.java.repository;

import edu.java.model.Chat;
import java.util.Collection;
import java.util.Optional;

public interface IChatRepository {
    void add(Chat chat);

    Optional<Chat> findById(long id);

    void remove(long id);

    Collection<Chat> findAllByLink(long linkId);
}