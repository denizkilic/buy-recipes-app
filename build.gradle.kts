import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
	kotlin("plugin.allopen") version "1.9.23"
	kotlin("plugin.jpa") version "1.9.23"
	kotlin("kapt") version "1.9.23"
}

group = "com.deniz.recipes"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

	implementation("org.flywaydb:flyway-core:9.22.2")
	implementation("org.postgresql:postgresql:42.7.2")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
	testImplementation("org.mockito:mockito-core:5.11.0")
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.1")
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("com.h2database:h2")


}


tasks.withType<KotlinCompile> {
	kotlinOptions {
		jvmTarget = "17"
		freeCompilerArgs += "-Xjsr305=strict"
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.Embeddable")
	annotation("jakarta.persistence.MappedSuperclass")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets {
	create("intTest") {
		kotlin.srcDir("src/intTest/kotlin")
		resources.srcDir("src/intTest/resources")
		compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
		runtimeClasspath += output + compileClasspath
	}
}

tasks.named<Copy>("processIntTestResources") {
	from("src/intTest/resources")
	into("build/resources/intTest")
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.register<Test>("intTest") {
	description = "Runs integration tests."
	group = "verification"
	testClassesDirs = sourceSets["intTest"].output.classesDirs
	classpath = sourceSets["intTest"].runtimeClasspath
	useJUnitPlatform()
	dependsOn("processIntTestResources")
}

