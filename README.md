# Fetch Rewards Coding Exercise: Mobile Engineer Assessment

This repository contains the solution for the **Fetch Rewards Coding Exercise - Software Engineering - Mobile**. The app is a native Android application built in **Kotlin** to retrieve and display data from the provided API.

---

## Overview

The app fetches a JSON dataset from the URL:  
[https://fetch-hiring.s3.amazonaws.com/hiring.json](https://fetch-hiring.s3.amazonaws.com/hiring.json).

### Features

1. **Grouped by List ID**: Items are grouped based on the `listId` field.
2. **Sorted Data**: Results are sorted by `listId` and then alphabetically by the `name` field.
3. **Filtered Data**: Items with blank or `null` names are filtered out.
4. **Clean Display**: The data is displayed in an easy-to-read, expandable list format for better user experience.
5. **Modern Development Tools**: The app is built to be compatible with the latest stable Android development tools and OS.

---

## Installation Instructions

To build and run the app, follow these steps:

1. Clone this repository in the src folder of your IDE:
   ```bash
   git clone https://github.com/movech123/Fetch_Assesment_Android.git
2. Open the project from the folder Fetch_Assesment_Android and make sure the file contents are in the folder
   
3. Rebuild Gradle and edit any build configurations in build.gradle.kts(App) to fit your emulator:
   ```kotlin
   android {
    namespace = "com.fetch_assesment"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fetch_assesment"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
4. Check for the correct dependencies in the build.gradle.kts(App) file:
   ```kotlin
   dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    implementation(libs.retrofit)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

5. Click the green run button on the top

6. Navigate to src/test/ to test the sort/filtering functionality with JUnit5
   
# Technical Implementation

## Network Stack

- **OkHttp3**: Efficient HTTP client for handling network requests
  - Custom interceptors for logging
  - Timeout configurations
  - Error handling middleware

## JSON Processing

- **Moshi**: Modern JSON parsing library for Kotlin
  - Kotlin codegen for type-safe parsing
  - Custom adapters for flexible data handling
  - Null safety handling

## Animations

### Logo Screen
- Custom animated logo on app launch
  - Vector-based scaling animation
  - Pop-Out animation


### List Animations
- Smooth transitions for data display
  - Item loading animations
  - Scroll state animations
  - Group expansion/collapse effects

## Future Improvements

- Implement offline caching to save unchanged data
- Add pull-to-refresh functionality for new data
- Add search, edit, insert, and delete functionality
- Unit Testing for more specific API failures

## Demo
https://github.com/user-attachments/assets/af79aaf8-fffa-43c6-9d71-1bb124a35104

## Edge Case Demo 
- Broke the link to the AWS server to simulate a failed API request

https://github.com/user-attachments/assets/68dfacdd-171b-4434-8ddd-068ed7d4a32b

- Working link with no internet

[Only_No_Internet.webm](https://github.com/user-attachments/assets/390d0157-968b-4056-a0f9-50d9125f90dd)
