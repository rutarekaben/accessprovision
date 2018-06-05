package rw.iraguha.secureaccess.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Report implements Parcelable {
    private String title;
    private String message;
    private Date createdAt;

    public Report() {
    }

    public Report(String title, String message, Date createdAt) {
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
    }

    protected Report(Parcel in) {
        this.title = in.readString();
        this.message = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel source) {
            return new Report(source);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };
}
