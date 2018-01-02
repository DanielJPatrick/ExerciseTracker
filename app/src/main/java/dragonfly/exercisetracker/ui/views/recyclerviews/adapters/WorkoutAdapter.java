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
import dragonfly.exercisetracker.data.database.models.DWorkout;
import dragonfly.exercisetracker.ui.fragments.DeleteConfirmDialogFragment;
import io.realm.Realm;
import io.realm.RealmResults;

public class WorkoutAdapter extends BaseAdapter {
    private static final int WORKOUT_VH_TYPE = 1;
    private AppCompatActivity activity;

    public WorkoutAdapter(AppCompatActivity activity, DWorkout[] rawItems) {
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
                items.add(new WorkoutViewBinder((DWorkout)rawItemLooper));
            }
        }
        return items.toArray();
    }

    @Override
    public int getItemViewType(int position) {
        if(super.getItem(position) instanceof WorkoutAdapter.WorkoutViewBinder) {
            return WorkoutAdapter.WORKOUT_VH_TYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case(WorkoutAdapter.WORKOUT_VH_TYPE):
                return new WorkoutAdapter.WorkoutViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout_list_workout, parent, false));
        }
        return null;
    }

    private class WorkoutViewHolder extends BaseViewHolder implements DeleteConfirmDialogFragment.NoticeDialogListener {
        private View itemView;
        private TextView nameTv;
        private ImageView deleteIv;

        public WorkoutViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.nameTv = (TextView)itemView.findViewById(R.id.name_tv);
            this.deleteIv = (ImageView)itemView.findViewById(R.id.delete_Iv);
            this.deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteConfirmDialogFragment deleteConfirmDialogFragment = new DeleteConfirmDialogFragment();
                    deleteConfirmDialogFragment.setListener(WorkoutViewHolder.this);
                    deleteConfirmDialogFragment.show(WorkoutAdapter.this.activity.getFragmentManager(), DeleteConfirmDialogFragment.class.getName());
                }
            });
        }

        @Override
        public WorkoutAdapter.WorkoutViewBinder getViewBinder() {
            return (WorkoutAdapter.WorkoutViewBinder)super.getViewBinder();
        }

        @Override
        public void onDialogPositiveClick(DialogFragment dialog) {
            Realm.getDefaultInstance().beginTransaction();
            Realm.getDefaultInstance().where(DWorkout.class).equalTo(DIModel.PRIMARY_KEY, WorkoutViewHolder.this.getViewBinder().workout.getPrimaryKey()).findAll().deleteAllFromRealm();
            Realm.getDefaultInstance().commitTransaction();
            RealmResults<DWorkout> realmResults = Realm.getDefaultInstance().where(DWorkout.class).findAll();
            WorkoutAdapter.this.setItems(realmResults.toArray(new DWorkout[realmResults.size()]));
        }

        @Override
        public void onDialogNegativeClick(DialogFragment dialog) {

        }
    }

    public class WorkoutViewBinder extends BaseViewBinder {
        public DWorkout workout;

        public WorkoutViewBinder(DWorkout workout) {
            this.workout = workout;
        }

        @Override
        public void bind(BaseViewHolder viewHolder) {
            ((WorkoutViewHolder)viewHolder).nameTv.setText(this.workout.getName());
        }
    }
}
