# Pharmacy Management App

This is a Pharmacy Management App that allows users to manage return requests and items within those requests. Users can add, delete, and update items for a specific return request and list all items related to a return request.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the App](#running-the-app)
- [Usage](#usage)
- [Architecture](#architecture)
- [Decisions and Rationale](#decisions-and-rationale)
- [License](#license)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Android Studio Bumblebee or later
- Kotlin 1.5.31 or later
- Gradle 7.0.3 or later
- Internet connection for API calls

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/ahmedkashwaa/Yomicepa_Pharmacy.git
    cd pharmacy-management-app
    ```

2. Open the project in Android Studio:
    - Open Android Studio.
    - Select `Open an existing project`.
    - Navigate to the cloned repository and select the `pharmacy-management-app` folder.

3. Install the required dependencies:
    - Android Studio should automatically download and install the required dependencies. If not, sync the project with Gradle files manually.

4. Set up Hilt for Dependency Injection:
    - Ensure Hilt is properly configured in your project by following the setup instructions provided in the `build.gradle` files.

## Running the App

1. Connect an Android device or start an emulator.

2. Run the app:
    - Click on the green play button in Android Studio or use the shortcut `Shift + F10`.

## Usage

### Adding an Item

1. Navigate to the 'Add Item' screen.
2. Fill in the following details:
    - NDC
    - Description
    - Manufacturer
    - Full Quantity
    - Partial Quantity
    - Expiration Date
    - Lot Number
3. Click 'Add Item'.
4. A success message will be displayed, and the form will be cleared.

### Listing Items

1. Navigate to the 'Items' screen.
2. All items related to a specific return request will be listed.
3. You can delete or update the description of an item.

## Architecture

The app follows the MVVM (Model-View-ViewModel) architecture pattern to separate the UI logic from the business logic. 

- **Model**: Represents the data layer of the app. This includes the data models and repository classes.
- **ViewModel**: Holds the app's UI-related data and handles the business logic.
- **View**: The UI layer of the app, built using Jetpack Compose.

### Key Components

- **Hilt**: Used for dependency injection.
- **Retrofit**: Used for making API calls.
- **Jetpack Compose**: Used for building the UI.
- **StateFlow** and **MutableStateFlow**: Used for state management in ViewModel.

### Logging Interceptor

A logging interceptor is used to log the details of HTTP requests and responses. This helps in debugging by providing visibility into the network calls made by the app. It logs information such as request and response headers, body, and status codes.

## Decisions and Rationale

1. **Jetpack Compose**: Chosen for its modern declarative approach to building UIs, which allows for more intuitive and maintainable code.
2. **Hilt**: Selected for dependency injection to manage the dependencies more efficiently and reduce boilerplate code.
3. **StateFlow**: Used for state management in ViewModel to provide a reactive and consistent data flow to the UI components.
4. **MVVM Architecture**: Adopted to separate concerns and make the app easier to maintain and test.
5. **Retrofit**: Chosen for its simplicity and ease of integration for making network requests.
6. **Auth Interceptor**: Used to handle authentication by attaching tokens to every network request seamlessly.
7. **Logging Interceptor**: Implemented to provide detailed logs of HTTP requests and responses for easier debugging.
