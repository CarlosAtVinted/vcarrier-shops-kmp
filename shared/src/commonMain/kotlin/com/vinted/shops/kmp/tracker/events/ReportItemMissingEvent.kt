package com.vinted.shops.kmp.tracker.events

import com.vinted.shops.kmp.omnilog.providers.datadog.models.DatadogEvent

class ReportItemMissingEvent(
    result: Result?,
    code: String?,
    itemStatus: String?,
    screen: String?,
    error: String?,
) : DatadogEvent() {
    override val name = "item_reported_missing"
    override val properties: Map<String, Any?> = mapOf(
        "result" to result,
        "code" to code,
        "item_status" to itemStatus,
        "screen" to screen,
        "error" to error,
    )

    enum class Result(val value: String) {
        SUCCESS("success"),
        ERROR("error"),
    }
}
