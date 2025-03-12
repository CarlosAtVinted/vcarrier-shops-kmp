package com.vinted.shops.kmp.omnilog

class Logger(
    private val providers: List<TrackerProvider>,
) {
    fun log(event: Event) {
        providers.forEach { provider ->
            provider.log(event)
        }
    }

    class Builder {
        private val providers = mutableListOf<TrackerProvider>()

        fun addProviders(vararg providers: TrackerProvider) = apply {
            this.providers.addAll(providers)
        }

        fun build() = Logger(providers)
    }
}
