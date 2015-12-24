package com.adithyasairam.android.android_commons;

import android.os.Parcel;

/**
 * Created by Adi on 12/2/2015.
 */
public class Parcelables {
    private Parcelables() {
    }

    public <T> void put(Parcel parcel, T t) {
        if (t instanceof Byte[]) { parcel.writeByteArray((byte[]) t); }
        if (t instanceof Object[]) { parcel.writeArray((Object[]) t); }
        }
    }
