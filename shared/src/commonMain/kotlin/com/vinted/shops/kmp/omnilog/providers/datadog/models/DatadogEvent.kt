package com.vinted.shops.kmp.omnilog.providers.datadog.models

import com.datadog.kmp.log.LogLevel
import com.vinted.shops.kmp.omnilog.Event

abstract class DatadogEvent : Event() {
    abstract override val name: String
    abstract override val properties: Map<String, Any?>
    open val logLevel: LogLevel = LogLevel.INFO
    open val topic: DatadogTopic? = null
    open val error: Throwable? = null

    override fun toString(): String {
        return "DatadogEvent(name='$name', properties=$properties, logLevel=$logLevel, topic=$topic, error=$error)"
    }
}
