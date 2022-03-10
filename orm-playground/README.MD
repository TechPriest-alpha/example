Basic application to learn ORM features with Hibernate.
Core functionality is as follows:
1. There are clients of the system.
2. Clients might require help of an assistant.
3. Only one assistant can be assigned to a client at any time.
4. Assistants can have a feedback from clients and/or other assistants.

Main goal was to implement it in a sort of DDD way. 
Like: there are roots in form of client entities, that receive domain events and update their state accordingly.
First attempt was to implement is as a purely standalone application, no container, no intrusive framework.
Just basics of DI (weld) and ORM (hibernate).

Discovered issues:
1. Transaction propagation. We either have to leak ORM details (like entity manager) in order to start transactions on
the topmost level, or invent some other ways to emulate JTA features available in any JEE container.
For example the case: client registration even arrives -> we need perform series of operations (create user, create client, record event in an event log) in a transaction.
If we try to keep domain objects (client, user, event) as clean as possible, with minimal intrusion of infrastructure in 
form of event publishers, orm services, etc.; then we need somehow indicate transaction start at the start of the event processing.
And ensure this transaction is propagated properly.
With any app server and EJB (or any other standard means) this should be much more simple to achieve.
2. Business and supporting logic intertwine. Ideally our domain objects should know nothing about OR or DI or event bus services.
However, this rule is broker right from the start as Hibernate annotations get into all domain objects.
To make matters worse, Hibernate (and JPA itself) impose severe limitations on object structure of entities (field finalization, use or records) 
which make code much more verbose and complex than it should be. 
Unfortunately only other option is to implement custom Unit-of-Work and/or fallback to other approaches (myBatis, jOOQ) which will be considered in a separate app.
3. Effective way to exchange events within application. While weld provides facility for event exchange, it still requires 
certain adjustments to application logic. So we again either have to accept non-pure solutions, or write a lot of custom boilerplate (possible with the use of Reflection API).
