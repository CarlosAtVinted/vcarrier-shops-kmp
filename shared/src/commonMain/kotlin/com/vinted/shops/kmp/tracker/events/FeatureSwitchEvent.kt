package com.vinted.shops.kmp.tracker.events

import com.vinted.shops.kmp.omnilog.providers.datadog.models.DatadogEvent
import com.vinted.shops.kmp.omnilog.providers.datadog.models.DatadogTopic

open class FeatureSwitchEvent(
    override val name: String,
    private val result: Result,
) : DatadogEvent() {
    override val topic: DatadogTopic = DatadogTopic.FEATURE_SWITCH
    override val properties: Map<String, Any?> = mapOf(
        RESULT_KEY to result,
    ).apply {
        if (result is Result.Error) {
            plus(
                mapOf(
                    ERROR_KEY to result.errorMessage,
                    ERROR_CODE_KEY to result.errorCode
                )
            )
        }
    }

    companion object {
        const val RESULT_KEY = "result"
        const val ERROR_KEY = "error"
        const val ERROR_CODE_KEY = "error_code"
        const val REASON_KEY = "reason"
        const val VARIANT_KEY = "variant"
        const val VALUE_KEY = "value"
        const val FEATURE_SWITCH_NAME_KEY = "feature_switch_name"
    }

    // TODO Remove name once we found a way to take the name from the result class type
    // e.g. result.javaClass.simpleName
    sealed class Result(val name: String) {
        data object Success : Result("Success")
        data class Error(val errorCode: String, val errorMessage: String) : Result("Error")
    }
}

class FeatureSwitchEvaluatedEvent(
    result: Result,
    private val flagKey: String,
    private val value: String,
    private val variant: String? = null,
    private val reason: String? = null,
) : FeatureSwitchEvent(
    name = "Feature Switch Evaluated",
    result = result,
) {
    override val properties: Map<String, Any?>
        get() = super.properties.apply {
            plus(
                mapOf(
                    FEATURE_SWITCH_NAME_KEY to flagKey,
                    VALUE_KEY to value,
                ).apply {
                    variant?.let { VARIANT_KEY to it }
                    reason?.let { REASON_KEY to it }
                }
            )
        }
}
