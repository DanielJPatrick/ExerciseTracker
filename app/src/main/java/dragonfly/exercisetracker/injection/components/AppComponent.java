package dragonfly.exercisetracker.injection.components;

import dagger.Component;
import dragonfly.exercisetracker.ExerciseApplication;
import dragonfly.exercisetracker.injection.modules.AppModule;

@Component(
        modules = {AppModule.class}
)
public interface AppComponent {
    ExerciseApplication getApplication();
}
