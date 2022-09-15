package my.scala.study.akka.configuration

import akka.actor.typed.ActorSystem
import my.scala.study.akka.example.GreeterMain
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class AkkaCfg {


  @Bean
  def greeterMain(): ActorSystem[GreeterMain.SayHello] = {
    ActorSystem(GreeterMain(), "AkkaQuickStart")
  }
}
