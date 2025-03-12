package com.vinted.shops.kmp.example

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
