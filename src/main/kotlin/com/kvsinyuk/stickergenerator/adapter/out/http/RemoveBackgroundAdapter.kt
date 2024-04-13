package com.kvsinyuk.stickergenerator.adapter.out.http

import com.kvsinyuk.stickergenerator.applicaiton.port.out.http.RemoveBackgroundPort
import com.kvsinyuk.stickergenerator.applicaiton.utils.toBufferedImage
import com.kvsinyuk.stickergenerator.applicaiton.utils.toByteArray
import mu.KLogging
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage

@Component
class RemoveBackgroundAdapter(
    private val client: OkHttpClient,
) : RemoveBackgroundPort {
    @Value("\${http.bg-removal.base-url}")
    private lateinit var backgroundRemoveBaseUrl: String

    override fun removeBackground(
        image: BufferedImage,
        originalFilename: String,
    ): BufferedImage {
        val response = callForBackgroundRemove(image, originalFilename)
        return response.body?.bytes()
            ?.toBufferedImage()
            ?: run {
                logger.error { response.message }
                throw RuntimeException(response.message)
            }
    }

    private fun callForBackgroundRemove(
        image: BufferedImage,
        originalFilename: String,
    ): Response {
        val request: Request =
            Request.Builder()
                .url("$backgroundRemoveBaseUrl/api/remove")
                .post(
                    MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart(
                            "file",
                            originalFilename,
                            image.toByteArray().toRequestBody(),
                        )
                        .build(),
                )
                .build()

        return client.newCall(request)
            .execute()
    }

    companion object : KLogging()
}
