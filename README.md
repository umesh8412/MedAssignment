# MVVM Android App with Jetpack Compose

https://github.com/user-attachments/assets/0f6a7d86-0858-4412-a8ef-a1e5172297c7

## Overview

This project is a simple Android application built using the MVVM architecture pattern with Jetpack Compose for UI development. It allows users to log in and displays a greeting based on the time of day. The app fetches a list of medicines from a mock API and displays them in a lazy list. Tapping on each medicine card navigates to a detailed view. The app also uses Room for local data storage and Hilt for dependency injection.

## Features

1. **Login Screen**: A simple login interface with no validation.
2. **Greeting Message**: Displays a greeting message based on the time of day and the user's entered username/email.
3. **Medicines List**: Fetches medicine data from a mock API and displays it in a lazy list.
4. **Detail View**: Each medicine card is clickable, leading to a detailed view of the selected medicine.
5. **Room Database**: Stores user data locally.
6. **Unit Tests**: Includes unit tests for core functionality.

## Technologies Used

- **Kotlin**: Programming language.
- **Jetpack Compose**: UI toolkit for building native Android UIs.
- **MVVM Architecture**: For a clean and manageable code structure.
- **Hilt**: Dependency injection library for Android.
- **Room**: Database



 library for local storage.
- **Retrofit**: Library for handling API requests.
- **Navigation Component**: For screen navigation.
- **JUnit**: For unit testing.

## Setup


### Clone the Repository

```bash
git clone  https://github.com/umesh8412/MedAssignment.git
