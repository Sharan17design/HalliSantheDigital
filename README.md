# Halli-Santhe Digital (Retail)

Hyper-local marketplace Android app for rural artisans. Kotlin + Room + MVVM, fully offline.

## Open in Android Studio
1. **Android Studio Hedgehog (2023.1.1) or newer** is required (AGP 8.2.2 / Kotlin 1.9.22 / Gradle 8.4 / JDK 17).
2. File → Open → select this folder (the one containing `settings.gradle.kts`).
3. When prompted, accept Android SDK licenses and let Gradle sync.
4. Run on a device/emulator with **API 24+**.

## If sync fails
- Make sure **Gradle JDK = 17** (Settings → Build Tools → Gradle → Gradle JDK).
- Ensure `compileSdk 34` platform is installed via SDK Manager.
- File → Invalidate Caches / Restart.
- Internet required on first sync to download dependencies.

## Project structure
```
HalliSantheDigital/
├── settings.gradle.kts
├── build.gradle.kts
├── gradle.properties
├── gradle/wrapper/{gradle-wrapper.jar, gradle-wrapper.properties}
├── gradlew, gradlew.bat
└── app/
    ├── build.gradle.kts
    ├── proguard-rules.pro
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/com/halli/santhe/
        │   ├── data/{Product, ProductDao, AppDatabase, ProductRepository}.kt
        │   ├── ui/viewmodel/ProductViewModel.kt
        │   ├── ui/adapter/ProductAdapter.kt
        │   └── ui/activity/{MainActivity, AddProductActivity, ProductDetailActivity}.kt
        └── res/
            ├── layout/{activity_main, item_product, activity_add_product, activity_product_detail}.xml
            ├── drawable/{ic_placeholder, ic_add, ic_call, bg_card}.xml
            ├── values/{strings, colors, themes}.xml
            ├── values-night/themes.xml
            ├── mipmap-anydpi-v26/ic_launcher{,_round}.xml
            ├── mipmap-*/ic_launcher{,_round}.png
            └── xml/{backup_rules, data_extraction_rules}.xml
```
