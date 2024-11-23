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

1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/fetch-rewards-coding-exercise.git
2. Rebuild Gradle oe edit any build configurations to fit your emulator:
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

4. Click the green run button on the top
   
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
- Add a refresh button if the server returns a non 200 response to retry
- Prompt the user to enable internet if offline

## Demo
https://github.com/user-attachments/assets/af79aaf8-fffa-43c6-9d71-1bb124a35104
