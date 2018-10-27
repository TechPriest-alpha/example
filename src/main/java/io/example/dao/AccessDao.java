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
public class AccessDao {
    public boolean readAllowed(final PrivateDataRequest msg) {
        return false;
    }

    public boolean writeAllowed(final PrivateData msg) {
        return false;
    }

    public boolean writeAllowed(final PublicData msg) {
        return false;
    }
}
