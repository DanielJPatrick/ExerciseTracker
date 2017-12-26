package dragonfly.exercisetracker.ui.views.recyclerviews.adapters;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.models.DExercise;

public class WorkoutExerciseSelectorAdapter extends BaseAdapter {
    private static final int EXERCISE_VH_TYPE = 1;

    public WorkoutExerciseSelectorAdapter(DExercise[] rawItems) {
        super(rawItems);
    }

    @Override
    protected Object[] generateListItems(Object[] rawItems) {
        ArrayList<Object> items = new ArrayList<Object>();
        if(rawItems == null) {
            return null;
        } else {
            for(Object rawItemLooper : rawItems) {
                items.add(new WorkoutExerciseSelectorAdapter.ExerciseViewBinder((DExercise)rawItemLooper));
            }
        }
        return items.toArray();
    }

    @Override
    public int getItemViewType(int position) {
        if(super.getItem(position) instanceof WorkoutExerciseSelectorAdapter.ExerciseViewBinder) {
            return WorkoutExerciseSelectorAdapter.EXERCISE_VH_TYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case(WorkoutExerciseSelectorAdapter.EXERCISE_VH_TYPE):
                return new WorkoutExerciseSelectorAdapter.ExerciseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout_exercise_list_exercise, parent, false));
        }
        return null;
    }

    private class ExerciseViewHolder extends BaseViewHolder {
        private View itemView;
        private TextView nameTv;
        private ImageView deleteIv;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.nameTv = (TextView)itemView.findViewById(R.id.name_tv);
        }

        @Override
        public dragonfly.exercisetracker.ui.views.recyclerviews.adapters.ExerciseAdapter.ExerciseViewBinder getViewBinder() {
            return (dragonfly.exercisetracker.ui.views.recyclerviews.adapters.ExerciseAdapter.ExerciseViewBinder)super.getViewBinder();
        }

        @Override
        protected void onSelected() {
            this.itemView.setBackgroundColor(this.itemView.getResources().getColor(R.color.accent));
        }

        @Override
        protected void onUnselected() {
            this.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    public class ExerciseViewBinder extends BaseViewBinder {
        public DExercise exercise;

        public ExerciseViewBinder(DExercise exercise) {
            this.exercise = exercise;
        }

        @Override
        public void bind(BaseViewHolder viewHolder) {
            ((WorkoutExerciseSelectorAdapter.ExerciseViewHolder)viewHolder).nameTv.setText(this.exercise.getName());
        }
    }
}
