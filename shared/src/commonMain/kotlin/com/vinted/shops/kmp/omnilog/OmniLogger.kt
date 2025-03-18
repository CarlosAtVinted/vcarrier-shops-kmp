package com.vinted.shops.kmp.omnilog

class OmniLogger(
    private val providers: List<TrackerProvider>,
) {
    fun log(event: Event) {
        providers.forEach { provider ->
            provider.log(event)
        }
    }

    class Builder {
        private val providers = mutableListOf<TrackerProvider>()

        fun addProviders(providers: List<TrackerProvider>) = apply {
            this.providers.addAll(providers)
        }

        fun build() = OmniLogger(providers)
    }
}
