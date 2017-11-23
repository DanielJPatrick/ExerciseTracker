package dragonfly.exercisetracker;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.stetho.Stetho;
import com.squareup.otto.Bus;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import javax.inject.Inject;

import dragonfly.exercisetracker.injection.components.AppComponent;
import dragonfly.exercisetracker.injection.components.BusComponent;
import dragonfly.exercisetracker.injection.components.DaggerAppComponent;
import dragonfly.exercisetracker.injection.components.DaggerBusComponent;
import dragonfly.exercisetracker.injection.components.DaggerDataComponent;
import dragonfly.exercisetracker.injection.components.DaggerDependencyGraphComponent;
import dragonfly.exercisetracker.injection.components.DataComponent;
import dragonfly.exercisetracker.injection.components.DependencyGraphComponent;
import dragonfly.exercisetracker.injection.modules.AppModule;
import dragonfly.exercisetracker.injection.modules.BusModule;
import dragonfly.exercisetracker.injection.modules.DataModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ExerciseApplication extends Application {
    private static final String PREFS_FILENAME = "SavedState";
    public static final int unsetIntValue = -1;
    public static SharedPreferences sharedPrefSavedState;
    public DependencyGraphComponent dependencyGraph;

    @Inject
    ExerciseApplication app;
    @Inject
    Bus bus;

    private void setupApplicationDependencyGraph() {
        AppModule appModule = new AppModule(this);
        BusModule busModule = new BusModule();
        DataModule dataModule = new DataModule();
        AppComponent appComponent = DaggerAppComponent.builder().appModule(appModule).build();
        BusComponent busComponent = DaggerBusComponent.builder().busModule(busModule).build();
        DataComponent dataComponent = DaggerDataComponent.builder().dataModule(dataModule).build();

        dependencyGraph = DaggerDependencyGraphComponent.builder()
                .appComponent(appComponent)
                .busComponent(busComponent)
                .dataComponent(dataComponent)
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ExerciseApplication.sharedPrefSavedState = this.getSharedPreferences(ExerciseApplication.PREFS_FILENAME, Context.MODE_PRIVATE);
        setupApplicationDependencyGraph();
        this.dependencyGraph.inject(this);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("StrengthTracker.realm")
                .schemaVersion(6)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }
}
