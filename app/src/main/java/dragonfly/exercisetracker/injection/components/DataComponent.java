package dragonfly.exercisetracker.injection.components;

import com.google.gson.Gson;

import dagger.Component;
import dragonfly.exercisetracker.injection.modules.DataModule;

@Component(
        modules = {DataModule.class}
)
public interface DataComponent {
    Gson getGsonConverter();
}
