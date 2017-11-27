package dragonfly.exercisetracker.ui.views.recyclerviews.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.models.DExercise;

public class WorkoutExerciseAdaptor extends BaseAdapter {
    private static final int EXERCISE_VH_TYPE = 1;

    public WorkoutExerciseAdaptor(DExercise[] rawItems) {
        super(rawItems);
    }

    @Override
    protected Object[] generateListItems(Object[] rawItems) {
        ArrayList<Object> items = new ArrayList<Object>();
        if(rawItems == null) {
            return null;
        } else {
            for(Object rawItemLooper : rawItems) {
                items.add(new WorkoutExerciseAdaptor.ExerciseViewBinder((DExercise)rawItemLooper));
            }
        }
        return items.toArray();
    }

    @Override
    public int getItemViewType(int position) {
        if(super.getItem(position) instanceof WorkoutExerciseAdaptor.ExerciseViewBinder) {
            return WorkoutExerciseAdaptor.EXERCISE_VH_TYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case(WorkoutExerciseAdaptor.EXERCISE_VH_TYPE):
                return new WorkoutExerciseAdaptor.ExerciseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_list_exercise, parent, false));
        }
        return null;
    }

    private class ExerciseViewHolder extends BaseAdapter.BaseViewHolder {
        private View itemView;
        private TextView nameTv;
        private ImageView deleteIv;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.nameTv = (TextView)itemView.findViewById(R.id.name_tv);
            this.deleteIv = (ImageView)itemView.findViewById(R.id.delete_Iv);
            this.deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WorkoutExerciseAdaptor.this.removeItems(new Integer[]{ExerciseViewHolder.this.boundPosition});
                }
            });
        }

        @Override
        public WorkoutExerciseAdaptor.ExerciseViewBinder getViewBinder() {
            return (WorkoutExerciseAdaptor.ExerciseViewBinder)super.getViewBinder();
        }
    }

    public class ExerciseViewBinder extends BaseAdapter.BaseViewBinder {
        public DExercise exercise;

        public ExerciseViewBinder(DExercise exercise) {
            this.exercise = exercise;
        }

        @Override
        public void bind(BaseAdapter.BaseViewHolder viewHolder) {
            ((WorkoutExerciseAdaptor.ExerciseViewHolder)viewHolder).nameTv.setText(this.exercise.getName());
        }
    }
}
