
## Technology Used / Tech Stack

[![JDK](https://img.shields.io/badge/openjdk-21.0.3-437291?style=for-the-badge&logo=openJdk&logoColor=white)](https://openjdk.org/)
[![Android Studio](https://img.shields.io/badge/Android_Studio-2024.2.1_Patch_3-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)](https://developer.android.com/studio)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.24-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](http://kotlinlang.org)
[![KSP](https://img.shields.io/badge/KSP-1.9.24--1.0.20-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://github.com/google/ksp)
[![Gradle](https://img.shields.io/badge/gradle-8.9-02303A?style=for-the-badge&logo=gradle&logoColor=white)](https://developer.android.com/studio/releases/gradle-plugin)
[![Hilt](https://img.shields.io/badge/hilt-2.51.1-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/training/dependency-injection/hilt-android)
[![Navigation](https://img.shields.io/badge/Navigation-2.8.5-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/jetpack/androidx/releases/navigation)
[![lifecycle](https://img.shields.io/badge/Lifecycle-2.8.7-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/jetpack/androidx/releases/lifecycle)
[![retrofit](https://img.shields.io/badge/Retrofit-2.11.0-000000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/square/retrofit)
[![Gson Converter](https://img.shields.io/badge/Converter_Gson-2.11.0-000000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/square/retrofit/blob/trunk/retrofit-converters/gson/README.md)
[![Logging Interceptor](https://img.shields.io/badge/Logging_Interceptor-4.9.1-000000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)
[![Data Store](https://img.shields.io/badge/Data_Store_Preference-1.0.0-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/jetpack/androidx/releases/datastore)
[![Room Database](https://img.shields.io/badge/Room_Database-2.6.1-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/jetpack/androidx/releases/room)
[![Coil](https://img.shields.io/badge/Coil-3.0.4-000000?style=for-the-badge&logo=github&logoColor=white)](https://coil-kt.github.io/coil/)
[![Shimmer Android](https://img.shields.io/badge/Shimmer_Android-0.5.0-0467DF?style=for-the-badge&logo=meta&logoColor=white)](https://github.com/facebookarchive/shimmer-android)
[![Swipe Refresh Layout](https://img.shields.io/badge/Swipe_Refresh_Layout-1.1.0-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout)
# TravelMate

**TravelMate** adalah aplikasi perencana perjalanan yang membantu pengguna menemukan destinasi yang diinginkan dan menyusun itinerary dengan mudah. Aplikasi ini memungkinkan pengguna untuk mencari destinasi wisata, mendapatkan informasi lengkap, dan memilih destinasi sesuai dengan preferensi.


## Table of Contents
- [Technology Used / Tech Stack](#technology-used--tech-stack)
- [Features](#features)
- [Installation (How to run the project)](#installation-how-to-run-the-project)
- [Tree / Folder Structure](#tree--folder-structure)
- [Architecture](#architecture)
- [Design Pattern](#design-pattern)
- [Todos](#todos)
- [Done](#done)
- [Flowchart](#flowchart)
- [Preview](#preview)
- [Demo](#demo)
- [Credit / Contributor(s)](#credit--contributors)
## Features

>- **Pencarian Destinasi**: Pengguna dapat mencari destinasi wisata sesuai keinginan
>- **Perencanaan Itinerary**: Pengguna dapat dengan mudah membuat dan mengelola itinerary perjalanan
>- **Sistem Rekomendasi**: Pengguna dapat memilih preferensi sesuai dengan destinasi wisata yang diinginkan

## Installation (How to run the project)

To run the project locally, follow these steps:

### 1. Clone the repository
>- ```https://github.com/zenmobiledev/travel-mate.git```
>- ```cd travel-mate```

### 2. Open the project
>- Launch your preferred Integrated Development Environment (IDE), such as Android Studio or IntelliJ IDEA. Then, open the ```travel-mate``` project directory within the IDE.

### 3. Build the project
Ensure that all necessary dependencies are installed. In Android Studio or IntelliJ IDEA, you can typically do this by:

>- **Syncing the Project**: The IDE should automatically prompt you to sync the project with the Gradle files. If not, you can manually sync by clicking on the "Sync Project with Gradle Files" button.
>- **Building the Project:** Navigate to the ```Build``` menu and select ```Build Project```. This process will compile the code and prepare the application for running.

### 4. Run the application
After the build process completes successfully:

>- **Select a Device**: Choose an emulator or a physical device connected to your computer where you want to run the application.

>- **Launch the App**: Click on the green 'Run' button (usually depicted as a play icon) in the IDE toolbar, or navigate to ```Run``` > ```Run 'app'```. This action will install and start the application on the selected device.
## Tree / Folder Structure

```
.
└── app
    └── src
        └── main
            └── java
                └── com
                    └── example
                        └── travelmate
                            ├── data
                            │   ├── mapper
                            │   ├── repository
                            │   └── source
                            │       ├── local
                            │       │   ├── dao
                            │       │   ├── database
                            │       │   ├── datasource
                            │       │   ├── entity
                            │       │   └── preference
                            │       └── remote
                            │           ├── api
                            │           ├── datasource
                            │           └── model
                            │               ├── destination
                            │               └── login
                            │                   ├── request
                            │                   └── response
                            ├── di
                            ├── domain
                            │   ├── model
                            │   │   ├── destination
                            │   │   └── login
                            │   ├── repositories
                            │   └── usecase
                            │       ├── category
                            │       ├── destination
                            │       ├── itinerary
                            │       └── login
                            ├── presentation
                            │   ├── feature
                            │   │   ├── category
                            │   │   ├── itinerary
                            │   │   │   └── view
                            │   │   │       ├── adapter
                            │   │   │       ├── itinerary
                            │   │   │       └── itinerarydetail
                            │   │   ├── login
                            │   │   ├── profile
                            │   │   └── travel
                            │   │       └── view
                            │   │           ├── adapter
                            │   │           ├── travel
                            │   │           └── traveldetail
                            │   └── main
                            └── utils
```
## Architecture
>- :exclamation: **COMING SOON**

## Design Pattern
>- :exclamation: **COMING SOON**

## Todos
>- [ ] Security and Testing

## Done
**Point Penilaian**:
>- [x] Core Functionalities
>- [x] Data Management and Recommendation System
>- [x] User Interface and Experience

**Fitur Aplikasi**:
- [x]  Pencarian Destinasi
- [x]  Perencanaan Itinerary
- [x]  Sistem Rekomendasi

## Flowchart
>- :exclamation: **COMING SOON**

## Preview
>- :exclamation: **COMING SOON**

## Demo
>- :exclamation: **COMING SOON**

## Credit / Contributor(s)

- [Zaenal Arif](https://github.com/zenmobiledev)

