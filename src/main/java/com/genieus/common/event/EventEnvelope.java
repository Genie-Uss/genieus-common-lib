package com.genieus.common.event;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genieus.common.event.util.ApplicationNameProvider;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventEnvelope<T extends DomainEvent> {
  private T event;
  private UUID eventId;
  private LocalDateTime createdAt;
  private String eventType;
  private String source;

  public static <T extends DomainEvent> EventEnvelope<T> create(T event) {
    return new EventEnvelope<>(
        event,
        UUID.randomUUID(),
        LocalDateTime.now(),
        event.getClass().getSimpleName(),
        ApplicationNameProvider.getName());
  }

  public static <T extends DomainEvent> EventEnvelope<T> restore(
      T event, String uuid, LocalDateTime createdAt, String eventType, String source) {
    return new EventEnvelope<>(event, UUID.fromString(uuid), createdAt, eventType, source);
  }

  public static EventEnvelope<? extends DomainEvent> fromJson(
      String json,
      ObjectMapper mapper,
      Function<String, Class<? extends DomainEvent>> typeResolver) {
    try {
      JsonNode root = mapper.readTree(json);
      String eventType = root.get("eventType").asText();
      Class<? extends DomainEvent> clazz = typeResolver.apply(eventType);
      if (clazz == null) {
        return null;
      }
      JavaType javaType =
          mapper.getTypeFactory().constructParametricType(EventEnvelope.class, clazz);
      return mapper.readValue(json, javaType);
    } catch (Exception e) {
      throw new RuntimeException("[fromJson] EventEnvelope 역직렬화 실패", e);
    }
  }
}
