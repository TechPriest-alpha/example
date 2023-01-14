package my.scala.study.spring.domain

import my.scala.study.spring.domain.cfg.DomainConfig
import my.scala.study.spring.domain.dto.{DomainEvent1, DomainEvent2}
import my.scala.study.spring.system.{LogicFor, OutputMarker}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.util.concurrent.{CompletableFuture, ConcurrentHashMap}

@Component
case class Logic2 @Autowired()(domainConfig: DomainConfig) extends LogicFor[DomainEvent2](domainConfig) {

  override def accept(event: DomainEvent2): CompletableFuture[OutputMarker] = {
    log.info("Domain event1: {} processed with cfg: {}", event, domainConfig.custom2)


    processedEvents.put(event.data2, event)
    CompletableFuture.completedFuture(Output())
  }

}
