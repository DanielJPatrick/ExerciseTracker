package dragonfly.exercisetracker.ui.views.recyclerviews.adapters;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dragonfly.exercisetracker.R;
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
        private View itemView;
        private TextView nameTv;

        public VariableViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.nameTv = (TextView)itemView.findViewById(R.id.name_tv);
        }

        @Override
        public VariableViewBinder getViewBinder() {
            return (VariableViewBinder)super.getViewBinder();
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
