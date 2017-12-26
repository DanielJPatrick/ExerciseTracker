package dragonfly.exercisetracker.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import java.text.AttributedCharacterIterator;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.RealmWrapper;
import dragonfly.exercisetracker.data.database.models.DAttribute;
import dragonfly.exercisetracker.data.database.models.DIModel;
import dragonfly.exercisetracker.data.database.models.DVariable;
import dragonfly.exercisetracker.data.intents.ContractKeyIntent;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.PrescriptionVariableSelectorAdapter;
import io.realm.Realm;
import io.realm.RealmResults;

public class AttributeActivity extends AppCompatActivity {
    private DAttribute attribute;
    private Toolbar toolbar;
    private EditText valueEt;
    private RecyclerView variablesRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_attribute);

        this.toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle(R.string.attribute);
        this.getSupportActionBar().setSubtitle(null);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        this.valueEt = (EditText)this.findViewById(R.id.value_et);

        this.variablesRv = (RecyclerView) this.findViewById(R.id.recycler_view);
        this.variablesRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RealmResults<DVariable> realmResults = Realm.getDefaultInstance().where(DVariable.class).findAll();
        this.variablesRv.setAdapter(new PrescriptionVariableSelectorAdapter(realmResults.toArray(new DVariable[realmResults.size()])));
        ((PrescriptionVariableSelectorAdapter)this.variablesRv.getAdapter()).nullSelectionAllowed = false;
        ((PrescriptionVariableSelectorAdapter)this.variablesRv.getAdapter()).multiSelectionAllowed = false;

        if(this.getIntent() != null && this.getIntent().hasExtra(ContractKeyIntent.AttributeActivity.SELECTED_ATTRIBUTE)) {
            Long selectedAttributePrimaryKey = (Long)this.getIntent().getSerializableExtra(ContractKeyIntent.AttributeActivity.SELECTED_ATTRIBUTE);
            if(selectedAttributePrimaryKey != null) {
                this.attribute = Realm.getDefaultInstance().where(DAttribute.class).equalTo(DIModel.PRIMARY_KEY, selectedAttributePrimaryKey).findFirst();
                this.valueEt.setText(this.attribute.getValue());
                ((PrescriptionVariableSelectorAdapter)this.variablesRv.getAdapter()).setSelected(this.attribute.getVariable());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();

        if(this.attribute == null) {
            if(((PrescriptionVariableSelectorAdapter)this.variablesRv.getAdapter()).hasSelectedItems()) {
                this.attribute = new DAttribute(((PrescriptionVariableSelectorAdapter.VariableViewBinder)((PrescriptionVariableSelectorAdapter)this.variablesRv.getAdapter()).getSelectedItems()[0]).variable, this.valueEt.getText().toString());
                RealmWrapper.saveObject(Realm.getDefaultInstance(), this.attribute);
                data.putExtra(ContractKeyIntent.PrescriptionActivity.NEW_TARGET_ATTRIBUTE, this.attribute.getPrimaryKey());
            }
        } else {
            Realm.getDefaultInstance().beginTransaction();
            this.attribute.setVariable(((PrescriptionVariableSelectorAdapter.VariableViewBinder)((PrescriptionVariableSelectorAdapter)this.variablesRv.getAdapter()).getSelectedItems()[0]).variable);
            this.attribute.setValue(this.valueEt.getText().toString());
            Realm.getDefaultInstance().commitTransaction();
        }

        this.setResult(RESULT_OK, data);
        this.finish();
    }
}
