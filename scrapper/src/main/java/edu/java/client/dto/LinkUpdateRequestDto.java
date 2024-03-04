package edu.java.client.dto;

import java.util.List;

public record LinkUpdateRequestDto(long id, String url, String description, List<Long> tgChatIds) {
}
