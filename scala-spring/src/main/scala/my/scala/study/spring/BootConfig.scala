package my.scala.study.spring

import my.scala.study.spring.domain.cfg.DomainConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.{ConfigurationPropertiesScan, EnableConfigurationProperties}

@SpringBootApplication
@EnableConfigurationProperties //(Array(classOf[DomainConfig]))
@ConfigurationPropertiesScan(Array("my.scala.study.spring.domain"))
class BootConfig
