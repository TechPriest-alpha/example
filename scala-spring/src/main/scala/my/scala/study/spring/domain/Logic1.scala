package my.scala.study.spring.domain

import my.scala.study.spring.domain.dto.DomainEvent1
import my.scala.study.spring.system.{Loggable, LogicFor}
import org.springframework.stereotype.Component

import java.util.concurrent.ConcurrentHashMap

@Component
class Logic1() extends LogicFor[DomainEvent1] {

  override def accept(event: DomainEvent1): Unit = {
    log.info("Domain event1: {} processed", event)
    processedEvents.put(event.data, event)
  }
}


