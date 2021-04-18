package com.education.Vidhyalaya_Faculty_APP.API.Faculty;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable {

    private String  id;
    private String name;
    private boolean isSelected;

    /**
     * Create parcelable of friend
     */
    public static final Parcelable.Creator<Friend> CREATOR = new Parcelable.Creator<Friend>() {
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    /**
     * Create Friend from Parcel object.
     *
     * @param in
     */
    public Friend(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString( (this.id));
        dest.writeByte((byte) (this.isSelected ? 1 : 0));
    }

    public Friend(String id,String name) {
        this.name = name;
        this.id = id; // true is male, false is woman
        this.isSelected = false; // not selected when create
    }

    public String isId() {
        return id;
    }

    public void setid(String  id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
