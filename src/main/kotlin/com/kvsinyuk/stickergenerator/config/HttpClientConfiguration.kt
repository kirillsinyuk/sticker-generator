package com.kvsinyuk.stickergenerator.config

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class HttpClientConfiguration {
    @Bean
    fun httpClient() =
        OkHttpClient.Builder()
            .writeTimeout(Duration.ofSeconds(5))
            .build()
}
