package dragonfly.exercisetracker.data.intents;


public class ContractKeyIntent {
    public static abstract class VariableActivity {
        public static final int DATA_TYPE_LIST_ACTIVITY_REQUEST_CODE = 0;
        public static final String SELECTED_DATA_TYPE = "SelectedDataType";
        public static final String SELECTED_VARIABLE = "SelectedVariable";
    }

    public static abstract class ExerciseActivity {
        public static final String SELECTED_EXERCISE = "SelectedExercise";
    }

    public static abstract class WorkoutActivity {
        public static final String SELECTED_WORKOUT = "SelectedWorkout";
    }
}