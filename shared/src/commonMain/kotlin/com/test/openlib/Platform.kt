package com.test.openlib

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform