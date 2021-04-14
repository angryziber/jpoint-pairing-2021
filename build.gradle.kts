import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.4.32"
  application
}

group = "jpoint.pairing"
version = "0.1-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.4.3")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")

  testImplementation(kotlin("test-junit5"))
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
  testImplementation("org.assertj:assertj-core:3.19.0")
  testImplementation("io.mockk:mockk:1.11.0")
}

sourceSets {
  getByName("main").apply {
    java.srcDirs("src")
    resources.srcDirs("src").exclude("**/*.kt")
  }
  getByName("test").apply {
    java.srcDirs("test")
    resources.srcDirs("test").exclude("**/*.kt")
  }
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "11"
}

application {
  mainClass.set("MainKt")
}
