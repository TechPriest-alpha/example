package my.scala.study.spring.domain

import my.scala.study.spring.domain.dto.{DomainEvent1, DomainEvent2}
import my.scala.study.spring.system.LogicFor
import org.springframework.stereotype.Component

import java.util.concurrent.ConcurrentHashMap

@Component
case class Logic2() extends LogicFor[DomainEvent2] {

  override def accept(event: DomainEvent2): Unit = {
    log.info("Domain event2: {} processed", event)

    processedEvents.put(event.data2, event)
  }

}
