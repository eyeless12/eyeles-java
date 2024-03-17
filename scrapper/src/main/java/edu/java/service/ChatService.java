package edu.java.service;

import edu.java.exception.ChatAlreadyRegisteredException;
import edu.java.model.Chat;
import edu.java.repository.ChatRepository;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class ChatService implements IChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void register(long chatId) {
        chatRepository.findById(chatId).ifPresent(c -> {
            throw new ChatAlreadyRegisteredException("Chat is already registered");
        });
        Chat chat = new Chat(chatId, OffsetDateTime.now());
        chatRepository.add(chat);
    }

    public void delete(long chatId) {
        chatRepository.remove(chatId);
    }
}
