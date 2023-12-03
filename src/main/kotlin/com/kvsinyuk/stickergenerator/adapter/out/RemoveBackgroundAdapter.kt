package com.kvsinyuk.stickergenerator.adapter.out

import com.kvsinyuk.stickergenerator.applicaiton.port.out.RemoveBackgroundPort
import com.kvsinyuk.stickergenerator.domain.ImageData
import mu.KLogging
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RemoveBackgroundAdapter(
    private val client: OkHttpClient
): RemoveBackgroundPort {

    @Value("\${http.bg-removal.base-url}")
    private lateinit var backgroundRemoveBaseUrl: String

    override fun removeBackground(imageData: ImageData): ImageData {
        val response = callForBackgroundRemove(imageData)
        return response.body
            ?.let { body -> imageData.copy(image = body.bytes()) }
            ?: run {
                logger.error { response.message }
                throw RuntimeException(response.message)
            }
    }

    private fun callForBackgroundRemove(imageData: ImageData): Response {
        val request: Request = Request.Builder()
            .url("$backgroundRemoveBaseUrl/api/remove")
            .post(
                MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "file",
                        imageData.originalName,
                        imageData.image.toRequestBody())
                    .build()
            )
            .build()

        return client.newCall(request)
            .execute()
    }

    companion object: KLogging()
}