package dragonfly.exercisetracker.data.database.models;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class DPrescription implements DIModel, Serializable {

    @PrimaryKey
    private Long primaryKey;
    private DExercise exercise;
    private RealmList<DAttribute> attributes;

    public DPrescription() {}

    public DPrescription(DExercise exercise, RealmList<DAttribute> attributes) {
        this.exercise = exercise;
        this.attributes = attributes;
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

    public DExercise getExercise() {
        return exercise;
    }

    public void setExercise(DExercise exercise) {
        this.exercise = exercise;
    }

    public RealmList<DAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(RealmList<DAttribute> attributes) {
        this.attributes = attributes;
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
        if(!(this.attributes == null && ((DPrescription)obj).attributes == null)) {
            if(this.attributes == null || ((DPrescription)obj).attributes == null) {
                return false;
            } else {
                if(!(this.attributes.equals(((DPrescription)obj).attributes))) {
                    return false;
                }
            }
        }
        return true;
    }
}
