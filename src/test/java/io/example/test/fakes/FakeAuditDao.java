package io.example.test.fakes;

import io.example.dao.AuditDao;
import io.example.dto.PrivateData;
import io.example.dto.PrivateDataRequest;
import io.example.dto.PublicData;

/**
 * @author Viktor
 * @since 2018-10-27
 */
public class FakeAuditDao extends AuditDao implements Sleeper {

    @Override
    public void recordAccess(final PrivateDataRequest msg) {
        delayAvg();
    }

    @Override
    public void recordUnsuccessfulAccess(final PrivateDataRequest msg) {
        delayAvg();
    }

    @Override
    public void recordSuccessfulAccess(final PrivateDataRequest msg) {
        delayAvg();
    }

    @Override
    public void recordSuccessfulAccess(final PrivateData msg) {
        delayAvg();
    }

    @Override
    public void recordUnsuccessfulAccess(final PrivateData msg) {
        delayAvg();
    }

    @Override
    public void recordSuccessfulAccess(final PublicData msg) {
        delayAvg();
    }

    @Override
    public void recordUnsuccessfulAccess(final PublicData msg) {
        delayAvg();
    }
}
