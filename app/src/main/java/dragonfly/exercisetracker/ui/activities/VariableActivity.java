package dragonfly.exercisetracker.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.RealmWrapper;
import dragonfly.exercisetracker.data.database.models.DDataType;
import dragonfly.exercisetracker.data.database.models.DIModel;
import dragonfly.exercisetracker.data.database.models.DVariable;
import dragonfly.exercisetracker.data.intents.ContractKeyIntent;
import io.realm.Realm;

public class VariableActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout dataTypeLl;
    private EditText nameEt;
    private TextView dataTypeTv;
    private DDataType dataType;
    private DVariable variable;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_variable);

        this.toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle(R.string.variable);
        this.getSupportActionBar().setSubtitle(null);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        this.dataTypeLl = (LinearLayout)this.findViewById(R.id.data_type_ll);
        this.dataTypeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showDataTypeListActivityIntent = new Intent(VariableActivity.this, DataTypeListActivity.class);
                if(dataType != null) {
                    showDataTypeListActivityIntent.putExtra(ContractKeyIntent.VariableActivity.SELECTED_DATA_TYPE, VariableActivity.this.dataType.getPrimaryKey());
                }
                VariableActivity.this.startActivityForResult(showDataTypeListActivityIntent, ContractKeyIntent.VariableActivity.DATA_TYPE_LIST_ACTIVITY_REQUEST_CODE);
            }
        });

        this.nameEt = (EditText)this.findViewById(R.id.name_et);
        this.dataTypeTv = (TextView)this.findViewById(R.id.data_type_tv);

        if(this.getIntent() != null && this.getIntent().hasExtra(ContractKeyIntent.VariableActivity.SELECTED_VARIABLE)) {
            this.variable = Realm.getDefaultInstance().where(DVariable.class).equalTo(DIModel.PRIMARY_KEY, (Long)this.getIntent().getSerializableExtra(ContractKeyIntent.VariableActivity.SELECTED_VARIABLE)).findFirst();
            this.dataType = this.variable.getDataType();
            this.nameEt.setText(this.variable.getName());
            this.dataTypeTv.setText(DDataType.getDataType(this.variable.getDataType().getValue()).name());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.nameEt.getText().length() > 0 && this.dataTypeTv.length() > 0) {
            if(this.variable == null) {
                this.variable = new DVariable(this.nameEt.getText().toString(), this.dataType);
                RealmWrapper.saveObject(Realm.getDefaultInstance(), this.variable);
            } else {
                Realm.getDefaultInstance().beginTransaction();
                this.variable = Realm.getDefaultInstance().where(DVariable.class).equalTo(DIModel.PRIMARY_KEY, this.variable.getPrimaryKey()).findFirst();
                this.variable.setName(this.nameEt.getText().toString());
                this.variable.setDataType(Realm.getDefaultInstance().where(DDataType.class).equalTo(DIModel.PRIMARY_KEY, this.dataType.getPrimaryKey()).findFirst());
                Realm.getDefaultInstance().commitTransaction();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if(requestCode == ContractKeyIntent.VariableActivity.DATA_TYPE_LIST_ACTIVITY_REQUEST_CODE) {
            if(data != null && data.hasExtra(ContractKeyIntent.VariableActivity.SELECTED_DATA_TYPE)) {
                this.dataType = (DDataType)data.getSerializableExtra(ContractKeyIntent.VariableActivity.SELECTED_DATA_TYPE);
                //this.variable.setDataType(Realm.getDefaultInstance().where(DDataType.class).equalTo(DIModel.PRIMARY_KEY, this.dataType.getPrimaryKey()).findFirst());
                dataTypeTv.setText(DDataType.getDataType(this.dataType.getValue()).name());
            }
        }
    }
}
