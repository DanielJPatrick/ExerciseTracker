package dragonfly.exercisetracker.injection.components;

import com.squareup.otto.Bus;
import dagger.Component;
import dragonfly.exercisetracker.injection.modules.BusModule;

@Component(
        modules = {BusModule.class}
)
public interface BusComponent {
    Bus getBus();
}
