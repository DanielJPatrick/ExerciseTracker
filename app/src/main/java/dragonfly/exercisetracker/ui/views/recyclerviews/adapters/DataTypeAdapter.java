package dragonfly.exercisetracker.ui.views.recyclerviews.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.models.DDataType;

public class DataTypeAdapter extends BaseAdapter {
    private static final int DATA_TYPE_VH_TYPE = 1;

    public DataTypeAdapter(Object[] rawItems) {
        super(rawItems);
    }

    @Override
    protected Object[] generateListItems(Object[] rawItems) {
        ArrayList<Object> listItems = new ArrayList<Object>();
        if(rawItems == null) {
            return null;
        } else {
            for(Object rawItemLooper : rawItems) {
                listItems.add(new DataTypeViewBinder((DDataType)rawItemLooper));
            }
        }
        return listItems.toArray();
    }

    @Override
    public int getItemViewType(int position) {
        return DataTypeAdapter.DATA_TYPE_VH_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case(DataTypeAdapter.DATA_TYPE_VH_TYPE):
                return new DataTypeAdapter.DataTypeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_type_list_data_type, parent, false));
        }
        return null;
    }

    private class DataTypeViewHolder extends BaseAdapter.BaseViewHolder {
        private TextView nameTv;

        public DataTypeViewHolder(View itemView) {
            super(itemView);
            this.nameTv = (TextView)itemView.findViewById(R.id.name_tv);
        }

        @Override
        public DataTypeViewBinder getViewBinder() {
            return (DataTypeViewBinder)super.getViewBinder();
        }
    }

    public class DataTypeViewBinder extends BaseViewBinder implements Item {
        private DDataType datatype;

        public DataTypeViewBinder(DDataType dataType) {
            this.datatype = dataType;
        }

        @Override
        public void bind(BaseViewHolder viewHolder) {
            ((DataTypeViewHolder)viewHolder).nameTv.setText(DDataType.getDataType(this.datatype.getValue()).name());
        }

        public DDataType getDatatype() {
            return this.datatype;
        }

        @Override
        public Object getItemKey() {
            return this.datatype;
        }
    }
}
