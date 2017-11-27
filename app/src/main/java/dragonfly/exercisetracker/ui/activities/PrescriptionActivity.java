package dragonfly.exercisetracker.ui.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.models.DAttribute;
import dragonfly.exercisetracker.data.database.models.DIModel;
import dragonfly.exercisetracker.data.database.models.DPrescription;
import dragonfly.exercisetracker.data.intents.ContractKeyIntent;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.AttributeAdapter;
import dragonfly.exercisetracker.ui.views.recyclerviews.adapters.BaseAdapter;
import io.realm.Realm;

public class PrescriptionActivity extends AppCompatActivity implements BaseAdapter.OnItemClickedListener {
    private Toolbar toolbar;
    private RecyclerView targetsRv;
    private DPrescription prescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_prescription);

        this.toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle(null);
        this.getSupportActionBar().setSubtitle(null);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        this.targetsRv = (RecyclerView) this.findViewById(R.id.recycler_view);
        this.targetsRv.setLayoutManager(new LinearLayoutManager(this));
        this.targetsRv.setAdapter(new AttributeAdapter(new DAttribute[]{}));
        ((AttributeAdapter)this.targetsRv.getAdapter()).addOnItemClickedListener(this);

        this.findViewById(R.id.new_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showAttributeActivityIntent = new Intent(PrescriptionActivity.this, AttributeActivity.class);
                PrescriptionActivity.this.startActivityForResult(showAttributeActivityIntent, ContractKeyIntent.PrescriptionActivity.ATTRIBUTE_ACTIVITY_REQUEST_CODE);
            }
        });

        if(this.getIntent() != null && this.getIntent().hasExtra(ContractKeyIntent.PrescriptionActivity.SELECTED_PRESCRIPTION)) {
            this.prescription = Realm.getDefaultInstance().where(DPrescription.class).equalTo(DIModel.PRIMARY_KEY, (Long)this.getIntent().getSerializableExtra(ContractKeyIntent.PrescriptionActivity.SELECTED_PRESCRIPTION)).findFirst();
            this.getSupportActionBar().setTitle(this.prescription.getExercise().getName());
            ((AttributeAdapter)this.targetsRv.getAdapter()).setItems(this.prescription.getTargets().toArray());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((AttributeAdapter)this.targetsRv.getAdapter()).setItems(this.prescription.getTargets().toArray());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ContractKeyIntent.PrescriptionActivity.ATTRIBUTE_ACTIVITY_REQUEST_CODE) {
            if(data.hasExtra(ContractKeyIntent.PrescriptionActivity.NEW_TARGET_ATTRIBUTE)) {
                long attributePrimaryKey = (Long)data.getSerializableExtra(ContractKeyIntent.PrescriptionActivity.NEW_TARGET_ATTRIBUTE);
                DAttribute attribute = Realm.getDefaultInstance().where(DAttribute.class).equalTo(DIModel.PRIMARY_KEY, attributePrimaryKey).findFirst();
                Realm.getDefaultInstance().beginTransaction();
                this.prescription.getTargets().add(attribute);
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
    public void onItemClicked(BaseAdapter adapter, BaseAdapter.BaseViewHolder viewHolder, Object item) {
        if(adapter == this.targetsRv.getAdapter()) {
            Intent showAttributeActivityIntent = new Intent(PrescriptionActivity.this, AttributeActivity.class);
            showAttributeActivityIntent.putExtra(ContractKeyIntent.AttributeActivity.SELECTED_ATTRIBUTE, ((AttributeAdapter.AttributeViewBinder)item).attribute.getPrimaryKey());
            PrescriptionActivity.this.startActivity(showAttributeActivityIntent);
        }
    }
}
