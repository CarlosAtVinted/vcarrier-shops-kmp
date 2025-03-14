package com.vinted.shops.kmp.trackers.datadog

import com.datadog.kmp.Datadog
import com.datadog.kmp.DatadogSite
import com.datadog.kmp.SdkLogVerbosity
import com.datadog.kmp.core.configuration.Configuration
import com.datadog.kmp.log.Logger
import com.datadog.kmp.log.Logs
import com.datadog.kmp.log.configuration.LogsConfiguration
import com.datadog.kmp.privacy.TrackingConsent
import com.datadog.kmp.rum.Rum
import com.datadog.kmp.rum.configuration.RumConfiguration
import com.vinted.shops.kmp.example.getPlatform
import com.vinted.shops.kmp.omnilog.Event
import com.vinted.shops.kmp.omnilog.TrackerProvider
import com.vinted.shops.kmp.trackers.datadog.models.DatadogEvent
import com.vinted.shops.kmp.trackers.datadog.models.DatadogUser

class DatadogProvider(
    // TODO Move all initial config here to the LIB once we find a way to do it in KMP
    clientToken: String,
    applicationId: String,
    env: String,
    variant: String = NO_VARIANT,
    // context should be application context on Android and can be null on iOS
    context: Any? = null,
) : TrackerProvider {

    override val id: String
        get() = "Datadog [${getPlatform().name}]"

    private var logger: Logger? = null

    init {
        initializeDatadog(context, clientToken, env, variant)
        initializeRum(applicationId)
        setupLogsConfig()

        logger = setupLogger()
    }

    private fun initializeDatadog(
        context: Any?,
        clientToken: String,
        env: String,
        variant: String
    ) {
        val configuration = Configuration.Builder(
            clientToken = clientToken,
            env = env,
            variant = variant,
            service = SERVICE_NAME,
        )
            .useSite(DatadogSite.EU1)
            .trackCrashes(true)
            .build()

        Datadog.apply {
            initialize(
                context = context,
                configuration = configuration,
                trackingConsent = TrackingConsent.PENDING,
            )
            verbosity = SdkLogVerbosity.DEBUG
        }
    }

    private fun initializeRum(applicationId: String) {
        val rumConfiguration = getRUMConfiguration(applicationId)

        rumConfiguration?.let {
            Rum.enable(rumConfiguration)
        }
    }

    private fun setupLogsConfig() {
        val logsConfig = LogsConfiguration.Builder().build()
        Logs.enable(logsConfig)
    }

    fun setUser(user: DatadogUser?) {
        user?.let {
            Datadog.setUserInfo(
                id = user.id,
                extraInfo = mapOf(
                    PARCEL_SHOP_ID_KEY to user.parcelShopId,
                    USER_ROLE_KEY to user.role,
                )
            )
        } ?: run {
            Datadog.setUserInfo(null)
        }
    }

    fun grantTracking() {
        Datadog.setTrackingConsent(TrackingConsent.GRANTED)
    }

    fun pauseTracking() {
        Datadog.setTrackingConsent(TrackingConsent.PENDING)
        setUser(null)
    }

    override fun log(event: Event) {
        val datadogEvent = event as? DatadogEvent ?: return

        logger?.let { logger ->
            with(event) {
                topic?.let { logger.addTag(TOPIC_KEY, it.value.lowercase()) }
                logger.log(
                    priority = logLevel,
                    throwable = error,
                    message = datadogEvent.name,
                    attributes = mapOf(
                        ATTRIBUTES_KEY to datadogEvent.properties
                    ),
                )
                topic?.let { logger.removeTagsWithKey(TOPIC_KEY) }
            }
        }
    }

    companion object {
        internal const val SERVICE_NAME = "Vinted Go Shops"
        internal const val NO_VARIANT = ""

        private const val PLATFORM_KEY = "platform"
        private const val PARCEL_SHOP_ID_KEY = "parcelShopId"
        private const val USER_ROLE_KEY = "userRole"
        private const val TOPIC_KEY = "topic"
        private const val ATTRIBUTES_KEY = "event_attributes"
    }
}

internal expect fun getRUMConfiguration(
    applicationId: String,
): RumConfiguration?

internal expect fun setupLogger(): Logger
