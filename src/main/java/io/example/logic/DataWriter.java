package io.example.logic;

import io.example.Addressing;
import io.example.dao.AccessDao;
import io.example.dao.AuditDao;
import io.example.dao.WriterDao;
import io.example.dto.PrivateData;
import io.example.dto.PublicData;
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
public class DataWriter extends BaseVerticle {

    private final AccessDao accessDao;
    private final AuditDao auditDao;
    private final WriterDao writerDao;


    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Addressing.PRIVATE_DATA_WRITER);
        startFuture.complete();
    }

    @Transactional
    @HandlerMethod
    public int handler(final PrivateData msg) {
        if (accessDao.writeAllowed(msg)) {
            val result = writerDao.store(msg);
            auditDao.recordSuccessfulAccess(msg);
            return result;
        } else {
            auditDao.recordUnsuccessfulAccess(msg);
            return -1;
        }
    }

    @Transactional
    @HandlerMethod
    public int checkAccess(final PublicData msg) {
        val result = writerDao.store(msg);
        auditDao.recordSuccessfulAccess(msg);
        return result;
    }
}
