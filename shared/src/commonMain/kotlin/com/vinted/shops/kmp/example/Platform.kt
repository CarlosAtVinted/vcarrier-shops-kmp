package com.vinted.shops.kmp.example

interface Platform {
    val name: String
    val version: String
}

expect fun getPlatform(): Platform
