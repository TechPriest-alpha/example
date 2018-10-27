package io.example.dao;

import io.example.dto.PrivateData;
import io.example.dto.PrivateDataRequest;
import io.example.dto.PublicData;
import org.springframework.stereotype.Service;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@Service
public class AuditDao {
    public void recordAccess(final PrivateDataRequest msg) {

    }

    public void recordUnsuccessfulAccess(final PrivateDataRequest msg) {

    }

    public void recordSuccessfulAccess(final PrivateDataRequest msg) {

    }

    public void recordSuccessfulAccess(final PrivateData msg) {

    }

    public void recordUnsuccessfulAccess(final PrivateData msg) {

    }

    public void recordSuccessfulAccess(final PublicData msg) {

    }

    public void recordUnsuccessfulAccess(final PublicData msg) {

    }
}
