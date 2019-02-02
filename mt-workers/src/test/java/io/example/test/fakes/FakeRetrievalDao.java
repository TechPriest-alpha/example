package io.example.test.fakes;

import io.example.dao.RetrievalDao;
import io.example.dto.PrivateData;
import io.example.dto.PrivateDataRequest;
import io.example.dto.PublicData;
import io.example.dto.PublicDataRequest;

/**
 * @author Viktor
 * @since 2018-10-27
 */
public class FakeRetrievalDao extends RetrievalDao implements Sleeper {

    @Override
    public PublicData fetch(final PublicDataRequest msg) {
        delayMin();
        return new PublicData();
    }

    @Override
    public PrivateData fetch(final PrivateDataRequest msg) {
        delayMin();
        return new PrivateData();
    }
}
