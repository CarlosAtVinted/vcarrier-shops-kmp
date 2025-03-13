package com.vinted.shops.kmp.trackers

import com.vinted.shops.kmp.omnilog.Event
import com.vinted.shops.kmp.omnilog.TrackerProvider

expect class PlatformProvider : TrackerProvider {
    override val id: String
    override fun log(event: Event)
}
