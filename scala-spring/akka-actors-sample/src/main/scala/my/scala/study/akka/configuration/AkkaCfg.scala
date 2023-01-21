package my.scala.study.akka.configuration

import akka.actor.typed.ActorRef.ActorRefOps
import akka.actor.typed.receptionist.Receptionist
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.cluster.sharding.typed.ShardingEnvelope
import akka.cluster.sharding.typed.scaladsl.{ClusterSharding, Entity, EntityTypeKey}
import com.fasterxml.jackson.databind.util.TypeKey
import com.typesafe.config.{Config, ConfigFactory}
import my.scala.study.akka.domain.dto.{DomainEvents, InitEvent}
import my.scala.study.akka.domain.{DomainEntityEventHandler, DomainSetup}
import my.scala.study.akka.example.GreeterMain
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class AkkaCfg {


  @Bean
  def greeterMain(): ActorSystem[GreeterMain.SayHello] = {
    ActorSystem(GreeterMain(), "AkkaQuickStart", ConfigFactory.empty())
  }

  @Bean
  def domainSetup(typeKey: EntityTypeKey[DomainEvents]): ClusterSharding = {
    val actorSystem = ActorSystem(DomainSetup(), "DomainSetup")
    val sharding = ClusterSharding(actorSystem)
    sharding.init(Entity(typeKey)(createBehavior = entityContext => {
      DomainEntityEventHandler(entityContext.entityId)
    }))

    sharding
  }

  @Bean
  def typeKey(): EntityTypeKey[DomainEvents] = {
    EntityTypeKey[DomainEvents]("DomainEvents")
  }
}
