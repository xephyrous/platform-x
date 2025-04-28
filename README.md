![PlatformX Logo](https://github.com/xephyrous/platform-x/blob/master/composeApp/src/commonMain/composeResources/drawable/logo.png)

---

### Overview
- [What is PlatformX?](#what-is-platformx)
- [Dependencies](#dependencies)
- [Building](#building-and-deployment)

---

### What is PlatformX

PlatformX is a website for SEET lab activity management, created as a project for CS360.

---

### Dependencies

#### [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/)
 - PlatformX is built on the Kotlin Compose Multiplatform UI Framework, specifically targeting WASM for web deployment. It should be noted that the Kotlin/WASM interoperability is in _Alpha_ and can have major changes or unstable features.
 - In the future, PlatformX aims to be avaliable on desktop, Android, and IOS using Compose's code sharing system.

#### [Firebase](https://firebase.google.com/)
 - For its database and authentication system PlatformX uses Google's Firebase. PlatformX uses firebase's [REST API](https://firebase.google.com/docs/reference/rest/database) for communication with the service.

---

### Building and Deployment
 - From the `master` branch, the project can be built by using the `wasmJsBroswerDevelopmentRun` gradle task.
   - For development runs, use the `wasmJsBrowserRun` gradle task
 - From there, the deployable code can be found in the `composeApp/build/dist/wasmJs/productionExecutable` folder.

#### Build-specific Dependencies
 - Kotlin Compose
   - `compose.runtime`
   - `compose.foundation`
   - `compose.material`
   - `compose.materialIconsExtended`
   - `compose.ui`
   - `compose.components.resources`
   - `compose.components.uiToolingPreview`
- AndroidX
  - `libs.androidx.lifecycle.viewmodel`
  - `libs.androidx.lifecycle.runtime.compose`
  - `org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10`
- KTor
  - `io.ktor:ktor-client-core:3.1.2`
  - `io.ktor:ktor-client-js:3.1.2`
  - `io.ktor:ktor-client-json:3.1.2`
  - `io.ktor:ktor-client-serialization:3.1.2`
  - `io.ktor:ktor-serialization-kotlinx-json:3.1.2`
  - `io.ktor:ktor-client-js:3.1.2`
  - `io.ktor:ktor-client-content-negotiation:3.1.2`

---
