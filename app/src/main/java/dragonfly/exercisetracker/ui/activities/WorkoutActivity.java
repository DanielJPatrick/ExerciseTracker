package dragonfly.exercisetracker.ui.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.RealmWrapper;
import dragonfly.exercisetracker.data.database.models.DExercise;
import dragonfly.exercisetracker.data.database.models.DIModel;
import dragonfly.exercisetracker.data.database.models.DWorkout;
import dragonfly.exercisetracker.data.intents.ContractKeyIntent;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.BaseAdapter;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.ExerciseAdapter;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.WorkoutExerciseAdaptor;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.WorkoutExerciseSelectorAdapter;
import io.realm.Realm;
import io.realm.RealmResults;

public class WorkoutActivity extends AppCompatActivity implements BaseAdapter.OnItemClickedListener {
    private Toolbar toolbar;
    private EditText nameEt;
    private DWorkout workout;
    private RecyclerView workoutRv;
    private RecyclerView workoutExercisesRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_workout);

        this.toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle(R.string.workout);
        this.getSupportActionBar().setSubtitle(null);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        this.nameEt = (EditText) this.findViewById(R.id.name_et);

        this.workoutRv = (RecyclerView)this.findViewById(R.id.recycler_view);
        this.workoutRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.workoutRv.setAdapter(new WorkoutExerciseSelectorAdapter(new DExercise[0]));
        ((WorkoutExerciseSelectorAdapter)this.workoutRv.getAdapter()).selectable = false;
        ((WorkoutExerciseSelectorAdapter)this.workoutRv.getAdapter()).addOnItemClickedListener(this);

        this.workoutExercisesRv = (RecyclerView)this.findViewById(R.id.sub_recycler_view);
        this.workoutExercisesRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.workoutExercisesRv.setAdapter(new WorkoutExerciseAdaptor(new DExercise[0]));

        if (this.getIntent() != null && this.getIntent().hasExtra(ContractKeyIntent.ExerciseActivity.SELECTED_EXERCISE)) {
            this.workout = Realm.getDefaultInstance().where(DWorkout.class).equalTo(DIModel.PRIMARY_KEY, (Long) this.getIntent().getSerializableExtra(ContractKeyIntent.ExerciseActivity.SELECTED_EXERCISE)).findFirst();
            this.nameEt.setText(this.workout.getName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        RealmResults<DExercise> realmResults = Realm.getDefaultInstance().where(DExercise.class).findAll();
        ((WorkoutExerciseSelectorAdapter)this.workoutRv.getAdapter()).setItems(realmResults.toArray(new DExercise[realmResults.size()]));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.nameEt.getText().length() > 0) {
            if(this.workout == null) {
                this.workout = new DWorkout(this.nameEt.getText().toString());
                RealmWrapper.saveObject(Realm.getDefaultInstance(), this.workout);
            } else {
                Realm.getDefaultInstance().beginTransaction();
                this.workout = Realm.getDefaultInstance().where(DWorkout.class).equalTo(DIModel.PRIMARY_KEY, this.workout.getPrimaryKey()).findFirst();
                this.workout.setName(this.nameEt.getText().toString());
                Realm.getDefaultInstance().commitTransaction();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return true;
    }

    @Override
    public void onItemClicked(BaseAdapter.BaseViewHolder viewHolder, Object item) {
        ((WorkoutExerciseAdaptor)this.workoutExercisesRv.getAdapter()).addItems(new Object[]{((WorkoutExerciseSelectorAdapter.ExerciseViewBinder)item).exercise});
    }
}
