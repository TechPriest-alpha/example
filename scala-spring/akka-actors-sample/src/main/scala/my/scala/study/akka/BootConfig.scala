package my.scala.study.akka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.{ConfigurationPropertiesScan, EnableConfigurationProperties}

@SpringBootApplication
@EnableConfigurationProperties //(Array(classOf[DomainConfig]))
@ConfigurationPropertiesScan(Array("my.scala.study.akka.domain"))
class BootConfig
