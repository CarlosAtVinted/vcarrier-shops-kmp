package com.vinted.shops.kmp.omnilog.providers

import com.vinted.shops.kmp.example.getPlatform
import com.vinted.shops.kmp.omnilog.Event
import com.vinted.shops.kmp.omnilog.TrackerProvider

actual class PlatformProvider : TrackerProvider {
    actual override val id: String = "${getPlatform().name} Default Logger"
    actual override fun log(event: Event) {
        // TODO NSLog or just println?
        println("$id > [${event.name}] ${event.properties}")
    }
}
