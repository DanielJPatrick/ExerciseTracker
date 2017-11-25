package dragonfly.exercisetracker.ui.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.RealmWrapper;
import dragonfly.exercisetracker.data.database.models.DExercise;
import dragonfly.exercisetracker.data.database.models.DIModel;
import dragonfly.exercisetracker.data.intents.ContractKeyIntent;
import io.realm.Realm;

public class ExerciseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText nameEt;
    private DExercise exercise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_exercise);

        this.toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle(R.string.exercise);
        this.getSupportActionBar().setSubtitle(null);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        this.nameEt = (EditText) this.findViewById(R.id.name_et);

        if (this.getIntent() != null && this.getIntent().hasExtra(ContractKeyIntent.ExerciseActivity.SELECTED_EXERCISE)) {
            this.exercise = Realm.getDefaultInstance().where(DExercise.class).equalTo(DIModel.PRIMARY_KEY, (Long) this.getIntent().getSerializableExtra(ContractKeyIntent.ExerciseActivity.SELECTED_EXERCISE)).findFirst();
            this.nameEt.setText(this.exercise.getName());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.nameEt.getText().length() > 0) {
            if(this.exercise == null) {
                this.exercise = new DExercise(this.nameEt.getText().toString());
                RealmWrapper.saveObject(Realm.getDefaultInstance(), this.exercise);
            } else {
                Realm.getDefaultInstance().beginTransaction();
                this.exercise = Realm.getDefaultInstance().where(DExercise.class).equalTo(DIModel.PRIMARY_KEY, this.exercise.getPrimaryKey()).findFirst();
                this.exercise.setName(this.nameEt.getText().toString());
                Realm.getDefaultInstance().commitTransaction();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return true;
    }
}
