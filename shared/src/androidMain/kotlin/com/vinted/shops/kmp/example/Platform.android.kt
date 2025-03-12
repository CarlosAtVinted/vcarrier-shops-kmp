package com.vinted.shops.kmp.example

class AndroidPlatform(
    override val name: String = "Android",
    override val version: String = android.os.Build.VERSION.SDK_INT.toString(),
) : Platform

actual fun getPlatform(): Platform = AndroidPlatform()
