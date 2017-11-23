package dragonfly.exercisetracker.data.database.models;

import java.io.Serializable;

import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class DExercise implements DIModel, Serializable {

    @PrimaryKey
    private Long primaryKey;
    private String name;

    public DExercise(){}

    public DExercise(String name) {
        this.name = name;
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
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof DExercise)) {
            return false;
        }
        if(!(this.primaryKey == null && ((DExercise)obj).primaryKey == null)) {
            if(this.primaryKey == null || ((DExercise)obj).primaryKey == null) {
                return false;
            } else {
                if(!(this.primaryKey.equals(((DExercise)obj).primaryKey))) {
                    return false;
                }
            }
        }
        if(!(this.name == null && ((DExercise)obj).name == null)) {
            if(this.name == null || ((DExercise)obj).name == null) {
                return false;
            } else {
                if(!(this.name.equals(((DExercise)obj).name))) {
                    return false;
                }
            }
        }
        return true;
    }
}
