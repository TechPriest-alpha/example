package my.playground.orm.firsttry.events.entity;

import my.playground.orm.firsttry.entities.UserEntity;

public record CreateUser(UserEntity user) implements FromClient {

    public boolean shouldPersist() {
        return user.getId() == null;
    }
}
