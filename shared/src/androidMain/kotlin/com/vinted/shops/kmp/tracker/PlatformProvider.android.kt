package com.vinted.shops.kmp.tracker

import com.vinted.shops.kmp.BuildConfig
import com.vinted.shops.kmp.example.getPlatform
import com.vinted.shops.kmp.omnilog.Event
import com.vinted.shops.kmp.omnilog.TrackerProvider
import timber.log.Timber

actual class PlatformProvider(
    debugTimberTree: Timber.Tree = Timber.DebugTree(),
) : TrackerProvider {
    actual override val id: String = "${getPlatform().name} Default Logger"
    actual override fun log(event: Event) {
        Timber.tag(id).d("[${event.name}] ${event.properties}")
    }

    init {
        if (!hasDebugTree() && BuildConfig.DEBUG) {
            Timber.plant(debugTimberTree)
        }
    }

    private fun hasDebugTree() = Timber.forest().any { it is Timber.DebugTree }
}
