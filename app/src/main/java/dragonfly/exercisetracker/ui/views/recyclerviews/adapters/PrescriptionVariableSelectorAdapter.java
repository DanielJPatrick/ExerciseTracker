package dragonfly.exercisetracker.ui.views.recyclerviews.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.models.DVariable;

public class PrescriptionVariableSelectorAdapter extends BaseAdapter {
    private static final int VARIABLE_VH_TYPE = 1;

    public PrescriptionVariableSelectorAdapter(DVariable[] rawItems) {
        super(rawItems);
    }

    @Override
    protected Object[] generateListItems(Object[] rawItems) {
        ArrayList<Object> items = new ArrayList<Object>();
        if(rawItems == null) {
            return null;
        } else {
            for(Object rawItemLooper : rawItems) {
                items.add(new PrescriptionVariableSelectorAdapter.VariableViewBinder((DVariable)rawItemLooper));
            }
        }
        return items.toArray();
    }

    @Override
    public int getItemViewType(int position) {
        if(super.getItem(position) instanceof PrescriptionVariableSelectorAdapter.VariableViewBinder) {
            return PrescriptionVariableSelectorAdapter.VARIABLE_VH_TYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case(PrescriptionVariableSelectorAdapter.VARIABLE_VH_TYPE):
                return new PrescriptionVariableSelectorAdapter.VariableViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prescription_variable_list_variable, parent, false));
        }
        return null;
    }

    private class VariableViewHolder extends BaseAdapter.BaseViewHolder {
        private View itemView;
        private TextView nameTv;
        private ImageView deleteIv;

        public VariableViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.nameTv = (TextView)itemView.findViewById(R.id.name_tv);
        }

        @Override
        public VariableAdapter.VariableViewBinder getViewBinder() {
            return (VariableAdapter.VariableViewBinder)super.getViewBinder();
        }
    }

    public class VariableViewBinder extends BaseAdapter.BaseViewBinder implements Item {
        public DVariable variable;

        public VariableViewBinder(DVariable variable) {
            this.variable = variable;
        }

        @Override
        public void bind(BaseAdapter.BaseViewHolder viewHolder) {
            ((PrescriptionVariableSelectorAdapter.VariableViewHolder)viewHolder).nameTv.setText(this.variable.getName());
        }

        @Override
        public Object getItemKey() {
            return this.variable;
        }
    }
}
