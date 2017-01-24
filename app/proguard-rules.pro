# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/Sergio/android-sdks/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn com.squareup.okhttp.**
-dontwarn okio.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

-keep class sun.misc.Unsafe { *; }
#your package path where your gson models are stored

-keep public class coop.biantik.traductor.model.** { *; }
-keep public class coop.biantik.traductor.enums.** { *; }
-keep public class coop.biantik.traductor.network.beans.** { *; }
-keep public class coop.biantik.traductor.network.enums.** { *; }


-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}



-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v4.** { *; }
-keep interface android.support.v7.** { *; }

# Keep Facebook classes

-keep class com.mobsandgeeks.saripaar.** {*;}
-keep class commons.validator.routines.** {*;}

