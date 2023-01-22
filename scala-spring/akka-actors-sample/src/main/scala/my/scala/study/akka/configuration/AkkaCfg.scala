package my.scala.study.akka.configuration

import akka.actor.typed.ActorRef.ActorRefOps
import akka.actor.typed.receptionist.Receptionist
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.cluster.sharding.typed.ShardingEnvelope
import akka.cluster.sharding.typed.scaladsl.{ClusterSharding, Entity, EntityTypeKey}
import akka.persistence.typed.PersistenceId
import com.fasterxml.jackson.databind.util.TypeKey
import com.typesafe.config.{Config, ConfigFactory}
import my.scala.study.akka.domain.dto.{DomainEvents, InitEvent}
import my.scala.study.akka.domain.{DomainSetup, NonPersistingDomainEntityEventHandler, PersistingDomainEntityEventHandler}
import my.scala.study.akka.example.GreeterMain
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class AkkaCfg {


  @Bean
  def greeterMain(): ActorSystem[GreeterMain.SayHello] = {
    ActorSystem(GreeterMain(), "AkkaQuickStart", ConfigFactory.empty())
  }

  @Bean
  def domainSetup(typeKeyNonPersistent: EntityTypeKey[DomainEvents], typeKeyPersistent: EntityTypeKey[DomainEvents]): ClusterSharding = {
    val actorSystem = ActorSystem(DomainSetup(), "DomainSetup")
    val sharding = ClusterSharding(actorSystem)
    sharding.init(Entity(typeKeyNonPersistent)(createBehavior = entityContext => {
      NonPersistingDomainEntityEventHandler(entityContext.entityId, PersistenceId(entityContext.entityTypeKey.name, entityContext.entityId))
    }))
    sharding.init(Entity(typeKeyPersistent)(createBehavior = entityContext => {
      PersistingDomainEntityEventHandler(entityContext.entityId, PersistenceId(entityContext.entityTypeKey.name, entityContext.entityId))
    }))

    sharding
  }

  @Bean
  def typeKeyNonPersistent(): EntityTypeKey[DomainEvents] = {
    EntityTypeKey[DomainEvents]("NonPersistentDomainEvents")
  }

  @Bean
  def typeKeyPersistent(): EntityTypeKey[DomainEvents] = {
    EntityTypeKey[DomainEvents]("PersistentDomainEvents")
  }
}
