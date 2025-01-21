plugins {
  java
  application
  id("org.jetbrains.kotlin.jvm") version "1.9.25"
  id("org.javamodularity.moduleplugin") version "1.8.15"
  id("org.openjfx.javafxplugin") version "0.0.13"
  id("org.beryx.jlink") version "3.1.1"
}

group = "kg.musabaev.clusterizator"
version = "0.9.0"

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

application {
  mainModule.set("kg.musabaev.cluserizator")
  mainClass.set("kg.musabaev.cluserizator.Launcher")
}

tasks.withType<JavaCompile> {
  options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions {
    jvmTarget = "21"
  }
}

val javaFxVer = "21.0.5"
val mvvmFxVer by extra("1.9.0-SNAPSHOT")

javafx {
  version = javaFxVer
  modules = listOf("javafx.controls", "javafx.fxml", "javafx.web", "javafx.swing")
}

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
  addExtraDependencies("javafx")
  imageZip.set(file("${layout.buildDirectory}/distributions/app-${javafx.platform.classifier}.zip"))
  options.set(listOf("--strip-debug", "--compress", "zip-6", "--no-header-files", "--no-man-pages"))
  launcher {
    name = "clusterizator"
    noConsole = true
  }

  jpackage {
    val installerType = project.findProperty("installerType") // we will pass this from the command line (example: -PinstallerType=msi)

    when (installerType) {
      "msi" -> {
        installerOptions =  listOf(
          "--verbose",
          "--win-per-user-install", "--win-dir-chooser",
          "--win-menu", "--win-shortcut",)
      }
      "deb" -> {
        installerOptions = listOf(
          "--linux-deb-maintainer", "musabaeveldiar@gmail.com", "--linux-shortcut")
      }
      "rpm" -> {
        installerOptions = listOf(
          "--linux-rpm-license-type", "GPLv3", "--linux-shortcut")
      }
      "dmg" -> {
        installerOptions = listOf(
          "--mac-package-name", "Clusterizator",
          "--mac-package-identifier", "kg.musabaev.clusterizator")
      }
    }
  }
}

tasks.named("jlinkZip") {
  group = "distribution"
}
