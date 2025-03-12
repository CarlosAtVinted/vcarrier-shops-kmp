package com.vinted.shops.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform