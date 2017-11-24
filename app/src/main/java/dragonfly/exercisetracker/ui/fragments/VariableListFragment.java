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
import dragonfly.exercisetracker.data.database.models.DVariable;
import dragonfly.exercisetracker.data.intents.ContractKeyIntent;
import dragonfly.exercisetracker.ui.activities.VariableActivity;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.BaseAdapter;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.VariableAdapter;
import io.realm.Realm;
import io.realm.RealmResults;

public class VariableListFragment extends Fragment implements BaseAdapter.OnItemSelectedListener {

    @Inject Bus bus;
    private boolean busRegistered = false;
    private RecyclerView variableRv;

    public static VariableListFragment newInstance() {
        VariableListFragment variableListFragment = new VariableListFragment();
        Bundle args = new Bundle();
        variableListFragment.setArguments(args);
        return variableListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {

        } else if (this.getArguments() != null) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((ExerciseApplication) context.getApplicationContext()).dependencyGraph.inject(this);
        this.bus.register(this);
        this.busRegistered = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_variable_list, container, false);

        this.variableRv = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        this.variableRv.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        this.variableRv.setAdapter(new VariableAdapter(new DVariable[0]));
        ((VariableAdapter)this.variableRv.getAdapter()).addOnItemSelectedListener(this);

        rootView.findViewById(R.id.new_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showVariableActivityIntent = new Intent(VariableListFragment.this.getActivity(), VariableActivity.class);
                VariableListFragment.this.getActivity().startActivity(showVariableActivityIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) this.getActivity()).getSupportActionBar().setTitle(R.string.variables);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!this.busRegistered) {
            this.bus.register(this);
            this.busRegistered = true;
        }
        final RealmResults<DVariable> realmResults = Realm.getDefaultInstance().where(DVariable.class).findAll();
        ((VariableAdapter)this.variableRv.getAdapter()).setItems(realmResults.toArray(new DVariable[realmResults.size()]));
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this.busRegistered) {
            this.bus.unregister(this);
            this.busRegistered = false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemSelected(BaseAdapter.BaseViewHolder viewHolder, Object item) {
        Intent showVariableActivityIntent = new Intent(VariableListFragment.this.getActivity(), VariableActivity.class);
        showVariableActivityIntent.putExtra(ContractKeyIntent.VariableActivity.SELECTED_VARIABLE, ((VariableAdapter.VariableViewBinder)item).variable.getPrimaryKey());
        VariableListFragment.this.getActivity().startActivity(showVariableActivityIntent);
    }
}
