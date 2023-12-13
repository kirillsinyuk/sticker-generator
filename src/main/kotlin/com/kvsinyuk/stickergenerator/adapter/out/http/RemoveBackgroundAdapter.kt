package com.kvsinyuk.stickergenerator.adapter.out.http

import com.kvsinyuk.stickergenerator.applicaiton.port.out.http.RemoveBackgroundPort
import com.kvsinyuk.stickergenerator.domain.StickerData
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

    override fun removeBackground(stickerData: StickerData): StickerData {
        val response = callForBackgroundRemove(stickerData)
        return response.body
            ?.let { body -> stickerData.copy(image = body.bytes()) }
            ?: run {
                logger.error { response.message }
                throw RuntimeException(response.message)
            }
    }

    private fun callForBackgroundRemove(stickerData: StickerData): Response {
        val request: Request = Request.Builder()
            .url("$backgroundRemoveBaseUrl/api/remove")
            .post(
                MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "file",
                        stickerData.originalFilename,
                        stickerData.image.toRequestBody())
                    .build()
            )
            .build()

        return client.newCall(request)
            .execute()
    }

    companion object: KLogging()
}