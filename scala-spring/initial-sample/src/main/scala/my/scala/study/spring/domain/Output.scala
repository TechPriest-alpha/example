package my.scala.study.spring.domain

import com.typesafe.scalalogging.Logger
import my.scala.study.Loggable
import my.scala.study.spring.system.{OutputMarker, PartialResult}
import org.springframework.stereotype.Component

@Component
class Output extends Loggable, OutputMarker {
  override def doNothing(): Unit = {
    log.info("Do nothing")
  }

  override def process(partialResult: PartialResult, error: Throwable): Unit = {
    if (error == null) {
      log.info("Process success: {}", partialResult)
    } else {
      log.info("Process error", error)
    }
  }
}
