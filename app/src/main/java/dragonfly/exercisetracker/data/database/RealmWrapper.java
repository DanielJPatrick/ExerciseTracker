package dragonfly.exercisetracker.data.database;


import dragonfly.exercisetracker.data.database.models.DIModel;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

public abstract class RealmWrapper {

    private static long getNewPrimaryKey(Realm realm, Class<? extends DIModel> objectType) {
        try {
            return realm.where(objectType).max(DIModel.PRIMARY_KEY).longValue() + 1L;
        } catch (ArrayIndexOutOfBoundsException e) {
            return 1L;
        } catch (NullPointerException e) {
            return 1L;
        }
    }

    public static void saveObject(final Realm realm, final DIModel object) throws RealmException {
        if(object != null) {
            if (object.getPrimaryKey() == null) {
                try {
                    object.setPrimaryKey(RealmWrapper.getNewPrimaryKey(realm, object.getClass()));
                } catch (DIModel.PrimaryKeyException e) {
                    e.printStackTrace();
                }
            }
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(object);
            realm.commitTransaction();
        }
    }
}