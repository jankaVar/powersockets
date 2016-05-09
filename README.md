# powersockets
Android game for Game Design


To build and run on desktop paste execute the following in the project's root-folder:

```
./gradlew desktop:run
```

To build and run it on android, first check if your device is in debug mode and
shows up in the android debug bridge (`adb devices`). Then run:

```
./gradlew android:installDebug android:run
```


