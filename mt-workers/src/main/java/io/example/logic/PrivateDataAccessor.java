package io.example.logic;

import io.example.Addressing;
import io.example.dao.AccessDao;
import io.example.dao.AuditDao;
import io.example.dao.RetrievalDao;
import io.example.dto.AccessCheckRequest;
import io.example.dto.PrivateData;
import io.example.dto.PrivateDataRequest;
import io.example.infrastructure.BaseVerticle;
import io.example.infrastructure.HandlerMethod;
import io.example.infrastructure.SpringVerticle;
import io.vertx.core.Future;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Viktor
 * @since 2018-10-27
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@SpringVerticle(worker = true)
public class PrivateDataAccessor extends BaseVerticle {
    private final AccessDao accessDao;
    private final AuditDao auditDao;
    private final RetrievalDao retrievalDao;

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Addressing.PRIVATE_DATA_READER);
        startFuture.complete();
    }

    @Transactional
    @HandlerMethod
    public PrivateData handler(final PrivateDataRequest msg) {
        logger.debug("Data request: {}", msg);
        if (accessDao.readAllowed(msg)) {
            val result = retrievalDao.fetch(msg);
            auditDao.recordSuccessfulAccess(msg);
            return result;
        } else {
            auditDao.recordUnsuccessfulAccess(msg);
            return PrivateData.NULL;
        }
    }

    @HandlerMethod
    public boolean checkAccess(final AccessCheckRequest msg) {
        logger.debug("Access check: {}", msg);
        return accessDao.readAllowed(msg);
    }
}
