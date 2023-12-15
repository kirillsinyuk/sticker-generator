import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.20"
	kotlin("plugin.spring") version "1.9.20"
	kotlin("kapt") version "1.9.20"
}

group = "com.kvsinyuk"
version = "0.5.0"

val mapstructVersion = "1.5.3.Final"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb:3.2.0")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

	kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")
	implementation("org.mapstruct:mapstruct:$mapstructVersion")

	implementation("commons-io:commons-io:2.13.0")
	implementation("com.github.pengrad:java-telegram-bot-api:6.9.1")

	implementation(platform("com.squareup.okhttp3:okhttp-bom:4.11.0"))
	implementation("com.squareup.okhttp3:okhttp")
	implementation("com.squareup.okhttp3:logging-interceptor")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
