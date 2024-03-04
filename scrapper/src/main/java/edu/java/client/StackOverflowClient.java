package edu.java.client;

import edu.java.client.dto.StackOverflowPostResponseDto;

public interface StackOverflowClient {
    StackOverflowPostResponseDto fetchPost(long id);
}
