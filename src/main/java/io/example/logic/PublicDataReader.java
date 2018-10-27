package io.example.logic;

import io.example.Addressing;
import io.example.dao.RetrievalDao;
import io.example.dto.PublicData;
import io.example.dto.PublicDataRequest;
import io.example.infrastructure.BaseVerticle;
import io.example.infrastructure.HandlerMethod;
import io.example.infrastructure.SpringVerticle;
import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Viktor
 * @since 2018-10-27
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@SpringVerticle(worker = true)
public class PublicDataReader extends BaseVerticle {
    private final RetrievalDao retrievalDao;

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Addressing.PUBLIC_DATA_READER);
        startFuture.complete();
    }

    @Transactional
    @HandlerMethod
    public PublicData handler(final PublicDataRequest msg) {
        logger.debug("Data request: {}", msg);
        return retrievalDao.fetch(msg);
    }
}
