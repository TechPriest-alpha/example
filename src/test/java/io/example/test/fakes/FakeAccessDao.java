package io.example.test.fakes;

import io.example.dao.AccessDao;
import io.example.dto.AccessCheckRequest;
import io.example.dto.PrivateData;
import io.example.dto.PrivateDataRequest;
import io.example.dto.PublicData;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Viktor
 * @since 2018-10-27
 */
public class FakeAccessDao extends AccessDao implements Sleeper {
    @Override
    public boolean readAllowed(final PrivateDataRequest msg) {
        delay();
        return ThreadLocalRandom.current().nextInt() % 2 == 0;
    }

    @Override
    public boolean writeAllowed(final PrivateData msg) {
        delay();
        return ThreadLocalRandom.current().nextInt() % 2 == 0;
    }

    @Override
    public boolean writeAllowed(final PublicData msg) {
        delay();
        return ThreadLocalRandom.current().nextInt() % 2 == 0;
    }

    @Override
    public boolean readAllowed(final AccessCheckRequest msg) {
        delay();
        return ThreadLocalRandom.current().nextInt() % 2 == 0;
    }
}
