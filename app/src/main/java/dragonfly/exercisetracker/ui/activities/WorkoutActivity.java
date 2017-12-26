package dragonfly.exercisetracker.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.RealmWrapper;
import dragonfly.exercisetracker.data.database.models.DExercise;
import dragonfly.exercisetracker.data.database.models.DIModel;
import dragonfly.exercisetracker.data.database.models.DPrescription;
import dragonfly.exercisetracker.data.database.models.DWorkout;
import dragonfly.exercisetracker.data.intents.ContractKeyIntent;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.BaseAdapter;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.WorkoutPrescriptionAdaptor;
import io.realm.Realm;
import io.realm.RealmList;

public class WorkoutActivity extends AppCompatActivity implements BaseAdapter.OnItemClickedListener {
    private Toolbar toolbar;
    private EditText nameEt;
    private DWorkout workout;
    private RecyclerView prescriptionsRv;

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

        this.prescriptionsRv = (RecyclerView)this.findViewById(R.id.recycler_view);
        this.prescriptionsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.prescriptionsRv.setAdapter(new WorkoutPrescriptionAdaptor(new DPrescription[0]));
        ((WorkoutPrescriptionAdaptor)this.prescriptionsRv.getAdapter()).selectable = false;
        ((WorkoutPrescriptionAdaptor)this.prescriptionsRv.getAdapter()).addOnItemClickedListener(this);

        this.findViewById(R.id.new_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showExerciseListActivityIntent = new Intent(WorkoutActivity.this, ExerciseListActivity.class);
                WorkoutActivity.this.startActivityForResult(showExerciseListActivityIntent, ContractKeyIntent.WorkoutActivity.EXERCISE_LIST_ACTIVITY_REQUEST_CODE);
            }
        });

        if(this.getIntent() != null && this.getIntent().hasExtra(ContractKeyIntent.WorkoutActivity.SELECTED_WORKOUT)) {
            this.workout = Realm.getDefaultInstance().where(DWorkout.class).equalTo(DIModel.PRIMARY_KEY, (Long)this.getIntent().getSerializableExtra(ContractKeyIntent.WorkoutActivity.SELECTED_WORKOUT)).findFirst();
            this.nameEt.setText(this.workout.getName());
            ((WorkoutPrescriptionAdaptor)this.prescriptionsRv.getAdapter()).setWorkout(this.workout);
            ((WorkoutPrescriptionAdaptor)this.prescriptionsRv.getAdapter()).setItems(this.workout.getPrescriptions().toArray());
        }
    }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ContractKeyIntent.WorkoutActivity.EXERCISE_LIST_ACTIVITY_REQUEST_CODE) {
            Long exercisePrimaryKey = (Long)data.getSerializableExtra(ContractKeyIntent.ExerciseListActivity.SELECTED_EXERCISE);
            if(exercisePrimaryKey != null) {
                DExercise exercise = Realm.getDefaultInstance().where(DExercise.class).equalTo(DIModel.PRIMARY_KEY, exercisePrimaryKey).findFirst();
                DPrescription newPrescription = new DPrescription(exercise);
                RealmWrapper.saveObject(Realm.getDefaultInstance(), newPrescription);
                if (this.workout == null) {
                    this.workout = new DWorkout(this.nameEt.getText().toString());
                    this.workout.setPrescriptions(new RealmList<DPrescription>(newPrescription));
                    RealmWrapper.saveObject(Realm.getDefaultInstance(), this.workout);
                } else {
                    Realm.getDefaultInstance().beginTransaction();
                    if (this.workout.getPrescriptions() == null) {
                        this.workout.setPrescriptions(new RealmList<DPrescription>(newPrescription));
                    } else {
                        this.workout.getPrescriptions().add(newPrescription);
                    }
                    Realm.getDefaultInstance().commitTransaction();
                }
            }
            ((WorkoutPrescriptionAdaptor)this.prescriptionsRv.getAdapter()).setItems(this.workout.getPrescriptions().toArray());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return true;
    }

    @Override
    public void onItemClicked(BaseAdapter adapter, BaseAdapter.BaseViewHolder viewHolder, Object item) {
        if(adapter == this.prescriptionsRv.getAdapter()) {
            Intent showPrescriptionActivityIntent = new Intent(this, PrescriptionActivity.class);
            showPrescriptionActivityIntent.putExtra(ContractKeyIntent.PrescriptionActivity.SELECTED_PRESCRIPTION, ((WorkoutPrescriptionAdaptor.ExerciseViewBinder)item).prescription.getPrimaryKey());
            this.startActivity(showPrescriptionActivityIntent);
        }
    }
}
