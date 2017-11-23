package dragonfly.exercisetracker.injection.modules;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    public static final String TAG = DataModule.class.getSimpleName();
    public static final SimpleDateFormat API_SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
    private Gson gson;
    private Calendar dateConverter = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

    public DataModule() {
        this.gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    @Override
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        try {
                            dateConverter.setTimeInMillis(API_SDF.parse(json.getAsString()).getTime());
                            return dateConverter.getTime();
                        } catch (ParseException e) {
                            Log.e(TAG, "ParseException, can't handle date", e);
                            return null;
                        }
                    }
                })
                .create();
    }

    @Provides
    Gson provideGson() {
        return this.gson;
    }

}
