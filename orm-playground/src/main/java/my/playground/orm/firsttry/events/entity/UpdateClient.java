package my.playground.orm.firsttry.events.entity;

import my.playground.orm.firsttry.entities.Client;

public record UpdateClient(Client client) implements FromClient {
}
