package edu.java.client;

import edu.java.client.dto.StackOverflowPostInnerResponseDto;
import java.util.Optional;

public interface StackOverflowClient {
    Optional<StackOverflowPostInnerResponseDto> fetchPost(long postId);

    boolean exists(long postId);
}
