package dragonfly.exercisetracker.ui.fragments;

import android.content.Context;
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

import com.squareup.otto.Bus;

import javax.inject.Inject;

import dragonfly.exercisetracker.ExerciseApplication;
import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.models.DExercise;
import dragonfly.exercisetracker.data.database.models.DVariable;
import dragonfly.exercisetracker.data.intents.ContractKeyIntent;
import dragonfly.exercisetracker.ui.activities.ExerciseActivity;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.BaseAdapter;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.ExerciseAdapter;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.VariableAdapter;
import io.realm.Realm;
import io.realm.RealmResults;


public class ExerciseListFragment extends Fragment implements BaseAdapter.OnItemSelectedListener {

    @Inject Bus bus;
    private boolean busRegistered = false;
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
                Intent showVariableActivityIntent = new Intent(ExerciseListFragment.this.getActivity(), ExerciseActivity.class);
                ExerciseListFragment.this.getActivity().startActivity(showVariableActivityIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((ExerciseApplication) context.getApplicationContext()).dependencyGraph.inject(this);
        this.bus.register(this);
        this.busRegistered = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)this.getActivity()).getSupportActionBar().setTitle(R.string.exercises);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!this.busRegistered) {
            this.bus.register(this);
            this.busRegistered = true;
        }
        RealmResults<DExercise> realmResults = Realm.getDefaultInstance().where(DExercise.class).findAll();
        ((ExerciseAdapter)this.exerciseRv.getAdapter()).setItems(realmResults.toArray(new DExercise[realmResults.size()]));
    }

    @Override
    public void onPause() {
        super.onPause();
        if(this.busRegistered) {
            this.bus.unregister(this);
            this.busRegistered = false;
        }
    }

    @Override
    public void onItemSelected(BaseAdapter.BaseViewHolder viewHolder, Object item) {
        Intent showVariableActivityIntent = new Intent(ExerciseListFragment.this.getActivity(), ExerciseActivity.class);
        showVariableActivityIntent.putExtra(ContractKeyIntent.ExerciseActivity.SELECTED_EXERCISE, ((ExerciseAdapter.ExerciseViewBinder)item).exercise.getPrimaryKey());
        ExerciseListFragment.this.getActivity().startActivity(showVariableActivityIntent);
    }
}
