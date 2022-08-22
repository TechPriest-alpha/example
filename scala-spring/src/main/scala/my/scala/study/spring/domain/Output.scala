package my.scala.study.spring.domain

import com.typesafe.scalalogging.Logger
import my.scala.study.spring.system.{Loggable, OutputMarker}
import org.springframework.stereotype.Component

@Component
class Output extends Loggable, OutputMarker {
  override def doNothing(): Unit = {
    log.info("Do nothing")
  }
}
