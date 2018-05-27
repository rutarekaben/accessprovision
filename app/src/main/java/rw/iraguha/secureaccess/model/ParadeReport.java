package rw.iraguha.secureaccess.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ParadeReport implements Parcelable {

    String date;
    String month;
    String year;
    String district;
    List<Parade> data;

    public ParadeReport() {
    }

    public ParadeReport(String date, String month, String year, String district, List<Parade> data) {
        this.date = date;
        this.month = month;
        this.year = year;
        this.district = district;
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<Parade> getData() {
        return data;
    }

    public void setData(List<Parade> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.month);
        dest.writeString(this.year);
        dest.writeString(this.district);
        dest.writeTypedList(this.data);
    }

    protected ParadeReport(Parcel in) {
        this.date = in.readString();
        this.month = in.readString();
        this.year = in.readString();
        this.district = in.readString();
        this.data = in.createTypedArrayList(Parade.CREATOR);
    }

    public static final Parcelable.Creator<ParadeReport> CREATOR = new Parcelable.Creator<ParadeReport>() {
        @Override
        public ParadeReport createFromParcel(Parcel source) {
            return new ParadeReport(source);
        }

        @Override
        public ParadeReport[] newArray(int size) {
            return new ParadeReport[size];
        }
    };
}
