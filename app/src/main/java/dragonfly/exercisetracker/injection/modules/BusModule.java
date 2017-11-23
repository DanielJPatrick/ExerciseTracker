package dragonfly.exercisetracker.injection.modules;

import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

@Module
public class BusModule {

    public static final String TAG = BusModule.class.getSimpleName();
    private final Bus bus;

    public BusModule() {
        this.bus = new Bus();
    }

    @Provides
    Bus provideBus() {
        return this.bus;
    }
}
