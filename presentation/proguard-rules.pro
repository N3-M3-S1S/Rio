 # keep bind and inflate static methods of generated databinding class for viewbindingpropertydelegate reflection methods
-keepclassmembers class com.nemesis.rio.presentation.databinding.** {
    public static *** inflate(...);
    public static *** bind(...);
}

 # do not obfuscate parcelable classes' names to not break navigation component arguments
-keepnames class * extends android.os.Parcelable

# keep all construcrors for koin reflection initialization
-keepclassmembers class * { public <init>(...); }

