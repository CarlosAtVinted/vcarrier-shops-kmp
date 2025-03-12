package com.vinted.shops.kmp.example

import platform.UIKit.UIDevice

data class IOSPlatform(
    override val name: String = UIDevice.currentDevice.systemName(),
    override val version: String = UIDevice.currentDevice.systemVersion
) : Platform

actual fun getPlatform(): Platform = IOSPlatform()
