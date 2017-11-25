package dragonfly.exercisetracker.ui.views.recyclerviews.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dragonfly.exercisetracker.R;
import dragonfly.exercisetracker.data.database.models.DExercise;
import dragonfly.exercisetracker.data.database.models.DIModel;
import io.realm.Realm;
import io.realm.RealmResults;

public class ExerciseAdapter extends BaseAdapter {
    private static final int EXERCISE_VH_TYPE = 1;

    public ExerciseAdapter(DExercise[] rawItems) {
        super(rawItems);
    }

    @Override
    protected Object[] generateListItems(Object[] rawItems) {
        ArrayList<Object> items = new ArrayList<Object>();
        if(rawItems == null) {
            return null;
        } else {
            for(Object rawItemLooper : rawItems) {
                items.add(new ExerciseViewBinder((DExercise)rawItemLooper));
            }
        }
        return items.toArray();
    }

    @Override
    public int getItemViewType(int position) {
        if(super.getItem(position) instanceof ExerciseAdapter.ExerciseViewBinder) {
            return ExerciseAdapter.EXERCISE_VH_TYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case(ExerciseAdapter.EXERCISE_VH_TYPE):
                return new ExerciseAdapter.ExerciseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_variable_list_variable, parent, false));
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
            this.deleteIv = (ImageView)itemView.findViewById(R.id.delete_Iv);
            this.deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Realm.getDefaultInstance().beginTransaction();
                    Realm.getDefaultInstance().where(DExercise.class).equalTo(DIModel.PRIMARY_KEY, ExerciseViewHolder.this.getViewBinder().exercise.getPrimaryKey()).findAll().deleteAllFromRealm();
                    Realm.getDefaultInstance().commitTransaction();
                    RealmResults<DExercise> realmResults = Realm.getDefaultInstance().where(DExercise.class).findAll();
                    ExerciseAdapter.this.setItems(realmResults.toArray(new DExercise[realmResults.size()]));
                }
            });
        }

        @Override
        public ExerciseAdapter.ExerciseViewBinder getViewBinder() {
            return (ExerciseAdapter.ExerciseViewBinder)super.getViewBinder();
        }
    }

    public class ExerciseViewBinder extends BaseViewBinder {
        public DExercise exercise;

        public ExerciseViewBinder(DExercise exercise) {
            this.exercise = exercise;
        }

        @Override
        public void bind(BaseViewHolder viewHolder) {
            ((ExerciseViewHolder)viewHolder).nameTv.setText(this.exercise.getName());
        }
    }
}
