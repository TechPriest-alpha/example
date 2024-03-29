package my.playground.orm.firsttry.domain.errors;

import my.playground.orm.firsttry.entities.UserEntity;

public class AssistantSameAsClientError extends BusinessException {
    public AssistantSameAsClientError(final UserEntity user) {
        super(String.format("Can not assign user %s:'%s' as an assistant to himself", user.getId(), user.getName()));
    }
}
