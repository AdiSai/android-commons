package com.adithyasairam.android.android_commons;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.adithyasairam.Utils.Misc.ClassTemplates.Builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Adi on 10/24/2015.
 */
public class Intents {
    public static class IntentBuilder implements Builder<Intent> {
        private Class<?> destinationClass;
        private Context context;
        private Map<String, Serializable> serializableData;
        private Map<String, Parcelable> parcelableData;

        public IntentBuilder() {
            destinationClass = null;
            context = null;
            serializableData = new HashMap<>();
            parcelableData = new HashMap<>();
        }

        public IntentBuilder toClass(Class<?> destination) {
            destinationClass = destination;
            return this;
        }

        public IntentBuilder withContext(Context c) {
            context = c;
            return this;
        }

        public IntentBuilder withSerializable(String key, Serializable serializable) {
            serializableData.put(key, serializable);
            return this;
        }

        public IntentBuilder withParcelable(String key, Parcelable parcelable) {
            parcelableData.put(key, parcelable);
            return this;
        }

        @Override
        public Intent build() {
            Intent intent;
            if (context == null || destinationClass == null) {
                intent = new Intent();
            } else {
                intent = new Intent(context, destinationClass);
            }
            if (!(serializableData.size() == 0 && serializableData.isEmpty())) {
                List<String> keys = Arrays.asList(serializableData.keySet().toArray(new String[serializableData.keySet().size()]));
                List<Serializable> data = new ArrayList<>(serializableData.values());
                for (int i = 0; i < keys.size(); i++) {
                    intent.putExtra(keys.get(i), data.get(i));
                }
            }
            if (!(parcelableData.size() == 0 && parcelableData.isEmpty())) {
                List<String> keys = Arrays.asList(parcelableData.keySet().toArray(new String[parcelableData.keySet().size()]));
                List<Parcelable> data = new ArrayList<>(parcelableData.values());
                for (int i = 0; i < keys.size(); i++) {
                    intent.putExtra(keys.get(i), data.get(i));
                }
            }
            return intent;
        }

        public static Intent buildDefaultIntent(Context context, Class<?> destination) { return new Intent(context, destination); }

        public static Intent buildBlankIntent() { return new Intent(); }
    }

    public static class IntentProperties {
        private static class ItemNotFoundException extends Exception {
            public ItemNotFoundException() {
                super();
            }

            public ItemNotFoundException(String message) {
                super(message);
            }

            public ItemNotFoundException(Throwable cause) {
                super(cause);
            }

            public ItemNotFoundException(String message, Throwable cause) {
                super(message, cause);
            }
        }

        public static <T> T getSerializable(String name, Intent intent) throws ItemNotFoundException {
            Serializable serializable = intent.getSerializableExtra(name);
            if (serializable == null) {
                throw new ItemNotFoundException("Serializable " + name + " not found in " + intent.toString() + ".");
            }
            return (T) serializable;
        }

        public static <T> T getParcelable(String name, Intent intent) throws ItemNotFoundException {
            Parcelable parcelable = intent.getParcelableExtra(name);
            if (parcelable == null) {
                throw new ItemNotFoundException("Parcelable " + name + " not found in " + intent.toString() + ".");
            }
            return (T) parcelable;
        }
    }
}
