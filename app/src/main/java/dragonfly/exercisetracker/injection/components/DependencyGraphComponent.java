package dragonfly.exercisetracker.injection.components;

import javax.inject.Singleton;

import dagger.Component;
import dragonfly.exercisetracker.ExerciseApplication;
import dragonfly.exercisetracker.ui.activities.DataTypeListActivity;
import dragonfly.exercisetracker.ui.activities.MainActivity;
import dragonfly.exercisetracker.ui.activities.VariableActivity;
import dragonfly.exercisetracker.ui.fragments.ExerciseListFragment;
import dragonfly.exercisetracker.ui.fragments.RoutineListFragment;
import dragonfly.exercisetracker.ui.fragments.TimetableFragment;
import dragonfly.exercisetracker.ui.fragments.VariableListFragment;
import dragonfly.exercisetracker.ui.fragments.WorkoutListFragment;

@Singleton
@Component(
        modules = {},
        dependencies = {AppComponent.class, BusComponent.class, DataComponent.class}
)
public interface DependencyGraphComponent {
    void inject(ExerciseApplication application);
    void inject(MainActivity mainActivity);
    void inject(VariableActivity variableActivity);
    void inject(DataTypeListActivity dataTypeListActivity);
    void inject(TimetableFragment timetableFragment);
    void inject(ExerciseListFragment exerciseListFragment);
    void inject(RoutineListFragment routineListFragment);
    void inject(WorkoutListFragment workoutListFragment);
    void inject(VariableListFragment variableListFragment);
}
