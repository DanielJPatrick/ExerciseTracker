package dragonfly.exercisetracker;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ExerciseApplication extends Application {
    private static final String PREFS_FILENAME = "SavedState";
    public static final int unsetIntValue = -1;
    public static SharedPreferences sharedPrefSavedState;

    @Override
    public void onCreate() {
        super.onCreate();

        ExerciseApplication.sharedPrefSavedState = this.getSharedPreferences(ExerciseApplication.PREFS_FILENAME, Context.MODE_PRIVATE);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("StrengthTracker.realm")
                .schemaVersion(6)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
