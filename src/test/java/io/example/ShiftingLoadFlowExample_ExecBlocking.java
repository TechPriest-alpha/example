package io.example;

import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@Slf4j
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShiftingLoadFlowExample_ExecBlocking extends ShiftingLoadFlowExample_Workers {

    @Override
    protected JsonObject getConfig() {
        return super.getConfig().put(Constants.EXECUTE_AS_BLOCKING, true);
    }

}
