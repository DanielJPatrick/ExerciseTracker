package dragonfly.exercisetracker.data.database.models;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class DWorkout implements DIModel {

    @PrimaryKey
    private Long primaryKey;
    private String name;
    private RealmList<DPrescription> prescriptions;

    public DWorkout(){}

    public DWorkout(String name) {
        this.name = name;
    }

    public DWorkout(String name, RealmList<DPrescription> prescriptions) {
        this.name = name;
        this.prescriptions = prescriptions;
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

    public RealmList<DPrescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(RealmList<DPrescription> prescriptions) {
        this.prescriptions = prescriptions;
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
        if(!(this.prescriptions == null && ((DWorkout)obj).prescriptions == null)) {
            if(this.prescriptions == null || ((DWorkout)obj).prescriptions == null) {
                return false;
            } else {
                if(!(this.prescriptions.equals(((DWorkout)obj).prescriptions))) {
                    return false;
                }
            }
        }
        return true;
    }
}
