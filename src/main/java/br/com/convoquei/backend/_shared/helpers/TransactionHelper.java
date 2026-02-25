package br.com.convoquei.backend._shared.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class TransactionHelper {

    private static final Logger log = LoggerFactory.getLogger(TransactionHelper.class);
    private final TransactionTemplate requiresNew;

    public TransactionHelper(PlatformTransactionManager txManager) {
        this.requiresNew = new TransactionTemplate(txManager);
        this.requiresNew.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    public void executeWithNewTransaction(Runnable action) {
        requiresNew.executeWithoutResult(status -> {
            try {
                action.run();
            }
            catch (RuntimeException ex) {
                status.setRollbackOnly();

                log.warn("Falha na transacao REQUIRES_NEW (caller={}). Fluxo principal continua normalmente. Erro: {}", caller(), ex.getMessage(), ex);
            }
        });
    }

    private String caller() {
        var st = Thread.currentThread().getStackTrace();
        var e = st[4];
        return e.getClassName() + "#" + e.getMethodName() + ":" + e.getLineNumber();
    }
}
