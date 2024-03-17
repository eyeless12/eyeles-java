package edu.java.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

public record Link(long id, String url, OffsetDateTime lastCheckTime, OffsetDateTime createdAt) {
}
