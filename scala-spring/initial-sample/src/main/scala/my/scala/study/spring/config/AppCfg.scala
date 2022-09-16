package my.scala.study.spring.config

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import my.scala.study.Loggable
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class AppCfg extends Loggable {

  @Bean
  def objectMapperBuilderCustomizer(): Jackson2ObjectMapperBuilderCustomizer = {
    (jacksonObjectMapperBuilder: Jackson2ObjectMapperBuilder) => jacksonObjectMapperBuilder.modules(DefaultScalaModule, new JavaTimeModule())
  }
}
