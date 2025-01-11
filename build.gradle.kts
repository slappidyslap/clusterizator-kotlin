plugins {
  java
  application
  id("org.jetbrains.kotlin.jvm") version "1.9.25"
  id("org.javamodularity.moduleplugin") version "1.8.15"
  id("org.openjfx.javafxplugin") version "0.0.13"
  id("org.beryx.jlink") version "2.25.0"
}

group = "kg.musabaev.clusterizator"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  maven {
    url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    mavenContent {
      snapshotsOnly()
    }
  }
}

java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile> {
  options.encoding = "UTF-8"
}

application {
  mainModule.set("kg.musabaev.cluserizator")
  mainClass.set("kg.musabaev.cluserizator.Launcher")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions {
    jvmTarget = "21"
  }
}

javafx {
  version = "21.0.5"
  modules = listOf("javafx.controls", "javafx.fxml", "javafx.web", "javafx.swing")
}

val mvvmFxVer by extra("1.9.0-SNAPSHOT")

dependencies {
  implementation(files("lib/scenicview.jar"))

  implementation("com.alibaba.fastjson2:fastjson2:2.0.53")

//  implementation("com.vladsch.javafx-webview-debugger:javafx-webview-debugger:0.8.0")
  implementation("de.saxsys:mvvmfx:$mvvmFxVer")
  implementation("de.saxsys:mvvmfx-easydi:$mvvmFxVer")
}

tasks.test {
  useJUnitPlatform()
}

jlink {
  imageZip.set(file("${buildDir}/distributions/app-${javafx.platform.classifier}.zip"))
  options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
  launcher {
    name = "app"
  }
}

tasks.named("jlinkZip") {
  group = "distribution"
}
