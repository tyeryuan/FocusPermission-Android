# README

# FocusPermission-Android

权限管理

# 用法

查看地址：https://github.com/tyeryuan/FocusPermission-Android/wiki

# AndroidStudio版本注意事项
## 使用AndroidStudio3.5版本
需要修改settings.gradle文件内容，保留为:
```java
rootProject.name = "FocusPermission-Android"
include ':app'
include ':FocusPermission'
```

然后将工程的build.gradle，classpath改了，我这里改成了：
```java
classpath "com.android.tools.build:gradle:3.5.0"
```
添加：
```java
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

再将其他的build.gradle改下：
```java
compileSdk改为compileSdkVersion
minSdk改为minSdkVersion
targetSdk改为targetSdkVersion
```
