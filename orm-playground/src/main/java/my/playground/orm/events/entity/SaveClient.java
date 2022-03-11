package my.playground.orm.events.entity;

import my.playground.orm.entities.Client;

public record SaveClient(Client client) implements FromClient {
}
