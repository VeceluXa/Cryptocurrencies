plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.danilovfa.cryptocurrencies"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.danilovfa.cryptocurrencies"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.danilovfa.cryptocurrencies.InstrumentationTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.5")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    debugImplementation("androidx.fragment:fragment-testing:1.6.0")

    // Koin
    implementation("io.insert-koin:koin-android:3.4.2")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Room
    val roomVersion = "2.5.2"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")

    // Local Time jdk addon
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

    // Splash Screen API
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Swipe to refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")

    // Charts
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}