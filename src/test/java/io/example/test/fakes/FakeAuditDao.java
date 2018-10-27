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
        delay();
    }

    @Override
    public void recordUnsuccessfulAccess(final PrivateDataRequest msg) {
        delay();
    }

    @Override
    public void recordSuccessfulAccess(final PrivateDataRequest msg) {
        delay();
    }

    @Override
    public void recordSuccessfulAccess(final PrivateData msg) {
        delay();
    }

    @Override
    public void recordUnsuccessfulAccess(final PrivateData msg) {
        delay();
    }

    @Override
    public void recordSuccessfulAccess(final PublicData msg) {
        delay();
    }

    @Override
    public void recordUnsuccessfulAccess(final PublicData msg) {
        delay();
    }
}
