package com.vinted.shops.kmp.providers.datadog

import com.datadog.kmp.log.Logger
import com.datadog.kmp.rum.configuration.RumConfiguration
import com.datadog.kmp.rum.configuration.trackUserInteractions
import com.datadog.kmp.rum.configuration.useViewTrackingStrategy
import com.datadog.kmp.rum.tracking.FragmentViewTrackingStrategy
import com.vinted.shops.kmp.providers.datadog.DatadogProvider.Companion.SERVICE_NAME

internal actual fun setupLogger() = Logger.Builder()
    .setService(SERVICE_NAME)
    .build()

internal actual fun getRUMConfiguration(
    applicationId: String,
): RumConfiguration? = RumConfiguration.Builder(applicationId)
    .trackUserInteractions()
    .trackLongTasks()
    .useViewTrackingStrategy(FragmentViewTrackingStrategy(true))
    .build()
