import com.android.build.gradle.internal.errors.DeprecationReporterImpl.Companion.clean

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}


//buildscript {
//    repositories {
//        google()
//    }
//    dependencies {
//        val nav_version = "2.7.6"
//        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
//    }
//}


buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        val kotlinVersion = "1.6.10"
        val navigationVersion = "2.5.0"

        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }


}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}
