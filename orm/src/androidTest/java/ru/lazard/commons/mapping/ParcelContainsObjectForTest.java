/**************************************************************************
 * TODO copyright
 *
 * $Id: ParcelContainsObjectForTest.java 71 2012-05-23 01:28:27Z tepikin $
 * $HeadURL: svn://rcs/android-commons/common_lib/trunk/EnterraCommonsTest/src/com/enterra/android/lib/commons/mapping/ParcelContainsObjectForTest.java $
 **************************************************************************/

package ru.lazard.commons.mapping;

/**
 * Object for tests. Object contains parcel.
 */
public class ParcelContainsObjectForTest {
    @MappedField
    private ParcelObjectForTest parcel;

    @MappedField
    private String text;

    /**
     * @return
     */
    public ParcelObjectForTest getParcel() {
        return parcel;
    }

    /**
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * @param parcel
     */
    public void setParcel(ParcelObjectForTest parcel) {
        this.parcel = parcel;
    }

    /**
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

}
