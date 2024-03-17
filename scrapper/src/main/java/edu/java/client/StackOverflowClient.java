package edu.java.client;

import edu.java.client.dto.StackOverflowPostInnerResponseDto;
import edu.java.client.dto.StackOverflowPostResponseDto;
import java.util.Optional;

public interface StackOverflowClient {
    public Optional<StackOverflowPostInnerResponseDto> fetchPost(long postId);
    public boolean exists(long postId);
}
