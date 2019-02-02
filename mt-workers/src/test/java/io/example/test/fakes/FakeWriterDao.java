package io.example.test.fakes;

import io.example.dao.WriterDao;
import io.example.dto.PrivateData;
import io.example.dto.PublicData;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Viktor
 * @since 2018-10-27
 */
public class FakeWriterDao extends WriterDao implements Sleeper {

    @Override
    public int store(final PrivateData msg) {
        delayMax();
        return ThreadLocalRandom.current().nextInt();
    }

    @Override
    public int store(final PublicData msg) {
        delayMax();
        return ThreadLocalRandom.current().nextInt();
    }
}
