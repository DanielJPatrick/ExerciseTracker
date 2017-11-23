package dragonfly.exercisetracker.data.database.models;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class DRoutine implements DIModel, Serializable {

    @PrimaryKey
    private Long primaryKey;
    private String name;
    private RealmList<DDay> days;

    public DRoutine(){}

    public DRoutine(String name) {
        this.name = name;
    }

    public DRoutine(String name, RealmList<DDay> days) {
        this.name = name;
        this.days = days;
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

    public RealmList<DDay> getDays() {
        return days;
    }

    public void setDays(RealmList<DDay> days) {
        this.days = days;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof DRoutine)) {
            return false;
        }
        if(!(this.primaryKey == null && ((DRoutine)obj).primaryKey == null)) {
            if(this.primaryKey == null || ((DRoutine)obj).primaryKey == null) {
                return false;
            } else {
                if(!(this.primaryKey.equals(((DRoutine)obj).primaryKey))) {
                    return false;
                }
            }
        }
        if(!(this.name == null && ((DRoutine)obj).name == null)) {
            if(this.name == null || ((DRoutine)obj).name == null) {
                return false;
            } else {
                if(!(this.name.equals(((DRoutine)obj).name))) {
                    return false;
                }
            }
        }
        if(!(this.days == null && ((DRoutine)obj).days == null)) {
            if(this.days == null || ((DRoutine)obj).days == null) {
                return false;
            } else {
                if(!(this.days.equals(((DRoutine)obj).days))) {
                    return false;
                }
            }
        }
        return true;
    }
}
