package com.kvsinyuk.stickergenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StickerGeneratorApplication

fun main(args: Array<String>) {
    runApplication<StickerGeneratorApplication>(*args)
}
