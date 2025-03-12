package com.vinted.shops.kmp.omnilog

interface TrackerProvider {
    val id: String
    fun log(event: Event)
}
