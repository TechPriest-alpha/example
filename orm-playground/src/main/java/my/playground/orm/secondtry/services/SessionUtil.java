package my.playground.orm.secondtry.services;

import my.playground.orm.secondtry.entities.Person;
import my.playground.orm.secondtry.entities.Ranking;
import my.playground.orm.secondtry.entities.Skill;
import my.playground.orm.secondtry.entities.hierarchy.*;
import my.playground.orm.secondtry.entities.lazyness.MappedEmailEager;
import my.playground.orm.secondtry.entities.lazyness.MappedEmailLazy;
import my.playground.orm.secondtry.entities.lazyness.MappedMessageEager;
import my.playground.orm.secondtry.entities.lazyness.MappedMessageLazy;
import my.playground.orm.secondtry.entities.many.Ent1;
import my.playground.orm.secondtry.entities.many.Ent2;
import my.playground.orm.secondtry.entities.many.Ent3;
import my.playground.orm.secondtry.entities.map.MappedEmail;
import my.playground.orm.secondtry.entities.map.MappedMessage;
import my.playground.orm.secondtry.entities.nomap.NonMappedEmail;
import my.playground.orm.secondtry.entities.nomap.NonMappedMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionUtil {
    private static final SessionUtil instance = new SessionUtil();
    private final SessionFactory factory;

    private SessionUtil() {
        final var registry = new StandardServiceRegistryBuilder().configure().build();
        this.factory = new MetadataSources(registry)
            .addPackage("my.playground.orm.secondtry.entities")
            .addAnnotatedClass(Person.class)
            .addAnnotatedClass(Ranking.class)
            .addAnnotatedClass(Skill.class)
            .addAnnotatedClass(NonMappedMessage.class)
            .addAnnotatedClass(NonMappedEmail.class)
            .addAnnotatedClass(MappedEmail.class)
            .addAnnotatedClass(MappedMessage.class)
            .addAnnotatedClass(MappedEmailLazy.class)
            .addAnnotatedClass(MappedMessageLazy.class)
            .addAnnotatedClass(MappedEmailEager.class)
            .addAnnotatedClass(MappedMessageEager.class)
            .addAnnotatedClass(JoinTableParent.class)
            .addAnnotatedClass(JoinTableInheritor.class)
            .addAnnotatedClass(SingleTableParent.class)
            .addAnnotatedClass(SingleTableInheritor.class)
            .addAnnotatedClass(SeparateTableParent.class)
            .addAnnotatedClass(SeparateTableInheritor.class)
            .addAnnotatedClass(Ent1.class)
            .addAnnotatedClass(Ent2.class)
            .addAnnotatedClass(Ent3.class)
            .buildMetadata()
            .buildSessionFactory();
    }

    public static Session getSession() {
        return instance.factory.openSession();
    }

    public static void close() {
        instance.factory.close();
    }

}
