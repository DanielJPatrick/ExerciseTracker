package dragonfly.exercisetracker.ui.views.recyclerviews.adapters;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.models.DIModel;
import dragonfly.exercisetracker.data.database.models.DVariable;
import dragonfly.exercisetracker.ui.fragments.DeleteConfirmDialogFragment;
import io.realm.Realm;
import io.realm.RealmResults;

public class VariableAdapter extends BaseAdapter {
    private static final int VARIABLE_VH_TYPE = 1;
    private AppCompatActivity activity;

    public VariableAdapter(AppCompatActivity activity, DVariable[] rawItems) {
        super(rawItems);
        this.activity = activity;
    }

    @Override
    protected Object[] generateListItems(Object[] rawItems) {
        ArrayList<Object> items = new ArrayList<Object>();
        if(rawItems == null) {
            return null;
        } else {
            for(Object rawItemLooper : rawItems) {
                items.add(new VariableViewBinder((DVariable)rawItemLooper));
            }
        }
        return items.toArray();
    }

    @Override
    public int getItemViewType(int position) {
        if(super.getItem(position) instanceof VariableViewBinder) {
            return VariableAdapter.VARIABLE_VH_TYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case(VariableAdapter.VARIABLE_VH_TYPE):
                return new VariableViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_variable_list_variable, parent, false));
        }
        return null;
    }

    private class VariableViewHolder extends BaseViewHolder implements DeleteConfirmDialogFragment.NoticeDialogListener {
        private View itemView;
        private TextView nameTv;
        private ImageView deleteIv;

        public VariableViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.nameTv = (TextView)itemView.findViewById(R.id.name_tv);
            this.deleteIv = (ImageView)itemView.findViewById(R.id.delete_Iv);
            this.deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteConfirmDialogFragment deleteConfirmDialogFragment = new DeleteConfirmDialogFragment();
                    deleteConfirmDialogFragment.setListener(VariableViewHolder.this);
                    deleteConfirmDialogFragment.show(VariableAdapter.this.activity.getFragmentManager(), DeleteConfirmDialogFragment.class.getName());
                }
            });
        }

        @Override
        public VariableViewBinder getViewBinder() {
            return (VariableViewBinder)super.getViewBinder();
        }

        @Override
        public void onDialogPositiveClick(DialogFragment dialog) {
            Realm.getDefaultInstance().beginTransaction();
            Realm.getDefaultInstance().where(DVariable.class).equalTo(DIModel.PRIMARY_KEY, VariableViewHolder.this.getViewBinder().variable.getPrimaryKey()).findAll().deleteAllFromRealm();
            Realm.getDefaultInstance().commitTransaction();
            RealmResults<DVariable> realmResults = Realm.getDefaultInstance().where(DVariable.class).findAll();
            VariableAdapter.this.setItems(realmResults.toArray(new DVariable[realmResults.size()]));
        }

        @Override
        public void onDialogNegativeClick(DialogFragment dialog) {

        }
    }

    public class VariableViewBinder extends BaseViewBinder {
        public DVariable variable;

        public VariableViewBinder(DVariable variable) {
            this.variable = variable;
        }

        @Override
        public void bind(BaseViewHolder viewHolder) {
            ((VariableViewHolder)viewHolder).nameTv.setText(this.variable.getName());
        }
    }
}
