package dragonfly.exercisetracker.data.database.models;

import android.annotation.SuppressLint;

import java.io.Serializable;

import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class DDataType implements DIModel, Serializable {
    @PrimaryKey @SuppressWarnings("UnnecessaryBoxing") @SuppressLint("UseValueOf")
    private Long primaryKey;
    private Integer value;

    public DDataType() {}

    public DDataType(Integer value) {
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

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static DataType getDataType(int value) {
        for (DataType dataTypeLooper : DataType.values()) {
            if(dataTypeLooper.ordinal() == value) {
                return dataTypeLooper;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof DDataType)) {
            return false;
        }
        if(!(this.primaryKey == null && ((DDataType)obj).primaryKey == null)) {
            if(this.primaryKey == null || ((DDataType)obj).primaryKey == null) {
                return false;
            } else {
                if(!(this.primaryKey.equals(((DDataType)obj).primaryKey))) {
                    return false;
                }
            }
        }
        if(!(this.value == null && ((DDataType)obj).value == null)) {
            if(this.value == null || ((DDataType)obj).value == null) {
                return false;
            } else {
                if(!(this.value.equals(((DDataType)obj).value))) {
                    return false;
                }
            }
        }
        return true;
    }

    public enum DataType {
        INTEGER,
        DOUBLE,
        BOOLEAN,
        STRING,
        DATE
    }
}
