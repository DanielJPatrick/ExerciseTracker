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
import dragonfly.exercisetracker.data.database.models.DWorkout;
import dragonfly.exercisetracker.data.intents.ContractKeyIntent;
import dragonfly.exercisetracker.ui.activities.WorkoutActivity;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.BaseAdapter;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.WorkoutAdapter;
import io.realm.Realm;
import io.realm.RealmResults;


public class WorkoutListFragment extends Fragment implements BaseAdapter.OnItemSelectedListener {
    private RecyclerView workoutRv;

    public static WorkoutListFragment newInstance() {
        WorkoutListFragment workoutListFragment = new WorkoutListFragment();
        Bundle args = new Bundle();
        workoutListFragment.setArguments(args);
        return workoutListFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_workout_list, container, false);
        this.workoutRv = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        this.workoutRv.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        this.workoutRv.setAdapter(new WorkoutAdapter(new DWorkout[0]));
        ((WorkoutAdapter)this.workoutRv.getAdapter()).addOnItemSelectedListener(this);

        rootView.findViewById(R.id.new_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showWorkoutActivityIntent = new Intent(WorkoutListFragment.this.getActivity(), WorkoutActivity.class);
                WorkoutListFragment.this.getActivity().startActivity(showWorkoutActivityIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)this.getActivity()).getSupportActionBar().setTitle(R.string.workouts);
    }

    @Override
    public void onResume() {
        super.onResume();
        RealmResults<DWorkout> realmResults = Realm.getDefaultInstance().where(DWorkout.class).findAll();
        ((WorkoutAdapter)this.workoutRv.getAdapter()).setItems(realmResults.toArray(new DWorkout[realmResults.size()]));
    }

    @Override
    public void onItemSelected(BaseAdapter adapter, BaseAdapter.BaseViewHolder viewHolder, Object item) {
        Intent showWorkoutActivityIntent = new Intent(WorkoutListFragment.this.getActivity(), WorkoutActivity.class);
        showWorkoutActivityIntent.putExtra(ContractKeyIntent.WorkoutActivity.SELECTED_WORKOUT, ((WorkoutAdapter.WorkoutViewBinder)item).workout.getPrimaryKey());
        WorkoutListFragment.this.getActivity().startActivity(showWorkoutActivityIntent);
    }
}
