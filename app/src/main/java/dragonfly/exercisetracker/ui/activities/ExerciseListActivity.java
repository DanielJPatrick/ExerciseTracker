package dragonfly.exercisetracker.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.models.DExercise;
import dragonfly.exercisetracker.data.intents.ContractKeyIntent;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.BaseAdapter;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.ExerciseSelectorAdapter;
import io.realm.Realm;
import io.realm.RealmResults;

public class ExerciseListActivity extends AppCompatActivity implements BaseAdapter.OnItemClickedListener {
    private Toolbar toolbar;
    private RecyclerView exerciseRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_exercise_list);

        this.toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle(R.string.workout);
        this.getSupportActionBar().setSubtitle(null);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        RealmResults<DExercise> realmResults = Realm.getDefaultInstance().where(DExercise.class).findAll();
        this.exerciseRv = (RecyclerView) this.findViewById(R.id.recycler_view);
        this.exerciseRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.exerciseRv.setAdapter(new ExerciseSelectorAdapter(realmResults.toArray(new DExercise[realmResults.size()])));
        ((ExerciseSelectorAdapter) this.exerciseRv.getAdapter()).selectable = false;
        ((ExerciseSelectorAdapter) this.exerciseRv.getAdapter()).addOnItemClickedListener(this);
    }

    @Override
    public void onItemClicked(BaseAdapter adapter, BaseAdapter.BaseViewHolder viewHolder, Object item) {
        if (adapter == this.exerciseRv.getAdapter()) {
            Intent data = new Intent();
            data.putExtra(ContractKeyIntent.ExerciseListActivity.SELECTED_EXERCISE, ((ExerciseSelectorAdapter.ExerciseViewBinder)item).exercise.getPrimaryKey());
            this.setResult(ExerciseListActivity.RESULT_OK, data);
            this.finish();
        }
    }
}
