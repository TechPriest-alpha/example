package com.example.spring.modulith.study.calculation.internal;

import com.example.spring.modulith.study.calculation.dto.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
class TransactionLogger {

    /**
     * Logs domain events only after the transaction that published them has committed.
     * Events published outside a transaction are ignored (there is nothing to commit).
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void afterCommit(DomainEvent event) {
        log.trace("Transaction committed for domain event: {}", event);
    }
}
