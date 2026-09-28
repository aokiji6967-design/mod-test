# Simple HUD (Fabric, Minecraft 1.21.11)
Shows FPS, XYZ, facing, real-world clock and ping in the top-left corner.

## Build locally
Needs JDK 21 and Gradle 9.2+ (or run `gradle wrapper` once, then `./gradlew build`).
    gradle build
Jar: build/libs/simplehud-1.0.0.jar  (not the -sources jar)

## Build with no local setup
Push this folder to a GitHub repo -> Actions tab -> "Build mod" -> download the `simplehud-jar` artifact.

## Install
Install Fabric Loader + Fabric API for 1.21.11, then drop the jar into .minecraft/mods.
