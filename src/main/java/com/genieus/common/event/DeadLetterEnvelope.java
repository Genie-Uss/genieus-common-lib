package com.genieus.common.event;

import com.genieus.common.event.util.ApplicationNameProvider;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeadLetterEnvelope<T extends DomainEvent> {
  private T event;
  private UUID eventId;
  private LocalDateTime createdAt;
  private String eventType;
  private String source;

  private String reason;
  private String failedAtService;
  private LocalDateTime failedAt;

  public static <T extends DomainEvent> DeadLetterEnvelope<T> from(
      EventEnvelope<T> original, String reason) {
    return new DeadLetterEnvelope<>(
        original.getEvent(),
        original.getEventId(),
        original.getCreatedAt(),
        original.getEventType(),
        original.getSource(),
        reason,
        ApplicationNameProvider.getName(),
        LocalDateTime.now());
  }
}
