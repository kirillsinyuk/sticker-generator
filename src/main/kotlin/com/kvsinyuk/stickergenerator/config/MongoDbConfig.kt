package com.kvsinyuk.stickergenerator.config

import com.kvsinyuk.stickergenerator.domain.background.RemoveBackgroundData
import com.kvsinyuk.stickergenerator.domain.faceswap.FaceSwapData
import com.kvsinyuk.stickergenerator.domain.sticker.CreateStickerData
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.convert.ConfigurableTypeInformationMapper
import org.springframework.data.convert.SimpleTypeInformationMapper
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper.DEFAULT_TYPE_KEY
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@Configuration
@EnableConfigurationProperties(MongoProperties::class)
class MongoDbConfig(
    private val mongoProperties: MongoProperties,
) : AbstractMongoClientConfiguration() {
    override fun getDatabaseName(): String {
        return mongoProperties.database
    }

    override fun mongoClient(): MongoClient {
        val connectionString = ConnectionString(mongoProperties.uri)
        val mongoClientSettings =
            MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build()
        return MongoClients.create(mongoClientSettings)
    }

    @Bean
    fun configurableTypeInformationMapper() =
        ConfigurableTypeInformationMapper(
            mapOf(
                CreateStickerData::class.java to "create_sticker",
                FaceSwapData::class.java to "face_swap",
                RemoveBackgroundData::class.java to "remove_background",
            ),
        )

    override fun mappingMongoConverter(
        databaseFactory: MongoDatabaseFactory,
        customConversions: MongoCustomConversions,
        mappingContext: MongoMappingContext,
    ) = super.mappingMongoConverter(databaseFactory, customConversions, mappingContext).apply {
        setTypeMapper(
            DefaultMongoTypeMapper(
                DEFAULT_TYPE_KEY,
                listOf(
                    configurableTypeInformationMapper(),
                    SimpleTypeInformationMapper(),
                ),
            ),
        )
    }
}
