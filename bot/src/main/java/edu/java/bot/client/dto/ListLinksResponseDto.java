package edu.java.bot.client.dto;

import java.util.List;

public class ListLinksResponseDto {
    private List<LinkResponseDto> links;
    private long size;

    public List<LinkResponseDto> getLinks() {
        return links;
    }

    public void setLinks(List<LinkResponseDto> links) {
        this.links = links;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
