package com.vinted.shops.kmp.tracker.events

import com.vinted.shops.kmp.omnilog.providers.datadog.models.DatadogEvent
import com.vinted.shops.kmp.tracker.events.models.SUBMISSION_METHOD_KEY
import com.vinted.shops.kmp.tracker.events.models.SubmissionMethod

data class PickupItemEvent(
    private val pickupCode: String,
    private val unformattedPickupCode: String?,
    private val submissionMethod: SubmissionMethod,
    private val result: Result,
) : DatadogEvent() {
    override val name = "item_resolved"
    override val properties: Map<String, Any?> = mapOf<String, Any?>(
        RESULT_KEY to result.name,
        SUBMISSION_METHOD_KEY to submissionMethod,
        PICKUP_CODE to pickupCode,
    ).apply {
        unformattedPickupCode?.let {
            plus(UNFORMATTED_PICKUP_CODE to unformattedPickupCode)
        }

        if (result is Result.Error) {
            plus(ERROR_KEY to result.error.toString())
        }
    }


    // TODO Should we enable this?
//    override val error: Throwable? = (result as? Result.Error)?.error

    companion object {
        const val RESULT_KEY = "result"
        const val ERROR_KEY = "error"
        const val PICKUP_CODE = "pickup_code"
        const val UNFORMATTED_PICKUP_CODE = "unformatted_pickup_code"
    }

    // TODO Remove name once we found a way to take the name from the result class type
    // e.g. result.javaClass.simpleName
    sealed class Result(val name: String) {
        data object Success : Result("Success")
        data class Error(val error: Throwable) : Result("Error")
    }
}
