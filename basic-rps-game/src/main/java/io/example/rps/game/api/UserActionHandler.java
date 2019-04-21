package io.example.rps.game.api;

import io.example.rps.game.model.UserAction;

public interface UserActionHandler {

    void handleUserAction(final UserAction userAction);
}
