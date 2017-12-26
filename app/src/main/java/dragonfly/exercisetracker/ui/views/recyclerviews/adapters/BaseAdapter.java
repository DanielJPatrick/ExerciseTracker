package dragonfly.exercisetracker.ui.views.recyclerviews.adapters;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class BaseAdapter extends RecyclerView.Adapter {
    private static final int BASE_VH_TYPE = -1;
    private final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());
    private LinkedHashMap<Object, Integer> newSelectedKeys = new LinkedHashMap<Object, Integer>();
    private LinkedHashMap<Object, Integer> newUnselectedKeys = new LinkedHashMap<Object, Integer>();
    private LinkedHashMap<Object, Object> items = new LinkedHashMap<Object, Object>();
    private LinkedHashMap<Object, Integer> keysToPosition = new LinkedHashMap<Object, Integer>();
    private LinkedHashMap<Integer, Object> positionToKeys = new LinkedHashMap<Integer, Object>();
    private LinkedHashMap<Object, Integer> selectedKeys = new LinkedHashMap<Object, Integer>();
    private LinkedHashSet<OnItemSelectedListener> onItemSelectedListeners = new LinkedHashSet<OnItemSelectedListener>();
    private LinkedHashSet<OnItemUnselectedListener> onItemUnselectedListeners = new LinkedHashSet<OnItemUnselectedListener>();
    private LinkedHashSet<OnItemClickedListener> onItemClickedListeners = new LinkedHashSet<OnItemClickedListener>();
    private Object[] rawItems;
    protected RecyclerView boundRecyclerView;
    public boolean clickable = true;
    public boolean selectable = true;
    public boolean nullSelectionAllowed = false;
    public boolean multiSelectionAllowed = false;

    public BaseAdapter() {}

    public BaseAdapter(Object[] rawItems) {
        this.setItems(rawItems);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.boundRecyclerView = recyclerView;
    }

    @Override
    public int getItemViewType(int position) {
        return BaseAdapter.BASE_VH_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder)holder).bind(position);
        if(this.getItem(position) instanceof BaseViewBinder) {
            ((BaseViewBinder)this.getItem(position)).bind((BaseViewHolder)holder);
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void setItems(Object[] rawItems) {
        this.rawItems = rawItems;
        this.initialiseDataSet(this.rawItems);
    }

    public void addItems(Object[] rawItemsToAdd) {
        Object[] newRawItems = new Object[this.rawItems.length + rawItemsToAdd.length];
        System.arraycopy(this.rawItems, 0, newRawItems, 0, this.rawItems.length);
        System.arraycopy(rawItemsToAdd, 0, newRawItems, this.rawItems.length, rawItemsToAdd.length);
        this.setItems(newRawItems);
    }

    public void removeItems(Object[] rawItemsToRemove) {
        HashSet<Integer> positionsToRemove = new HashSet<Integer>();
        for (int itemIndex = 0; itemIndex < this.rawItems.length; itemIndex++)
        for (Object itemToRemoveLooper : rawItemsToRemove) {
            if(this.rawItems[itemIndex].equals(itemToRemoveLooper)) {
                positionsToRemove.add(itemIndex);
            }
        }
        this.removeItems(positionsToRemove);
    }

    public void removeItems(Integer[] positionsToRemove) {
        HashSet<Integer> positionsToRemoveSet = new HashSet<Integer>();
        for(Integer positionToRemoveLooper : positionsToRemove) {
            positionsToRemoveSet.add(positionToRemoveLooper);
        }
        this.removeItems(positionsToRemoveSet);
    }

    public void removeItems(HashSet<Integer> positionsToRemove) {
        int newRawItemsIndex = 0;
        Object[] newRawItems = new Object[this.rawItems.length - positionsToRemove.size()];
        for (int itemIndex = 0; itemIndex < this.rawItems.length; itemIndex++) {
            if(!positionsToRemove.contains(itemIndex)) {
                newRawItems[newRawItemsIndex] = this.rawItems[itemIndex];
                newRawItemsIndex++;
            }
        }
        this.setItems(newRawItems);
    }

    private void initialiseDataSet(Object[] rawItems) {
        this.indexItems(this.generateListItems(rawItems));
        this.clearSelections();
        this.notifyDataSetChanged();
    }

    protected Object[] generateListItems(Object[] rawItems) {
        return this.rawItems;
    }

    private void indexItems(Object[] items) {
        this.items.clear();
        if(items != null) {
            for (int itemIndex = 0; itemIndex < items.length; itemIndex++) {
                this.positionToKeys.put(itemIndex, this.getItemKey(items[itemIndex]));
                this.keysToPosition.put(this.getItemKey(itemIndex), itemIndex);
                this.items.put(this.getItemKey(itemIndex), items[itemIndex]);
            }
        }
    }

    public void clearSelections() {
        this.selectedKeys.clear();
        this.newSelectedKeys.clear();
    }

    public Object getItem(Object itemKey) {
        return this.items.get(itemKey);
    }

    public Object getItem(Integer position) {
        return this.items.get(this.getItemKey(position));
    }

    public Object getItemKey(Object item) {
        if(item instanceof Item) {
            if(((Item)item).getItemKey() != null) {
                return ((Item)item).getItemKey();
            } else {
                return item;
            }
        } else {
            return item;
        }
    }

    public Object getItemKey(Integer position) {
        return this.positionToKeys.get(position);
    }

    public Integer getPositionFromKey(Object itemKey) {
        return this.keysToPosition.get(itemKey);
    }

    public Integer getPositionFromItem(Object item) {
        return this.getPositionFromKey(this.getItemKey(item));
    }

    public boolean hasSelectedItems() {
        return this.selectedKeys.size() > 0;
    }

    public Object[] getSelectedKeys() {
        return this.selectedKeys.keySet().toArray();
    }

    public Object[] getSelectedItems() {
        Object[] selectedItems = new Object[this.selectedKeys.keySet().size()];
        int currentSelectedKeyIndex = 0;
        for(Object currentSelectedKey : this.selectedKeys.keySet()) {
            selectedItems[currentSelectedKeyIndex] = this.getItem(currentSelectedKey);
            currentSelectedKeyIndex++;
        }
        return selectedItems;
    }

    public void setSelected(Object selectedItemKey) {
        for(Object itemKeyLooper : this.items.keySet()) {
            if(selectedItemKey.equals(itemKeyLooper)) {
                this.selectedKeys.put(itemKeyLooper, this.getPositionFromKey(itemKeyLooper));
            }
        }
    }

    public void itemClicked(Object itemClicked) {
        if(itemClicked != null) {
            this.itemKeyClicked(this.getItemKey(itemClicked));
        } else {
            this.itemKeyClicked(null);
        }
    }

    public void itemKeyClicked(Object itemKeyClicked) {

        if (this.clickable && ((BaseViewHolder)this.boundRecyclerView.findViewHolderForAdapterPosition(this.getPositionFromKey(itemKeyClicked))).clickable) {
            for (OnItemClickedListener onItemClickedListenerLooper : onItemClickedListeners) {
                onItemClickedListenerLooper.onItemClicked(BaseAdapter.this, ((BaseViewHolder)this.boundRecyclerView.findViewHolderForAdapterPosition(this.getPositionFromKey(itemKeyClicked))), this.getItem(itemKeyClicked));
            }
            if (this.selectable && ((BaseViewHolder)this.boundRecyclerView.findViewHolderForAdapterPosition(this.getPositionFromKey(itemKeyClicked))).selectable) {
                if (itemKeyClicked != null && this.items.containsKey(itemKeyClicked)) {
                    if (!this.multiSelectionAllowed) {
                        for (Object selectedKeyLooper : this.selectedKeys.keySet()) {
                            if (!(selectedKeyLooper.equals(itemKeyClicked))) {
                                this.selectedKeys.remove(selectedKeyLooper);
                                this.newUnselectedKeys.put(selectedKeyLooper, this.getPositionFromKey(selectedKeyLooper));
                                this.notifyItemChanged(this.getPositionFromKey(selectedKeyLooper));
                            }
                        }
                    }
                    if (this.selectedKeys.containsKey(itemKeyClicked)) {
                        if (this.selectedKeys.size() > 1 || this.nullSelectionAllowed) {
                            this.selectedKeys.remove(itemKeyClicked);
                            this.newUnselectedKeys.put(itemKeyClicked, this.getPositionFromKey(itemKeyClicked));
                            this.notifyItemChanged(this.keysToPosition.get(itemKeyClicked));
                        }
                    } else {
                        if (this.selectedKeys.size() < 1 || this.multiSelectionAllowed) {
                            this.selectedKeys.put(itemKeyClicked, this.getPositionFromKey(itemKeyClicked));
                            this.newSelectedKeys.put(itemKeyClicked, this.getPositionFromKey(itemKeyClicked));
                            this.notifyItemChanged(this.keysToPosition.get(itemKeyClicked));
                        }
                    }
                } else {
                    if (this.nullSelectionAllowed && !this.multiSelectionAllowed) {
                        for (Object selectedKeyLooper : this.selectedKeys.keySet()) {
                            this.selectedKeys.remove(selectedKeyLooper);
                            this.newUnselectedKeys.put(selectedKeyLooper, this.getPositionFromKey(selectedKeyLooper));
                            this.notifyItemChanged(this.getPositionFromKey(selectedKeyLooper));
                        }
                    }
                }
            }
        }
    }

    public void addOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListeners.add(onItemSelectedListener);
    }

    public void removeOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListeners.remove(onItemSelectedListener);
    }

    public void addOnItemUnselectedListener(OnItemUnselectedListener onItemUnselectedListener) {
        this.onItemUnselectedListeners.add(onItemUnselectedListener);
    }

    public void removeOnItemUnselectedListener(OnItemUnselectedListener onItemUnselectedListener) {
        this.onItemUnselectedListeners.remove(onItemUnselectedListener);
    }

    public void addOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListeners.add(onItemClickedListener);
    }

    public boolean isItemSelected(Integer position) {
        return this.selectedKeys.containsKey(this.getItemKey(position));
    }

    public boolean isItemSelected(Object item) {
        return this.selectedKeys.containsKey(this.getItemKey(item));
    }

    private void itemSelected(BaseAdapter adapter, BaseViewHolder viewHolder, Object item) {
        for(OnItemSelectedListener onItemSelectedListenerLooper : this.onItemSelectedListeners) {
            onItemSelectedListenerLooper.onItemSelected(adapter, viewHolder, item);
        }
    }

    private void itemUnselected(BaseAdapter adapter, BaseViewHolder viewHolder, Object item) {
        for(OnItemUnselectedListener onItemUnSelectedListenerLooper : this.onItemUnselectedListeners) {
            onItemUnSelectedListenerLooper.onItemUnselected(adapter, viewHolder, item);
        }
    }

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public boolean clickable = true;
        public boolean selectable = true;
        protected Integer boundPosition;
        private Runnable updateTask;
        private BaseViewBinder viewHolderBinder;

        protected BaseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.updateTask = new Runnable() {
                @Override
                public void run() {
                    BaseViewHolder.this.update();
                }
            };
        }

        public BaseViewBinder getViewBinder() {
            return viewHolderBinder;
        }

        private void bind(int position) {
            this.boundPosition = position;
            if(BaseAdapter.this.getItem(this.boundPosition) instanceof BaseViewBinder) {
                this.viewHolderBinder = (BaseViewBinder)BaseAdapter.this.getItem(this.boundPosition);
            }
            BaseAdapter.this.MAIN_HANDLER.post(this.updateTask);
        }

        private void update() {
            if(BaseAdapter.this.selectedKeys.containsValue(this.boundPosition)) {
                this.onSelected();
                if(BaseAdapter.this.newSelectedKeys.containsValue(this.boundPosition)) {
                    BaseAdapter.this.newSelectedKeys.remove(BaseAdapter.this.getItemKey(this.boundPosition));
                    BaseAdapter.this.itemSelected(BaseAdapter.this, this, BaseAdapter.this.getItem(this.boundPosition));
                }
            } else {
                this.onUnselected();
                if(BaseAdapter.this.newUnselectedKeys.containsValue(this.boundPosition)) {
                    BaseAdapter.this.newUnselectedKeys.remove(BaseAdapter.this.getItemKey(this.boundPosition));
                    BaseAdapter.this.itemUnselected(BaseAdapter.this, this, BaseAdapter.this.getItem(this.boundPosition));
                }
            }
        }

        protected void onSelected() {}

        protected void onUnselected() {}

        public boolean isSelected() {
            return BaseAdapter.this.isItemSelected(this.boundPosition);
        }

        @Override
        public void onClick(View view) {
            BaseAdapter.this.itemKeyClicked(BaseAdapter.this.getItemKey(this.boundPosition));
            BaseAdapter.this.MAIN_HANDLER.post(this.updateTask);
        }
    }

    public interface Item {
        Object getItemKey();
    }

    public interface OnItemSelectedListener {
        void onItemSelected(BaseAdapter adapter, BaseViewHolder viewHolder, Object item);
    }

    public interface OnItemUnselectedListener {
        void onItemUnselected(BaseAdapter adapter, BaseViewHolder viewHolder, Object item);
    }

    public interface OnItemClickedListener {
        void onItemClicked(BaseAdapter adapter, BaseViewHolder viewHolder, Object item);
    }

    public class BaseViewBinder {
        void bind(BaseViewHolder viewHolder) {}
    }
}
