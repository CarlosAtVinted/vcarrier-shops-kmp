package com.vinted.shops.kmp.omnilog

interface Event {
    val name: String
    val properties: Map<String, Any>
}
