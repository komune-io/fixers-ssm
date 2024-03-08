package ssm.data.spring.autoconfigure

import java.lang.reflect.Method
import org.springframework.aot.generate.GenerationContext
import org.springframework.aot.hint.ExecutableMode
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotContribution
import org.springframework.beans.factory.aot.BeanFactoryInitializationAotProcessor
import org.springframework.beans.factory.aot.BeanFactoryInitializationCode
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.util.ReflectionUtils
import ssm.chaincode.dsl.config.SsmChaincodeConfig
import ssm.couchdb.dsl.config.SsmCouchdbConfig

class SsmDataAutoConfigurationBeanFactoryInitializationAotProcessor : BeanFactoryInitializationAotProcessor {
    override fun processAheadOfTime(bf: ConfigurableListableBeanFactory): BeanFactoryInitializationAotContribution {
        return  BeanFactoryInitializationAotContribution {
            ctx: GenerationContext, code: BeanFactoryInitializationCode? ->
            val hints = ctx.runtimeHints
            val dataCouchdbSsmConfigMethod: Method = ReflectionUtils.findMethod(
                DataSsmAutoConfiguration::class.java,
                DataSsmAutoConfiguration::dataCouchdbSsmConfig.name,
                SsmDataProperties::class.java
            )!!
            hints.reflection().registerMethod(dataCouchdbSsmConfigMethod, ExecutableMode.INVOKE)

            val ssmChaincodeConfigMethod: Method = ReflectionUtils.findMethod(
                DataSsmAutoConfiguration::class.java,
                DataSsmAutoConfiguration::ssmChaincodeConfig.name,
                SsmDataProperties::class.java
            )!!
            hints.reflection().registerMethod(ssmChaincodeConfigMethod, ExecutableMode.INVOKE)

            hints.reflection().registerType(SsmDataProperties::class.java)
            hints.reflection().registerType(SsmCouchdbConfig::class.java)
            hints.reflection().registerType(SsmChaincodeConfig::class.java)

            SsmCouchdbConfig::class.java.declaredConstructors.forEach {
                hints.reflection().registerConstructor(it, ExecutableMode.INVOKE)
            }
            SsmDataProperties::class.java.declaredConstructors.forEach {
                hints.reflection().registerConstructor(it, ExecutableMode.INVOKE)
            }
            SsmChaincodeConfig::class.java.declaredConstructors.forEach {
                hints.reflection().registerConstructor(it, ExecutableMode.INVOKE)
            }

            hints.resources().registerPattern("cloudant-parent.properties")

        }
    }
}
