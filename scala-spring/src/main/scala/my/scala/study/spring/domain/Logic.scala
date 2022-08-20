package my.scala.study.spring.domain

import my.scala.study.spring.domain.dto.DomainEvent
import org.springframework.stereotype.Component

import java.util.concurrent.ConcurrentHashMap

@Component
class Logic {
  private val processedEvents = new ConcurrentHashMap[String, DomainEvent]()

  def accept(event: DomainEvent): Unit = {
    processedEvents.put(event.data, event)
  }

}
