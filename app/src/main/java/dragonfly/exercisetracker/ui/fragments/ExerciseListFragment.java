package dragonfly.exercisetracker.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.models.DExercise;
import dragonfly.exercisetracker.data.intents.ContractKeyIntent;
import dragonfly.exercisetracker.ui.activities.ExerciseActivity;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.BaseAdapter;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.ExerciseAdapter;
import io.realm.Realm;
import io.realm.RealmResults;


public class ExerciseListFragment extends Fragment implements BaseAdapter.OnItemSelectedListener {
    private RecyclerView exerciseRv;

    public static ExerciseListFragment newInstance() {
        ExerciseListFragment exerciseListFragment = new ExerciseListFragment();
        Bundle args = new Bundle();
        exerciseListFragment.setArguments(args);
        return exerciseListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {

        } else if (this.getArguments() != null) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        this.exerciseRv = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        this.exerciseRv.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        this.exerciseRv.setAdapter(new ExerciseAdapter(new DExercise[0]));
        ((ExerciseAdapter)this.exerciseRv.getAdapter()).addOnItemSelectedListener(this);

        rootView.findViewById(R.id.new_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showExerciseActivityIntent = new Intent(ExerciseListFragment.this.getActivity(), ExerciseActivity.class);
                ExerciseListFragment.this.getActivity().startActivity(showExerciseActivityIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)this.getActivity()).getSupportActionBar().setTitle(R.string.exercises);
    }

    @Override
    public void onResume() {
        super.onResume();
        RealmResults<DExercise> realmResults = Realm.getDefaultInstance().where(DExercise.class).findAll();
        ((ExerciseAdapter)this.exerciseRv.getAdapter()).setItems(realmResults.toArray(new DExercise[realmResults.size()]));
    }

    @Override
    public void onItemSelected(BaseAdapter.BaseViewHolder viewHolder, Object item) {
        Intent showExerciseActivityIntent = new Intent(ExerciseListFragment.this.getActivity(), ExerciseActivity.class);
        showExerciseActivityIntent.putExtra(ContractKeyIntent.ExerciseActivity.SELECTED_EXERCISE, ((ExerciseAdapter.ExerciseViewBinder)item).exercise.getPrimaryKey());
        ExerciseListFragment.this.getActivity().startActivity(showExerciseActivityIntent);
    }
}
