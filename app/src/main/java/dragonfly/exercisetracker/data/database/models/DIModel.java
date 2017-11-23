package dragonfly.exercisetracker.data.database.models;

import android.support.annotation.RequiresApi;

import io.realm.RealmModel;

public interface DIModel extends RealmModel {
    public static final String PRIMARY_KEY = "primaryKey";
    public Long getPrimaryKey();
    public void setPrimaryKey(Long primaryKey) throws DIModel.PrimaryKeyException;


    public static abstract class DModel {
        //public static Long[] getPrimaryKey(Long[] primaryKeyRef) {
            //return primaryKeyRef;
        //}

        public static void setPrimaryKey(Long[] primaryKeyRef, long primaryKeyValue) throws DIModel.PrimaryKeyException {
            if(primaryKeyRef[0] == null) {
                primaryKeyRef[0] = primaryKeyValue;
            } else {
                throw new DIModel.PrimaryKeyException("PrimaryKey already set");
            }
        }
    }

    public static class PrimaryKeyException extends Exception {
        public PrimaryKeyException() {
            super();
        }

        public PrimaryKeyException(String message) {
            super(message);
        }

        public PrimaryKeyException(String message, Throwable cause) {
            super(message, cause);
        }

        public PrimaryKeyException(Throwable cause) {
            super(cause);
        }

        @RequiresApi(24)
        protected PrimaryKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
