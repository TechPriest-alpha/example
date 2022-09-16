package my.scala.study

import com.typesafe.scalalogging.Logger

trait Loggable {
  val log: Logger = Logger(getClass.getName)
}
