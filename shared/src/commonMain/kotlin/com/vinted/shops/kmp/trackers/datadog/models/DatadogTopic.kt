package com.vinted.shops.kmp.trackers.datadog.models

enum class DatadogTopic(val value: String) {
    LOGIN("login"),
    BAGIFICATION("bagification"),
    FEATURE_SWITCH("feature_switch"),
    AUTH0_MIGRATION("auth0_migration"),
}
