package my.playground.orm.events.entity;

import my.playground.orm.entities.Client;

public record UpdateClient(Client client) implements FromClient {
}
