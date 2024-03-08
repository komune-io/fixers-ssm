package ssm.tx.config.spring.autoconfigure

import java.lang.reflect.Method
import org.springframework.aot.generate.GenerationContext
import org.springframework.aot.hint.ExecutableMode
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotContribution
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor
import org.springframework.beans.factory.aot.BeanFactoryInitializationCode
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.util.ReflectionUtils
import ssm.chaincode.dsl.config.SsmChaincodeConfig

class SsmTxAutoConfigurationBeanFactoryInitializationAotProcessor : BeanFactoryInitializationAotProcessor {
    override fun processAheadOfTime(bf: ConfigurableListableBeanFactory): BeanFactoryInitializationAotContribution {
        return  BeanFactoryInitializationAotContribution {
            ctx: GenerationContext, code: BeanFactoryInitializationCode? ->
            val hints = ctx.runtimeHints
            val method: Method = ReflectionUtils.findMethod(
                SsmTxAutoConfiguration::class.java,
                SsmTxAutoConfiguration::chaincodeSsmConfig.name,
                SsmTxProperties::class.java
            )!!
            hints.reflection().registerMethod(method, ExecutableMode.INVOKE)

            hints.reflection().registerType(SsmTxProperties::class.java)
            hints.reflection().registerType(SsmChaincodeConfig::class.java)

            SsmTxProperties.SignerAgentFileConfig::class.java.getDeclaredConstructors().forEach {
                hints.reflection().registerConstructor(it, ExecutableMode.INVOKE)
            }
        }
    }
}
