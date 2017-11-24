package dragonfly.exercisetracker.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import dragonfly.exercisetracker.ExerciseApplication;
import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.RealmWrapper;
import dragonfly.exercisetracker.data.database.models.DDataType;
import dragonfly.exercisetracker.data.database.models.DIModel;
import dragonfly.exercisetracker.data.intents.ContractKeyIntent;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.BaseAdapter;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.DataTypeAdapter;
import io.realm.Realm;

public class DataTypeListActivity extends AppCompatActivity {
    @Inject
    Bus bus;
    private boolean busRegistered = false;
    private Toolbar toolbar;
    private RecyclerView dataTypeRv;
    private DDataType selectedDataType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_data_type_list);
        ((ExerciseApplication) this.getApplication()).dependencyGraph.inject(this);
        this.bus.register(this);
        this.busRegistered = true;

        this.toolbar = (Toolbar)this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle(R.string.data_types);
        this.getSupportActionBar().setSubtitle(null);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        this.dataTypeRv = (RecyclerView)this.findViewById(R.id.recycler_view);
        this.dataTypeRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DDataType[] dataTypes = new DDataType[DDataType.DataType.values().length];
        for(int index = 0; index < DDataType.DataType.values().length; index++) {
            //dataTypes[index] = Realm.getDefaultInstance().createObject(DDataType.class);
            //dataTypes[index].setValue(DDataType.DataType.values()[index].ordinal());
            dataTypes[index] = new DDataType(DDataType.DataType.values()[index].ordinal());
            try {
                dataTypes[index].setPrimaryKey(index + 1L);
            } catch (DIModel.PrimaryKeyException e) {}
            RealmWrapper.saveObject(Realm.getDefaultInstance(), dataTypes[index]);
        }
        DataTypeAdapter dataTypeAdapter = new DataTypeAdapter(dataTypes);
        dataTypeAdapter.addOnItemSelectedListener(new BaseAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BaseAdapter.BaseViewHolder viewHolder, Object item) {
                DataTypeListActivity.this.selectedDataType = ((DataTypeAdapter.DataTypeViewBinder)item).getDatatype();
            }
        });
        this.dataTypeRv.setAdapter(dataTypeAdapter);

        if(savedInstanceState != null) {

        } else if(this.getIntent() != null) {
            if(this.getIntent().hasExtra(ContractKeyIntent.VariableActivity.SELECTED_DATA_TYPE)) {
                ((DataTypeAdapter)dataTypeRv.getAdapter()).setSelected(Realm.getDefaultInstance().where(DDataType.class).equalTo(DIModel.PRIMARY_KEY, (Long)this.getIntent().getSerializableExtra(ContractKeyIntent.VariableActivity.SELECTED_DATA_TYPE)).findFirst());
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    protected void onResume() {
        super.onResume();
        if(!this.busRegistered) {
            this.bus.register(this);
            this.busRegistered = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.busRegistered) {
            this.bus.unregister(this);
            this.busRegistered = false;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        this.returnResult();
    }

    public void returnResult() {
        Intent data = new Intent();
        if(((DataTypeAdapter)this.dataTypeRv.getAdapter()).hasSelectedItems()) {
            data.putExtra(ContractKeyIntent.VariableActivity.SELECTED_DATA_TYPE, ((DataTypeAdapter.DataTypeViewBinder)((DataTypeAdapter)this.dataTypeRv.getAdapter()).getSelectedItems()[0]).getDatatype());
        }
        this.setResult(RESULT_OK, data);
        this.finish();
    }
}
