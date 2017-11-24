package dragonfly.exercisetracker.data.database.models;

import java.io.Serializable;

import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class DVariable implements DIModel, Serializable {

    @PrimaryKey
    private Long primaryKey;
    private String name;
    private DDataType dataType;

    public DVariable(){}

    public DVariable(String name, DDataType dataType) {
        this.name = name;
        this.dataType = dataType;
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

    public DDataType getDataType() {
        return dataType;
    }

    public void setDataType(DDataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof DVariable)) {
            return false;
        }
        if(!(this.primaryKey == null && ((DVariable)obj).primaryKey == null)) {
            if(this.primaryKey == null || ((DVariable)obj).primaryKey == null) {
                return false;
            } else {
                if(!(this.primaryKey.equals(((DVariable)obj).primaryKey))) {
                    return false;
                }
            }
        }
        if(!(this.name == null && ((DVariable)obj).name == null)) {
            if(this.name == null || ((DVariable)obj).name == null) {
                return false;
            } else {
                if(!(this.name.equals(((DVariable)obj).name))) {
                    return false;
                }
            }
        }
        if(!(this.dataType == null && ((DVariable)obj).dataType == null)) {
            if(this.dataType == null || ((DVariable)obj).dataType == null) {
                return false;
            } else {
                if(!(this.dataType.equals(((DVariable)obj).dataType))) {
                    return false;
                }
            }
        }
        return true;
    }
}
