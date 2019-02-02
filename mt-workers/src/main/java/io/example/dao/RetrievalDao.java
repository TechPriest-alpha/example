package io.example.dao;

import io.example.dto.PrivateData;
import io.example.dto.PrivateDataRequest;
import io.example.dto.PublicData;
import io.example.dto.PublicDataRequest;
import org.springframework.stereotype.Service;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@Service
public class RetrievalDao {
    public PublicData fetch(final PublicDataRequest msg) {
        return null;
    }

    public PrivateData fetch(final PrivateDataRequest msg) {
        return null;
    }
}
