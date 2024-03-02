package edu.java.controller.dto;

import java.util.List;

public record ListLinksResponseDto(List<LinkResponseDto> links, int size) {
}
