/**************************************************************************
 * TODO copyright
 *
 * $Id: ParcelObjectForTest.java 71 2012-05-23 01:28:27Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/mapping/ParcelObjectForTest.java $
 **************************************************************************/

package ru.lazard.commons.mapping;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Class for test parcel mapping.
 */
public class ParcelObjectForTest implements Parcelable {
    public static final Creator<ParcelObjectForTest> CREATOR = new Creator<ParcelObjectForTest>() {
        @Override
        public ParcelObjectForTest createFromParcel(Parcel in) {
            return new ParcelObjectForTest(in);
        }

        @Override
        public ParcelObjectForTest[] newArray(int size) {
            return new ParcelObjectForTest[size];
        }
    };

    private HashMap<String, String> map;

    public ParcelObjectForTest() {
        map = new HashMap<String, String>();
    }

    public ParcelObjectForTest(Parcel in) {
        map = new HashMap<String, String>();
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String get(String key) {
        return map.get(key);
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public void readFromParcel(Parcel in) {
        int count = in.readInt();
        for (int i = 0; i < count; i++) {
            map.put(in.readString(), in.readString());
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(map.size());
        for (String s : map.keySet()) {
            dest.writeString(s);
            dest.writeString(map.get(s));
        }
    }
}