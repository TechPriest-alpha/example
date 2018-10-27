package io.example.dao;

import io.example.dto.PrivateDataRequest;
import org.springframework.stereotype.Service;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@Service
public class AccessDao {
    public boolean allowed(final PrivateDataRequest msg) {
        return false;
    }
}
