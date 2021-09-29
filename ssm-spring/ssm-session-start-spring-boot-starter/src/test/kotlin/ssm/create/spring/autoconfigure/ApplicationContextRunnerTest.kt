package ssm.create.spring.autoconfigure

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.cloud.function.context.FunctionCatalog
import ssm.session.start.spring.autoconfigure.SsmSessionStartAutoConfiguration
import ssm.session.start.spring.autoconfigure.SsmSessionStartProperties
import ssm.test.cucumber.spring.ApplicationContextBuilder
import ssm.test.cucumber.spring.ApplicationContextRunnerBuilder


class ApplicationContextRunnerTest {

	@Test
	fun `spring context runner must must start`() {
		ApplicationContextRunnerBuilder()
			.buildContext(SsmChaincodeConfigTest.localDockerComposeParams).run { context ->
				assertThat(context).hasSingleBean(FunctionCatalog::class.java)
				assertThat(context).hasSingleBean(SsmSessionStartProperties::class.java)
				assertThat(context).hasBean(SsmSessionStartAutoConfiguration::ssmSessionStartFunction.name)
			}
	}


	@Test
	fun `spring context must must start`() {
		val context = ApplicationContextBuilder().create(
			types = arrayOf(ApplicationContextBuilder.SimpleConfiguration::class.java),
			config = SsmChaincodeConfigTest.localDockerComposeParams
		)
		assertThat(context.getBean(SsmSessionStartAutoConfiguration::ssmSessionStartFunction.name)).isNotNull
		assertThat(context.getBean(FunctionCatalog::class.java)).isNotNull
	}
}
