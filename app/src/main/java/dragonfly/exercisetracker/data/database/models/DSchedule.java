package dragonfly.exercisetracker.data.database.models;

import android.annotation.SuppressLint;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class DSchedule implements DIModel {

    @PrimaryKey @SuppressWarnings("UnnecessaryBoxing") @SuppressLint("UseValueOf")
    private Long primaryKey;
    private String name;
    private RealmList<DRoutine> routines;

    public DSchedule(){}

    public DSchedule(String name) {
        this.name = name;
    }

    public DSchedule(String name, RealmList<DRoutine> routines) {
        this.name = name;
        this.routines = routines;
    }

    @Override
    public Long getPrimaryKey() {
        return this.primaryKey;
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

    public RealmList<DRoutine> getRoutines() {
        return routines;
    }

    public void setRoutines(RealmList<DRoutine> routines) {
        this.routines = routines;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof DSchedule)) {
            return false;
        }
        if(!(this.primaryKey == null && ((DSchedule)obj).primaryKey == null)) {
            if(this.primaryKey == null || ((DSchedule)obj).primaryKey == null) {
                return false;
            } else {
                if(!(this.primaryKey.equals(((DSchedule)obj).primaryKey))) {
                    return false;
                }
            }
        }
        if(!(this.name == null && ((DSchedule)obj).name == null)) {
            if(this.name == null || ((DSchedule)obj).name == null) {
                return false;
            } else {
                if(!(this.name.equals(((DSchedule)obj).name))) {
                    return false;
                }
            }
        }
        if(!(this.routines == null && ((DSchedule)obj).routines == null)) {
            if(this.routines == null || ((DSchedule)obj).routines == null) {
                return false;
            } else {
                if(!(this.routines.equals(((DSchedule)obj).routines))) {
                    return false;
                }
            }
        }
        return true;
    }
}
