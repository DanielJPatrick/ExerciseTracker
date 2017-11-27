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

    public static abstract class ExerciseListActivity {
        public static final String SELECTED_EXERCISE = "SelectedExercise";
    }

    public static abstract class WorkoutActivity {
        public static final int EXERCISE_LIST_ACTIVITY_REQUEST_CODE = 0;
        public static final String SELECTED_WORKOUT = "SelectedWorkout";
    }

    public static abstract class PrescriptionActivity {
        public static final int ATTRIBUTE_ACTIVITY_REQUEST_CODE = 0;
        public static final String NEW_TARGET_ATTRIBUTE = "NewAttribute";
        public static final String SELECTED_PRESCRIPTION = "SelectedPrescription";
    }

    public static abstract class AttributeActivity {
        public static final String SELECTED_ATTRIBUTE = "SelectedAttribute";
    }
}