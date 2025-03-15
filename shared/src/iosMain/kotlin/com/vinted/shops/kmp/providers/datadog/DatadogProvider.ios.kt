package com.vinted.shops.kmp.providers.datadog

import com.datadog.kmp.log.LogLevel
import com.datadog.kmp.log.Logger
import com.datadog.kmp.rum.configuration.RumConfiguration
import com.datadog.kmp.rum.configuration.trackUiKitActions
import com.datadog.kmp.rum.configuration.trackUiKitViews
import com.datadog.kmp.rum.tracking.DefaultUIKitRUMActionsPredicate
import com.datadog.kmp.rum.tracking.DefaultUIKitRUMViewsPredicate
import com.vinted.shops.kmp.providers.datadog.DatadogProvider.Companion.SERVICE_NAME

internal actual fun setupLogger() = Logger.Builder()
    .setName(SERVICE_NAME)
    .setNetworkInfoEnabled(true)
    .setRemoteLogThreshold(LogLevel.INFO)
    .build()

internal actual fun getRUMConfiguration(
    applicationId: String,
): RumConfiguration? = RumConfiguration.Builder(applicationId)
    .trackUiKitViews(DefaultUIKitRUMViewsPredicate())
    .trackUiKitActions(DefaultUIKitRUMActionsPredicate())
    // TODO Couldn't find: urlSessionTracking: RUM.Configuration.URLSessionTracking()
    .build()
