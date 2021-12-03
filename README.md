***
```
              |    |    |                 
             )_)  )_)  )_)              
            )___))___))___)\            
           )____)____)_____)\\
         _____|____|____|____\\\__
---------\                   /---------
  ^^^^^ ^^^^^^^^^^^^^^^^^^^^^
    ^^^^      ^^^^     ^^^    ^^
         ^^^^      ^^^

```
# boatgame

### Project Architecture
```
settings.gradle            <- definition of sub-modules. By default core, desktop, android, html, ios
build.gradle               <- main Gradle build file, defines dependencies and plugins
gradlew                    <- local Gradle wrapper
gradlew.bat                <- script that will run Gradle on Windows
gradle                     <- script that will run Gradle on Unix systems
local.properties           <- IntelliJ only file, defines Android SDK location

core/
    build.gradle           <- Gradle build file for core project*
    src/                   <- Source folder for all your game's code

desktop/
    build.gradle           <- Gradle build file for desktop project*
    src/                   <- Source folder for your desktop project, contains LWJGL launcher class

android/
    build.gradle           <- Gradle build file for android project*
    AndroidManifest.xml    <- Android specific config
    assets/                <- contains for your graphics, audio, etc.  Shared with other projects.
    res/                   <- contains icons for your app and other resources
    src/                   <- Source folder for your Android project, contains android launcher class

html/
    build.gradle           <- Gradle build file for the html project*
    src/                   <- Source folder for your html project, contains launcher and html definition
    webapp/                <- War template, on generation the contents are copied to war. Contains startup url index page and web.xml

ios/
    build.gradle           <- Gradle build file for the iOS project*
    src/                   <- Source folder for your iOS project, contains launcher
```
