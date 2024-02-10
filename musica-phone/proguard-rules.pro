###### ----- CONFIG ----- ######

-optimizationpasses 5
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

###### ----- PROGUARD-ANDROID.txt ----- ######

# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontoptimize

-keepattributes *Annotation*
#-keep public class com.google.vending.licensing.ILicensingService
#-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends androidx.fragment.app.Fragment {
    <init>(...);
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-dontwarn android.support.**

-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }

-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

-keep class android.support.design.widget.** { *; }
-keep interface android.support.design.widget.** { *; }

#Apache Http Removed from Android 6+
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.http.SslCertificate
-dontwarn android.net.http.SslError
-dontwarn com.sun.org.apache.xml.internal.security**

###### ----- 3rd PARTY LIBS ----- ######

# ----- KOSHA SPECIFICS -----
-keepclassmembernames class com.musica.common.service.models.** { *; }
-keep class com.musica.common.service.models.** {*;}
-keepclassmembernames class com.musica.common.navigation.** {*;}
-keep class com.musica.common.navigation.** {*;}

#------Sentry io -----
-dontwarn javax.naming.**
-dontwarn javax.servlet.**
-dontwarn org.slf4j.**

# ----- Logging -----
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
    public static int wtf(...);
}

-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    Object writeReplace();
    Object readResolve();
}

#HMS obfuscation
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}

#Crashlytics
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

#Appsflyer
-keep class com.appsflyer.** { *; }
-keep public class com.android.installreferrer.** { *; }

-dontwarn java.lang.invoke.**
