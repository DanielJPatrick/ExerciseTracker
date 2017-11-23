package dragonfly.exercisetracker.data.database.models;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class DDay implements DIModel, Serializable {

    @PrimaryKey
    private Long primaryKey;
    private Integer index;
    private RealmList<DWorkout> workouts;

    public DDay(){}

    public DDay(Integer index) {
        this.index = index;
    }

    public DDay(Integer index, RealmList<DWorkout> workouts) {
        this.index = index;
        this.workouts = workouts;
    }

    @Override
    public Long getPrimaryKey() {
        return primaryKey;
    }

    @Override @Deprecated
    public void setPrimaryKey(Long primaryKey) throws DIModel.PrimaryKeyException {
        Long[] primaryKeyRef = new Long[1];
        DModel.setPrimaryKey(primaryKeyRef, primaryKey);
        this.primaryKey = primaryKeyRef[0];
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public RealmList<DWorkout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(RealmList<DWorkout> workouts) {
        this.workouts = workouts;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof DDay)) {
            return false;
        }
        if(!(this.primaryKey == null && ((DDay)obj).primaryKey == null)) {
            if(this.primaryKey == null || ((DDay)obj).primaryKey == null) {
                return false;
            } else {
                if(!(this.primaryKey.equals(((DDay)obj).primaryKey))) {
                    return false;
                }
            }
        }
        if(!(this.index == null && ((DDay)obj).index == null)) {
            if(this.index == null || ((DDay)obj).index == null) {
                return false;
            } else {
                if(!(this.index.equals(((DDay)obj).index))) {
                    return false;
                }
            }
        }
        if(!(this.workouts == null && ((DDay)obj).workouts == null)) {
            if(this.workouts == null || ((DDay)obj).workouts == null) {
                return false;
            } else {
                if(!(this.workouts.equals(((DDay)obj).workouts))) {
                    return false;
                }
            }
        }
        return true;
    }
}
