package ssm.sdk.json

import java.time.LocalDateTime
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class JsonUtilsTest {

	@Test
	fun `handle LocalDateTime`() {
		val time = LocalDateTime.now()
		val json = JsonUtils.toJson(WithLocalDateTime(time))
		val data = JsonUtils.toObject(json, WithLocalDateTime::class.java)
		Assertions.assertThat(data.time).isEqualTo(time)
	}

	@Test
	fun `handle LocalDateTime as map key`() {
		val time = LocalDateTime.now()
		val json = JsonUtils.toJson(WithLocalDateTimeAsMapKey(mapOf(time to "test")))
		val data = JsonUtils.toObject(json, WithLocalDateTimeAsMapKey::class.java)
		Assertions.assertThat(data.times).containsKey(time)
	}
}

data class WithLocalDateTime(
	val time: LocalDateTime
)

data class WithLocalDateTimeAsMapKey(
	val times: Map<LocalDateTime, String>
)