package io.example.api;

import io.example.Addressing;
import io.example.dto.AccessCheckRequest;
import io.example.dto.PrivateData;
import io.example.dto.PrivateDataRequest;
import io.example.dto.PublicDataRequest;
import io.example.infrastructure.BaseVerticle;
import io.example.infrastructure.SpringVerticle;
import lombok.val;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@SpringVerticle
public class CommonApi extends BaseVerticle {

    public void insertPrivateData(final PrivateData privateData) {
        val sync = new ArrayBlockingQueue<Integer>(1);
        send(Addressing.PRIVATE_DATA_WRITER, privateData, reply -> {
            logger.info("Insertion result: {}", reply.result().body());
            put(sync, 1);
        });
        poll(sync);
    }

    public void readPrivateData(final PrivateDataRequest privateDataRequest) {
        val sync = new ArrayBlockingQueue<Integer>(1);

        send(Addressing.PRIVATE_DATA_READER, privateDataRequest, reply -> {
            logger.info("Read private result: {}", reply.result().body());
            put(sync, 2);
        });
        poll(sync);
    }

    public void readPublicData(final PublicDataRequest publicDataRequest) {
        val sync = new ArrayBlockingQueue<Integer>(1);

        send(Addressing.PUBLIC_DATA_READER, publicDataRequest, reply -> {
            logger.info("Read public result: {}", reply.result().body());
            put(sync, 3);
        });
        poll(sync);
    }

    public void checkPrivateAccess(final AccessCheckRequest privateDataRequest) {
        val sync = new ArrayBlockingQueue<Integer>(1);

        send(Addressing.PRIVATE_DATA_READER, privateDataRequest, reply -> {
            logger.info("Access check result: {}", reply.result().body());
            put(sync, 4);
        });
        poll(sync);
    }

    private void put(final ArrayBlockingQueue<Integer> sync, final int i) {
        try {
            logger.trace("Put: {} start", i);
            sync.offer(i, 100L, TimeUnit.SECONDS);
            logger.trace("Put: {} end", i);
        } catch (final InterruptedException e) {
            logger.error("Error", e);
        }
    }

    private void poll(final ArrayBlockingQueue<Integer> sync) {
        try {
            logger.trace("Sync result: {}", sync.poll(100L, TimeUnit.SECONDS));
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
