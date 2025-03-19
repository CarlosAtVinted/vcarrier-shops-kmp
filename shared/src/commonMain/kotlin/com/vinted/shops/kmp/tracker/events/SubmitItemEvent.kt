package com.vinted.shops.kmp.tracker.events

import com.vinted.shops.kmp.omnilog.providers.datadog.models.DatadogEvent
import com.vinted.shops.kmp.tracker.events.models.SubmissionMethod

class SubmitItemEvent(
    submissionMethod: SubmissionMethod?,
    result: Result?,
    code: String?,
    itemStatus: String?,
    screen: String?,
    error: String?,
) : DatadogEvent() {
    override val name: String = "item_submitted"
    override val properties: Map<String, Any?> = mapOf(
        "method" to submissionMethod,
        "result" to result,
        "code" to code,
        "item_status" to itemStatus,
        "screen" to screen,
        "error" to error,
    )

    enum class Result(val value: String) {
        SUCCESS("success"),
        ITEM_NOT_FOUND("item_not_found"),
        WRONG_ITEM("wrong_item"),
        BAD_DELIVERY("bad_delivery"),
        ERROR("error"),
    }
}
