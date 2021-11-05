# COMP90018-selfcare-app

SelfCare is an app built using Kotlin and Jetpack Compose designed for users to have better awareness of their mental health, productivity and to help them better engage with their surroundings. It promotes both movement and relaxation for those who have been physically inactive or feeling fatigued due to  lockdown.

To run this app, you need to use **Android Studio**. You can clone this repository or import the project from Android Studio.

## API Level Required
`API level 29`

## How to Run the App
<ol><li>Preliminaries:
<ul><li> Install Android Studio</li>
 <li>Install an Android Virtual Device with API Level 29, ensuring that Back Camera is set to VirtualScene</li>
  </ul></li>
  <li>Open Android Studio and import the repo folder as a project</li>
  <li>Sync Gradle dependencies and build the project</li>
<li> Ensure that the configuration to be built is called “app” and the virtual device that you have installed is selected</li>
  <li>Run the “app” configuration to start the application</li></ol>


# Features
Functionalities of selfcare include:
- **Account Management**: Create and authenticate account through Google Firebase Authentication.
- **Chat**: Share a conversation with the app's companion. The companion will be able to see how the user is feeling by using sentiment analysis provided by paralleldots API. They will then react according to the user's emotion to recommend a movie or book if the user is feeling down. The companion will also be able to act as a mental health diary for the user by aggregating the user’s moods during a certain time period from when they chatted with the companion in the past. 
- **Go-Live + Augmented Reality (AR)**: Go-live is an easy way to interact with your surroundings and gets you moving. Playing the AR coin game, users can get rewarded through being able to purchase new AR mascots to interact with. Go-live implements google’s ARCore and utilises the phone's camera, accelerometer, gyroscope and magnetometer to calculate motion tracking, environmental understanding and light estimation. 
- **Reminders**: Create and set small daily reminders. Aimed at giving the user achievable goals that they can set for the day and enabling a way for the user to experience small wins and stay productive. When the time the user set hits, regardless of whether the application is open or not, the user will receive a notification, reminding them to complete the task they have set for themselves. 
- **Breathe**:  Follow along a relaxing breathing exercise with the option of having calming music on or off. Designed to help users destress and focus on relaxation and meditation, breathe imposes a sense of calm and tranquility. If the user would prefer to meditate with their eyes closed, the phone also vibrates to notify the user to change their breathing. 
- **Store**: Use coins collected from the Go-live coin game to purchase various AR mascots from the store. Store is connected in real-time to Google Firebase Realtime Database.
- **Settings**: Personalise the app further by setting a username and choose between an app-wide light and dark mode. In settings, users are also able to update other account management choices such as updating password or deleting account.

# Technologies/Libraries Used
- **Language**: Kotlin
- **Component Library**: Jetpack Compose
- **Architecture**: MVVM
- **Local storage**: ROOM Database, DataStore
- **Remote storage**: Firebase Realtime Database
- **View Models**: Loaded via Dagger-Hilt Dependency Injection
- **Sentiment Analysis**: Komprehend.io (paralleldots API)
- **Augmented Reality**: AR Core
- **User Authentication**: Firebase Authentication

# Sensors Used
- **Go-Live**: Camera, Accelerometer, Gyroscope, Magnetometer
- **Reminders**: Calendar

# Screenshots
**Sign-Up & Log-In Screen**</br>
<img src="https://github.com/r4dhiDj/COMP90018-selfcare-app/blob/main/screenshots/register.png" height="450">&nbsp; &nbsp;<img src="https://github.com/r4dhiDj/COMP90018-selfcare-app/blob/main/screenshots/login.png" height="450"></br>
**Menu Screen**</br>
<img src="https://github.com/r4dhiDj/COMP90018-selfcare-app/blob/main/screenshots/menu.png" height="450"></br>
**Chat**</br>
<img src="https://github.com/r4dhiDj/COMP90018-selfcare-app/blob/main/screenshots/chat.png" height="450"></br>
**Go-Live**</br>
<img src="https://github.com/r4dhiDj/COMP90018-selfcare-app/blob/main/screenshots/golive.png" height="450"></br>
**Reminders**</br>
<img src="https://github.com/r4dhiDj/COMP90018-selfcare-app/blob/main/screenshots/reminders.png" height="450"></br>
**Breathe**</br>
<img src="https://github.com/r4dhiDj/COMP90018-selfcare-app/blob/main/screenshots/breathe.png" height="450"></br>
**Store**</br>
<img src="https://github.com/r4dhiDj/COMP90018-selfcare-app/blob/main/screenshots/store.png" height="450"></br>
**Settings**</br>
<img src="https://github.com/r4dhiDj/COMP90018-selfcare-app/blob/main/screenshots/settings.png" height="450"></br>



