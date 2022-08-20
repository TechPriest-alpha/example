package my.scala.study.spring.domain

import my.scala.study.spring.system.Loggable
import org.springframework.stereotype.Component

@Component
class Output extends Loggable {
  def doNothing(): Unit = {
    log.info("Do nothing")
  }

}