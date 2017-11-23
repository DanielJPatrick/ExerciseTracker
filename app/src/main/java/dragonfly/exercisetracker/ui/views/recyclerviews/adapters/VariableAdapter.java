package dragonfly.exercisetracker.ui.views.recyclerviews.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.models.DDataType;
import dragonfly.exercisetracker.data.database.models.DVariable;

public class VariableAdapter extends BaseAdapter {
    private static final int VARIABLE_VH_TYPE = 1;

    public VariableAdapter(DVariable[] rawItems) {
        super(rawItems);
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

    private class VariableViewHolder extends BaseViewHolder {
        private TextView nameTv;
        private TextView valueTv;
        private TextView dataTypeTv;

        public VariableViewHolder(View itemView) {
            super(itemView);
            this.nameTv = (TextView)itemView.findViewById(R.id.name_tv);
            this.valueTv = (TextView)itemView.findViewById(R.id.value_tv);
            this.dataTypeTv = (TextView)itemView.findViewById(R.id.data_type_tv);
        }

        @Override
        public VariableViewBinder getViewBinder() {
            return (VariableViewBinder)super.getViewBinder();
        }
    }

    private class VariableViewBinder extends BaseViewBinder {
        private DVariable variable;

        public VariableViewBinder(DVariable variable) {
            this.variable = variable;
        }

        @Override
        public void bind(BaseViewHolder viewHolder) {
            ((VariableViewHolder)viewHolder).nameTv.setText(this.variable.getName());
            //((VariableViewHolder)viewHolder).valueTv.setText(this.variable.getValue());
            ((VariableViewHolder)viewHolder).dataTypeTv.setText(DDataType.getDataType(this.variable.getDataType().getValue()).name());
        }
    }
}
