package com.kvsinyuk.stickergenerator.infrastructure.http

import com.kvsinyuk.stickergenerator.applicaiton.domain.Image
import com.kvsinyuk.stickergenerator.applicaiton.port.out.http.FaceSwapPort
import com.kvsinyuk.stickergenerator.infrastructure.http.request.FaceSwapRequest
import com.kvsinyuk.stickergenerator.infrastructure.http.response.FaceSwapResponse
import mu.KLogging
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import tools.jackson.databind.json.JsonMapper
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Component
class FaceSwapAdapter(
    private val client: OkHttpClient,
    private val jsonMapper: JsonMapper,
) : FaceSwapPort {
    @Value("\${http.face-swap.base-url}")
    private lateinit var faceSwapBaseUrl: String

    @OptIn(ExperimentalEncodingApi::class)
    override fun swapFace(
        chatId: Long,
        sourceImage: Image,
        targetImage: Image,
    ): ByteArray =
        callForFaceSwap(chatId, sourceImage, targetImage).use { response ->
            if (!response.isSuccessful) {
                val body = response.body?.string().orEmpty()
                logger.error { "Face swap failed: ${response.code} $body" }
                throw RuntimeException("Face swap failed: ${response.code}")
            }
            Base64.decode(
                jsonMapper
                    .readValue(response.body!!.charStream(), FaceSwapResponse::class.java)
                    .output,
            )
        }

    @OptIn(ExperimentalEncodingApi::class)
    private fun callForFaceSwap(
        chatId: Long,
        sourceImage: Image,
        targetImage: Image,
    ): Response {
        val request: Request =
            Request
                .Builder()
                .url("$faceSwapBaseUrl/")
                .post(
                    jsonMapper
                        .writeValueAsString(
                            FaceSwapRequest(
                                Base64.encode(sourceImage.image),
                                Base64.encode(targetImage.image),
                                chatId.toString(),
                                sourceImage.fileName,
                                targetImage.fileName,
                            ),
                        ).toRequestBody(JSON),
                ).build()

        return client
            .newCall(request)
            .execute()
    }

    companion object : KLogging() {
        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    }
}
