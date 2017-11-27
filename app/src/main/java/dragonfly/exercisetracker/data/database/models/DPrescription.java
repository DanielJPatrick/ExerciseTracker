package dragonfly.exercisetracker.data.database.models;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class DPrescription implements DIModel {

    @PrimaryKey
    private Long primaryKey;
    private DExercise exercise;
    private RealmList<DAttribute> targets;
    private RealmList<DAttribute> values;

    public DPrescription() {}

    public DPrescription(DExercise exercise) {
        this.exercise = exercise;
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

    public DExercise getExercise() {
        return exercise;
    }

    public void setExercise(DExercise exercise) {
        this.exercise = exercise;
    }

    public RealmList<DAttribute> getTargets() {
        return targets;
    }

    public void setTargets(RealmList<DAttribute> targets) {
        this.targets = targets;
    }

    public RealmList<DAttribute> getValues() {
        return values;
    }

    public void setValues(RealmList<DAttribute> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof DPrescription)) {
            return false;
        }
        if(!(this.primaryKey == null && ((DPrescription)obj).primaryKey == null)) {
            if(this.primaryKey == null || ((DPrescription)obj).primaryKey == null) {
                return false;
            } else {
                if(!(this.primaryKey.equals(((DPrescription)obj).primaryKey))) {
                    return false;
                }
            }
        }
        if(!(this.exercise == null && ((DPrescription)obj).exercise == null)) {
            if(this.exercise == null || ((DPrescription)obj).exercise == null) {
                return false;
            } else {
                if(!(this.exercise.equals(((DPrescription)obj).exercise))) {
                    return false;
                }
            }
        }
        if(!(this.targets == null && ((DPrescription)obj).targets == null)) {
            if(this.targets == null || ((DPrescription)obj).targets == null) {
                return false;
            } else {
                if(!(this.targets.equals(((DPrescription)obj).targets))) {
                    return false;
                }
            }
        }
        if(!(this.values == null && ((DPrescription)obj).values == null)) {
            if(this.values == null || ((DPrescription)obj).values == null) {
                return false;
            } else {
                if(!(this.values.equals(((DPrescription)obj).values))) {
                    return false;
                }
            }
        }
        return true;
    }
}
