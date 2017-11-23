package dragonfly.exercisetracker.injection.modules;

import dagger.Module;
import dagger.Provides;
import dragonfly.exercisetracker.ExerciseApplication;

@Module
public class AppModule {

    public static final String TAG = AppModule.class.getSimpleName();
    private final ExerciseApplication application;

    public AppModule(ExerciseApplication application) {
        this.application = application;
    }

    @Provides
    ExerciseApplication provideApp() {
        return application;
    }
}
