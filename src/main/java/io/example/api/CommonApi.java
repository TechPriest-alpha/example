package io.example.api;

import io.example.Addressing;
import io.example.dto.PrivateData;
import io.example.infrastructure.BaseVerticle;
import io.example.infrastructure.SpringVerticle;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@SpringVerticle
public class CommonApi extends BaseVerticle {

    public void insertPrivateData(final PrivateData privateData) {
        send(Addressing.PRIVATE_DATA_WRITER, privateData, reply -> {
            logger.info("Insertion result: {}", reply.result().body());
        });
    }

    public void readPrivateData(final PrivateData privateData) {
        send(Addressing.PRIVATE_DATA_READER, privateData, reply -> {
            logger.info("Read private result: {}", reply.result().body());
        });
    }

    public void readPublicData(final PrivateData privateData) {
        send(Addressing.PUBLIC_DATA_READER, privateData, reply -> {
            logger.info("Read public result: {}", reply.result().body());
        });
    }

    public void checkPrivateAccess(final PrivateData privateData) {
        send(Addressing.PRIVATE_DATA_READER, privateData, reply -> {
            logger.info("Access check result: {}", reply.result().body());
        });
    }


}
