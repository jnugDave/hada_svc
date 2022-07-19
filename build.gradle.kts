import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
	kotlin("kapt") version "1.6.21"
}

group = "com.shoplworks"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	/** web */
	implementation("org.springframework.boot:spring-boot-starter-web")

	/** security */
	implementation("org.springframework.boot:spring-boot-starter-security")

	/** oauth2 client */
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

	/** validation */
	implementation("org.springframework.boot:spring-boot-starter-validation")

	/** json */
	implementation("org.json:json:20211205")

	/** model mapper */
	implementation("org.modelmapper:modelmapper:3.0.0")

	/** mysql */
	implementation("mysql:mysql-connector-java")

	/** jpa */
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	/** springdoc */
	implementation("org.springdoc:springdoc-openapi-ui:1.6.6")

	/** jwt */
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	/** query dsl */
	implementation("com.querydsl:querydsl-jpa") // 1)
	kapt(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa") // 2)
	kotlin.sourceSets.main {
		println("kotlin sourceSets buildDir :: $buildDir")
		setBuildDir("$buildDir")
	}

	/** test */
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
	/** mock */
	testImplementation("org.mockito:mockito-core:4.4.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}


tasks.withType<Test> {
	useJUnitPlatform()
}
