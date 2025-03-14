package com.vinted.shops.kmp.omnilog

abstract class Event {
    abstract val name: String
    abstract val properties: Map<String, Any?>

    override fun toString(): String {
        return "Event(name=$name, properties=$properties)"
    }
}
