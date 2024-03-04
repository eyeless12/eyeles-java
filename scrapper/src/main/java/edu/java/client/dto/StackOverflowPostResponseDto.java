package edu.java.client.dto;

import java.util.List;

public record StackOverflowPostResponseDto(List<StackOverflowPostInnerResponseDto> items) {}
