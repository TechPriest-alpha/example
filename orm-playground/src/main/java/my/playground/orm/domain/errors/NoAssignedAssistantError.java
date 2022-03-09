package my.playground.orm.domain.errors;

import my.playground.orm.entities.UserEntity;

public class NoAssignedAssistantError extends BusinessException {
    public NoAssignedAssistantError(final UserEntity user) {
        super(String.format("Client %s:'%s' has no assistant assigned", user.getId(), user.getName()));
    }
}
