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

dependencies {
  implementation(files("lib/scenicview.jar"))
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
  implementation("de.saxsys:mvvmfx:1.9.0-SNAPSHOT")
  implementation("de.saxsys:mvvmfx-easydi:1.9.0-SNAPSHOT")

  implementation("com.opencsv:opencsv:5.9")

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
