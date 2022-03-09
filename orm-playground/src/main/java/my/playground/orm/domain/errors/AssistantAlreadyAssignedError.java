package my.playground.orm.domain.errors;

import my.playground.orm.entities.UserEntity;
import my.playground.orm.entities.sub.AssistantEntity;

public class AssistantAlreadyAssignedError extends BusinessException {
    public AssistantAlreadyAssignedError(final UserEntity client, final AssistantEntity currentAssistant, final AssistantEntity targetAssistant) {
        super(String.format(
            "Client %s:'%s' already has assistant %s:'%s' while assigning %s:'%s'",
            client.getId(), client.getName(), currentAssistant.getId(), currentAssistant.getName(), targetAssistant.getId(), targetAssistant.getName()
        ));
    }
}
