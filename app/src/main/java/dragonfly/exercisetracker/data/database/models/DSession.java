package dragonfly.exercisetracker.data.database.models;


import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

public class DSession implements DIModel {
    @PrimaryKey
    private Long primaryKey;
    private DWorkout workout;
    private RealmList<DPrescription> prescriptions;

    @Override
    public Long getPrimaryKey() {
        return primaryKey;
    }

    @Override @Deprecated
    public void setPrimaryKey(Long primaryKey) throws DIModel.PrimaryKeyException {
        Long[] primaryKeyRef = new Long[1];
        DIModel.DModel.setPrimaryKey(primaryKeyRef, primaryKey);
        this.primaryKey = primaryKeyRef[0];
    }

    public DWorkout getWorkout() {
        return workout;
    }

    public void setWorkout(DWorkout workout) {
        this.workout = workout;
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
        if(!(obj instanceof DSession)) {
            return false;
        }
        if(!(this.primaryKey == null && ((DSession)obj).primaryKey == null)) {
            if(this.primaryKey == null || ((DSession)obj).primaryKey == null) {
                return false;
            } else {
                if(!(this.primaryKey.equals(((DSession)obj).primaryKey))) {
                    return false;
                }
            }
        }
        if(!(this.workout == null && ((DSession)obj).workout == null)) {
            if(this.workout == null || ((DSession)obj).workout == null) {
                return false;
            } else {
                if(!(this.workout.equals(((DSession)obj).workout))) {
                    return false;
                }
            }
        }
        if(!(this.prescriptions == null && ((DSession)obj).prescriptions == null)) {
            if(this.prescriptions == null || ((DSession)obj).prescriptions == null) {
                return false;
            } else {
                if(!(this.prescriptions.equals(((DSession)obj).prescriptions))) {
                    return false;
                }
            }
        }
        return true;
    }
}
