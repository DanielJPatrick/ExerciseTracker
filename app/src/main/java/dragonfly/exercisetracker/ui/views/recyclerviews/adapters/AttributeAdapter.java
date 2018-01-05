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
import dragonfly.exercisetracker.data.database.models.DAttribute;
import dragonfly.exercisetracker.data.database.models.DIModel;
import dragonfly.exercisetracker.ui.fragments.DeleteConfirmDialogFragment;
import io.realm.Realm;
import io.realm.RealmResults;

public class AttributeAdapter extends BaseAdapter {
    private static final int ATTRIBUTE_VH_TYPE = 1;
    private AppCompatActivity activity;

    public AttributeAdapter(AppCompatActivity activity, DAttribute[] rawItems) {
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
                items.add(new AttributeViewBinder((DAttribute)rawItemLooper));
            }
        }
        return items.toArray();
    }

    @Override
    public int getItemViewType(int position) {
        if(super.getItem(position) instanceof AttributeViewBinder) {
            return AttributeAdapter.ATTRIBUTE_VH_TYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case(AttributeAdapter.ATTRIBUTE_VH_TYPE):
                return new AttributeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attribute_list_attribute, parent, false));
        }
        return null;
    }

    private class AttributeViewHolder extends BaseViewHolder implements DeleteConfirmDialogFragment.NoticeDialogListener {
        private View itemView;
        private TextView variableTv;
        private TextView valueTv;
        private ImageView deleteIv;

        public AttributeViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.variableTv = (TextView)itemView.findViewById(R.id.variable_tv);
            this.valueTv = (TextView)itemView.findViewById(R.id.value_tv);
            this.deleteIv = (ImageView)itemView.findViewById(R.id.delete_Iv);
            this.deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteConfirmDialogFragment deleteConfirmDialogFragment = new DeleteConfirmDialogFragment();
                    deleteConfirmDialogFragment.setListener(AttributeViewHolder.this);
                    deleteConfirmDialogFragment.show(AttributeAdapter.this.activity.getFragmentManager(), DeleteConfirmDialogFragment.class.getName());
                }
            });
        }

        @Override
        public AttributeViewBinder getViewBinder() {
            return (AttributeViewBinder)super.getViewBinder();
        }

        @Override
        public void onDialogPositiveClick(DialogFragment dialog) {
            AttributeAdapter.this.removeItems(new DAttribute[] {AttributeViewHolder.this.getViewBinder().attribute});
            Realm.getDefaultInstance().beginTransaction();
            Realm.getDefaultInstance().where(DAttribute.class).equalTo(DIModel.PRIMARY_KEY, AttributeViewHolder.this.getViewBinder().attribute.getPrimaryKey()).findAll().deleteAllFromRealm();
            Realm.getDefaultInstance().commitTransaction();
        }

        @Override
        public void onDialogNegativeClick(DialogFragment dialog) {

        }
    }

    public class AttributeViewBinder extends BaseViewBinder {
        public DAttribute attribute;

        public AttributeViewBinder(DAttribute attribute) {
            this.attribute = attribute;
        }

        @Override
        public void bind(BaseViewHolder viewHolder) {
            if(this.attribute.getVariable() != null) {
                ((AttributeViewHolder) viewHolder).variableTv.setText(this.attribute.getVariable().getName());
                ((AttributeViewHolder) viewHolder).valueTv.setText(this.attribute.getValue());
            }
        }
    }
}
