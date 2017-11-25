package dragonfly.exercisetracker.ui.fragments;

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


public class TimetableFragment extends Fragment {
    private RecyclerView timetableRv;

    public static TimetableFragment newInstance() {
        TimetableFragment timetableFragment = new TimetableFragment();
        Bundle args = new Bundle();
        timetableFragment.setArguments(args);
        return timetableFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);
        this.timetableRv = (RecyclerView) rootView.findViewById(R.id.timetable_recycler_view);
        this.timetableRv.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        //this.timetableRv.setAdapter(new ScheduleAdapter());
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) this.getActivity()).getSupportActionBar().setTitle(R.string.timetable);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
