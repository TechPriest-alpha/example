package my.scala.study.spring.domain

import my.scala.study.Loggable
import my.scala.study.spring.domain.cfg.DomainConfig
import my.scala.study.spring.domain.dto.DomainEvent1
import my.scala.study.spring.system.LogicFor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.util.concurrent.ConcurrentHashMap

@Component
case class Logic1 @Autowired()(domainConfig: DomainConfig) extends LogicFor[DomainEvent1](domainConfig) {

  override def accept(event: DomainEvent1): Unit = {
    log.info("Domain event1: {} processed with cfg: {}", event, domainConfig.custom1)
    processedEvents.put(event.data1, event)
  }
}


