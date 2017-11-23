package dragonfly.exercisetracker.data.database.models;

import android.annotation.SuppressLint;

import java.io.Serializable;

import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class DAttribute implements DIModel, Serializable {
    @PrimaryKey
    @SuppressWarnings("UnnecessaryBoxing") @SuppressLint("UseValueOf")
    private Long primaryKey;
    private DVariable variable;
    private String value;

    public DAttribute() {}

    public DAttribute(DVariable variable, String value) {
        this.variable = variable;
        this.value = value;
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

    public DVariable getVariable() {
        return variable;
    }

    public void setVariable(DVariable variable) {
        this.variable = variable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof DAttribute)) {
            return false;
        }
        if(!(this.primaryKey == null && ((DAttribute)obj).primaryKey == null)) {
            if(this.primaryKey == null || ((DAttribute)obj).primaryKey == null) {
                return false;
            } else {
                if(!(this.primaryKey.equals(((DAttribute)obj).primaryKey))) {
                    return false;
                }
            }
        }
        if(!(this.variable == null && ((DAttribute)obj).variable == null)) {
            if(this.variable == null || ((DAttribute)obj).variable == null) {
                return false;
            } else {
                if(!(this.variable.equals(((DAttribute)obj).variable))) {
                    return false;
                }
            }
        }
        if(!(this.value == null && ((DAttribute)obj).value == null)) {
            if(this.value == null || ((DAttribute)obj).value == null) {
                return false;
            } else {
                if(!(this.value.equals(((DAttribute)obj).value))) {
                    return false;
                }
            }
        }
        return true;
    }
}
