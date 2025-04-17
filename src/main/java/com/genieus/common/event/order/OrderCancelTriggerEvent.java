package com.genieus.common.event.order;

import com.genieus.common.event.DomainEvent;

/**
 * 주문 취소를 트리거하는 도메인 이벤트.
 * 주문 취소 프로세스를 시작하기 위해 발행됩니다.
 */
public record OrderCancelTriggerEvent(Long orderId) implements DomainEvent {
  /**
   * 주문 ID를 통해 주문 취소 트리거 이벤트를 생성합니다.
   *
   * @param orderId 취소할 주문의 ID
   * @return 생성된 OrderCancelTriggerEvent 인스턴스
   */
  public static OrderCancelTriggerEvent of(Long orderId) {
    return new OrderCancelTriggerEvent(orderId);
  }
}
