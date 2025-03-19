package com.vinted.shops.kmp.tracker.events.models

internal const val SUBMISSION_METHOD_KEY = "submission_method"

enum class SubmissionMethod(val value: String) {
    SCAN("scan"),
    KEYBOARD("keyboard"),
    TROUBLE_SCANNING("trouble_scanning"),
}
