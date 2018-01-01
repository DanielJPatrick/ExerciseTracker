package dragonfly.exercisetracker.ui.views.recyclerviews.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dragonfly.exercisetracker.R;

public class DrawerAdapter extends BaseAdapter {
    private static final int ITEM_VH_TYPE = 0;

    public DrawerAdapter(Item[] rawItems) {
        super(rawItems);
    }

    @Override
    public int getItemViewType(int position) {
        return DrawerAdapter.ITEM_VH_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_list_drawer, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ((ItemViewHolder)holder).bind();
    }

    public class ItemViewHolder extends BaseAdapter.BaseViewHolder {
        public TextView nameTv;

        private ItemViewHolder(View itemView) {
            super(itemView);
            itemView.setBackgroundResource(R.drawable.selector_drawer_item_background);
            this.nameTv = (TextView)itemView.findViewById(R.id.name_tv);
        }

        private void bind() {
            this.nameTv.setText(((Item)DrawerAdapter.this.getItem(this.boundPosition)).name);
        }
    }

    public static class Item implements BaseAdapter.Item{
        private String name;

        public Item(String name) {
            this.name = name;
        }

        @Override
        public Object getItemKey() {
            return this;
        }

        public String getName() {
            return name;
        }
    }
}
