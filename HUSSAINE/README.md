# HUSSAINE

A native Android security-learning companion with a dark green console-inspired interface.

## Features in this starter
- Dashboard and quick navigation
- Safe simulated terminal (`help`, `about`, `status`, `clear`)
- Manual network notes (no Wi-Fi scanning)
- Everyday security-learning cards
- Offline-first; no account or API key required
- **No Android permissions declared** in the manifest

## Safety and scope
This is an independently written starter project. It is not a code copy of StrykerOSS. It intentionally excludes active network scanning, password attacks, exploit execution, wireless attacks, HID injection, root/chroot setup, and access to other devices. The terminal is simulated and never executes shell commands.

## Build
Requirements: Android Studio, JDK 17, Android SDK Platform 35.

1. Open this folder in Android Studio.
2. Allow Gradle sync to finish.
3. Select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
4. Debug APK output: `app/build/outputs/apk/debug/app-debug.apk`.

Command line (after adding the Gradle wrapper from Android Studio or a local Gradle install):
```bash
gradle assembleDebug
```

## Publish on GitHub
Create an empty repository named `HUSSAINE`, then from this folder:
```bash
git init
git add .
git commit -m "Initial HUSSAINE Android app"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/HUSSAINE.git
git push -u origin main
```

A GitHub Actions workflow is included to build a debug APK on each push.

## API keys
None required for the current version. Do not commit private API keys or signing passwords.
