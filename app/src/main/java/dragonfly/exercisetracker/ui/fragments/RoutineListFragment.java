package dragonfly.exercisetracker.ui.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import dragonfly.exercisetracker.ExerciseApplication;
import dragonfly.exercisetracker.R;


public class RoutineListFragment extends Fragment {

    @Inject Bus bus;
    private boolean busRegistered = false;
    private RecyclerView routineRv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bus.register(this);
        this.busRegistered = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        this.routineRv = (RecyclerView)rootView.findViewById(R.id.timetable_recycler_view);
        this.routineRv.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        //this.routineRv.setAdapter(new ScheduleAdapter());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ExerciseApplication)this.getActivity().getApplication()).dependencyGraph.inject(this);
        ((AppCompatActivity)this.getActivity()).getSupportActionBar().setTitle(R.string.timetable);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!this.busRegistered) {
            this.bus.register(this);
            this.busRegistered = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(this.busRegistered) {
            this.bus.unregister(this);
            this.busRegistered = false;
        }
    }
}