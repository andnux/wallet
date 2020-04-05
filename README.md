### 快速集成：
当前版本： [![](https://jitpack.io/v/andnux/wallet.svg)](https://jitpack.io/#andnux/wallet)
- **Step 1.** Add the JitPack repository to your build file
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
- **Step 2.** Add the dependency
```groovy
dependencies {
    def version = "lasted.version"
    implementation "com.github.andnux:wallet:${version}"
    or
    implementation "com.github.andnux.wallet:basic:${version}"
    implementation "com.github.andnux.wallet:bos:${version}"
    implementation "com.github.andnux.wallet:eos:${version}"
    implementation "com.github.andnux.wallet:eth:${version}"
    implementation "com.github.andnux.wallet:vsys:${version}"
    implementation "com.github.andnux.wallet:btc:${version}"
}
```