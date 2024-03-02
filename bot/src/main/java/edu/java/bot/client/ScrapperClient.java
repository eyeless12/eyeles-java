package edu.java.bot.client;

import edu.java.bot.client.dto.LinkResponseDto;
import edu.java.bot.client.dto.ListLinksResponseDto;

public interface ScrapperClient {
    ListLinksResponseDto fetchLinks(long chatId);

    void registerChat(long chatId);

    LinkResponseDto trackLink(long chatId, String link);

    LinkResponseDto untrackLink(long chatId, String link);
}
