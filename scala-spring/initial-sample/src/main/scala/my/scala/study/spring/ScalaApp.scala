package my.scala.study.spring

import org.springframework.boot.SpringApplication

/**
 * Thanks to
 * https://dzone.com/articles/spring-scala-cloud-psh
 * for initial inspiration. Had to add adjustments for scala3 however.
 */
@main def hello(): Unit = {
  SpringApplication.run(classOf[BootConfig])
}



