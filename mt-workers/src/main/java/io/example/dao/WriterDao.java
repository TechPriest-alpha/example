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
public class WriterDao {

    public int store(final PrivateData msg) {
        return 0;
    }

    public int store(final PublicData msg) {
        return 0;
    }
}
