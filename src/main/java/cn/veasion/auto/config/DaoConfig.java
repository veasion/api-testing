package cn.veasion.auto.config;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;
import java.util.HashMap;

/**
 * DaoConfig
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
@Configuration
public class DaoConfig {

    private static final String TX_EXPRESSION = "execution(* cn.veasion.auto.service..*.*(..))";

    @Bean
    public DefaultBeanFactoryPointcutAdvisor defaultBeanFactoryPointcutAdvisor(TransactionManager transactionManager) {
        DefaultBeanFactoryPointcutAdvisor advisor = new DefaultBeanFactoryPointcutAdvisor();

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Throwable.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        source.setNameMap(new HashMap<String, TransactionAttribute>() {{
            put("save*", requiredTx);
            put("update*", requiredTx);
            put("insert*", requiredTx);
            put("delete*", requiredTx);
            put("*WithTx", requiredTx);
        }});

        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(transactionManager);
        interceptor.setTransactionAttributeSource(source);

        advisor.setAdvice(interceptor);

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(TX_EXPRESSION);
        advisor.setPointcut(pointcut);

        return advisor;
    }

}
