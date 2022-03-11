package my.playground.orm.events.entity;

import my.playground.orm.entities.UserEntity;

public record CreateUser(UserEntity user) implements FromClient {

    public boolean shouldPersist() {
        return user.getId() == null;
    }
}
