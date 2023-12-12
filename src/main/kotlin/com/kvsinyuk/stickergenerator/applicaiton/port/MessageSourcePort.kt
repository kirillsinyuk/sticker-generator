package com.kvsinyuk.stickergenerator.applicaiton.port

interface MessageSourcePort {

    fun getMessage(code: String): String
}