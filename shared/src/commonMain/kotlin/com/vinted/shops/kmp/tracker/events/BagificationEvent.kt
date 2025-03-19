package com.vinted.shops.kmp.tracker.events

import com.datadog.kmp.log.LogLevel
import com.vinted.shops.kmp.omnilog.providers.datadog.models.DatadogEvent
import com.vinted.shops.kmp.omnilog.providers.datadog.models.DatadogTopic

open class BagificationEvent(
    message: String,
    private val result: Result,
    override val logLevel: LogLevel = LogLevel.INFO,
) : DatadogEvent() {

    override val name: String = message
    override val topic: DatadogTopic = DatadogTopic.BAGIFICATION
    override val properties: Map<String, Any?> = mapOf(
        RESULT_KEY to result.name,
    ).apply {
        if (result is Result.Error) {
            plus(ERROR_KEY to result.error.message)
        }
    }

    // TODO Should we enable this?
//    override val error: Throwable? = (result as? Result.Error)?.error

    companion object {
        internal const val RESULT_KEY = "result"
        internal const val ERROR_KEY = "error"
        internal const val BAG_CODE_KEY = "bag_code"
        internal const val PARCEL_CODE_KEY = "parcel_code"
    }

    // TODO Remove name once we found a way to take the name from the result class type
    // e.g. result.javaClass.simpleName
    sealed class Result(val name: String) {
        data object Success : Result("Success")
        data class Error(val error: Throwable) : Result("Error")
    }
}


class ParcelAddedToBagEvent(
    result: Result,
    private val parcelCode: String,
    private val bagCode: String,
) : BagificationEvent(
    message = "Parcel Added To Bag",
    result = result,
) {
    override val properties: Map<String, Any?>
        get() = super.properties.apply {
            plus(
                hashMapOf(
                    PARCEL_CODE_KEY to parcelCode,
                    BAG_CODE_KEY to bagCode,
                )
            )
        }
}

class ParcelRemovedFromBagEvent(
    result: Result,
    private val parcelCode: String,
    private val bagCode: String,
) : BagificationEvent(
    message = "Parcel Removed From Bag",
    result = result,
) {
    override val properties: Map<String, Any?>
        get() = super.properties.apply {
            plus(
                mapOf(
                    PARCEL_CODE_KEY to parcelCode,
                    BAG_CODE_KEY to bagCode,
                )
            )
        }
}

class ParcelWithoutBagEvent(
    result: Result,
    private val parcelCode: String,
) : BagificationEvent(
    message = "Parcel Kept Without Bag",
    result = result,
) {
    override val properties: Map<String, Any?>
        get() = super.properties.apply {
            plus(
                PARCEL_CODE_KEY to parcelCode,
            )
        }
}

class BagOpenedEvent(
    result: Result,
    private val bagCode: String,
) : BagificationEvent(
    message = "Bag Opened",
    result = result,
) {
    override val properties: Map<String, Any?>
        get() = super.properties.apply {
            plus(
                BAG_CODE_KEY to bagCode,
            )
        }
}
