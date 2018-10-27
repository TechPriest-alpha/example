package io.example.test.cfg;

import io.example.dao.AccessDao;
import io.example.dao.AuditDao;
import io.example.dao.RetrievalDao;
import io.example.dao.WriterDao;
import io.example.test.fakes.FakeAccessDao;
import io.example.test.fakes.FakeAuditDao;
import io.example.test.fakes.FakeRetrievalDao;
import io.example.test.fakes.FakeWriterDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@Configuration
public class TestCfg {

    @Bean
    public AccessDao accessDao() {
        return new FakeAccessDao();
    }
    @Bean
    public AuditDao auditDao() {
        return new FakeAuditDao();
    }
    @Bean
    public RetrievalDao retrievalDao() {
        return new FakeRetrievalDao();
    }
    @Bean
    public WriterDao writerDao() {
        return new FakeWriterDao();
    }

}
