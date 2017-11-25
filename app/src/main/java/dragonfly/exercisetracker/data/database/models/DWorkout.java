package dragonfly.exercisetracker.data.database.models;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class DWorkout implements DIModel {

    @PrimaryKey
    private Long primaryKey;
    private String name;
    private RealmList<DExercise> exercises;

    public DWorkout(){}

    public DWorkout(String name) {
        this.name = name;
    }

    public DWorkout(String name, RealmList<DExercise> exercises) {
        this.name = name;
        this.exercises = exercises;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<DExercise> getExercises() {
        return exercises;
    }

    public void setExercises(RealmList<DExercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof DWorkout)) {
            return false;
        }
        if(!(this.primaryKey == null && ((DWorkout)obj).primaryKey == null)) {
            if(this.primaryKey == null || ((DWorkout)obj).primaryKey == null) {
                return false;
            } else {
                if(!(this.primaryKey.equals(((DWorkout)obj).primaryKey))) {
                    return false;
                }
            }
        }
        if(!(this.name == null && ((DWorkout)obj).name == null)) {
            if(this.name == null || ((DWorkout)obj).name == null) {
                return false;
            } else {
                if(!(this.name.equals(((DWorkout)obj).name))) {
                    return false;
                }
            }
        }
        if(!(this.exercises == null && ((DWorkout)obj).exercises == null)) {
            if(this.exercises == null || ((DWorkout)obj).exercises == null) {
                return false;
            } else {
                if(!(this.exercises.equals(((DWorkout)obj).exercises))) {
                    return false;
                }
            }
        }
        return true;
    }
}
