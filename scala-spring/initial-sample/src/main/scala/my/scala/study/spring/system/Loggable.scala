package my.scala.study.spring.system

import com.typesafe.scalalogging.Logger

trait Loggable {
  val log: Logger = Logger(getClass.getName)
}
