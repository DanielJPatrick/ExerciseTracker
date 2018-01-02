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
import dragonfly.exercisetracker.data.database.models.DPrescription;
import dragonfly.exercisetracker.data.database.models.DWorkout;
import dragonfly.exercisetracker.ui.fragments.DeleteConfirmDialogFragment;
import io.realm.Realm;

public class WorkoutPrescriptionAdaptor extends BaseAdapter {
    private static final int EXERCISE_VH_TYPE = 1;
    private DWorkout workout;
    private AppCompatActivity activity;

    public WorkoutPrescriptionAdaptor(AppCompatActivity activity, DPrescription[] rawItems) {
        super(rawItems);
        this.activity = activity;
    }

    public DWorkout getWorkout() {
        return workout;
    }

    public void setWorkout(DWorkout workout) {
        this.workout = workout;
    }

    @Override
    protected Object[] generateListItems(Object[] rawItems) {
        ArrayList<Object> items = new ArrayList<Object>();
        if(rawItems == null) {
            return null;
        } else {
            for(Object rawItemLooper : rawItems) {
                items.add(new ExerciseViewBinder(((DPrescription)rawItemLooper)));
                if(((DPrescription) rawItemLooper).getTargets() != null) {
                    for (DAttribute targetLooper : ((DPrescription) rawItemLooper).getTargets()) {
                        //targetLooper.getValue()
                    }
                }
            }
        }
        return items.toArray();
    }

    @Override
    public int getItemViewType(int position) {
        if(super.getItem(position) instanceof ExerciseViewBinder) {
            return WorkoutPrescriptionAdaptor.EXERCISE_VH_TYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case(WorkoutPrescriptionAdaptor.EXERCISE_VH_TYPE):
                return new WorkoutPrescriptionAdaptor.ExerciseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_list_exercise, parent, false));
        }
        return null;
    }

    private class ExerciseViewHolder extends BaseAdapter.BaseViewHolder implements DeleteConfirmDialogFragment.NoticeDialogListener {
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
                    DeleteConfirmDialogFragment deleteConfirmDialogFragment = new DeleteConfirmDialogFragment();
                    deleteConfirmDialogFragment.setListener(ExerciseViewHolder.this);
                    deleteConfirmDialogFragment.show(WorkoutPrescriptionAdaptor.this.activity.getFragmentManager(), DeleteConfirmDialogFragment.class.getName());
                }
            });
        }

        @Override
        public ExerciseViewBinder getViewBinder() {
            return (ExerciseViewBinder)super.getViewBinder();
        }

        @Override
        public void onDialogPositiveClick(DialogFragment dialog) {
            if(WorkoutPrescriptionAdaptor.this.workout != null) {
                Realm.getDefaultInstance().beginTransaction();
                WorkoutPrescriptionAdaptor.this.workout.getPrescriptions().remove(((ExerciseViewBinder)WorkoutPrescriptionAdaptor.this.getItem(ExerciseViewHolder.this.boundPosition)).prescription);
                Realm.getDefaultInstance().commitTransaction();
            }
            WorkoutPrescriptionAdaptor.this.removeItems(new Integer[]{ExerciseViewHolder.this.boundPosition});
        }

        @Override
        public void onDialogNegativeClick(DialogFragment dialog) {

        }
    }

    public class ExerciseViewBinder extends BaseAdapter.BaseViewBinder {
        public DPrescription prescription;

        public ExerciseViewBinder(DPrescription prescription) {
            this.prescription = prescription;
        }

        @Override
        public void bind(BaseAdapter.BaseViewHolder viewHolder) {
            ((WorkoutPrescriptionAdaptor.ExerciseViewHolder)viewHolder).nameTv.setText(this.prescription.getExercise().getName());
        }
    }
}
