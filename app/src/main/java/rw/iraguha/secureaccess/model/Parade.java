package rw.iraguha.secureaccess.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Parade implements Parcelable {
    String abs;
    String adm;
    String closearr;
    String duty;
    String onp;
    String pass;
    String rank;
    String sick;
    String sickout;
    String total;
    String description;

    public Parade() {
    }

    public Parade(String abs, String adm, String closearr, String duty, String onp, String pass, String rank, String sick, String sickout, String total, String description) {
        this.abs = abs;
        this.adm = adm;
        this.closearr = closearr;
        this.duty = duty;
        this.onp = onp;
        this.pass = pass;
        this.rank = rank;
        this.sick = sick;
        this.sickout = sickout;
        this.total = total;
        this.description = description;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

    public String getClosearr() {
        return closearr;
    }

    public void setClosearr(String closearr) {
        this.closearr = closearr;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getOnp() {
        return onp;
    }

    public void setOnp(String onp) {
        this.onp = onp;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSick() {
        return sick;
    }

    public void setSick(String sick) {
        this.sick = sick;
    }

    public String getSickout() {
        return sickout;
    }

    public void setSickout(String sickout) {
        this.sickout = sickout;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.abs);
        dest.writeString(this.adm);
        dest.writeString(this.closearr);
        dest.writeString(this.duty);
        dest.writeString(this.onp);
        dest.writeString(this.pass);
        dest.writeString(this.rank);
        dest.writeString(this.sick);
        dest.writeString(this.sickout);
        dest.writeString(this.total);
        dest.writeString(this.description);
    }

    protected Parade(Parcel in) {
        this.abs = in.readString();
        this.adm = in.readString();
        this.closearr = in.readString();
        this.duty = in.readString();
        this.onp = in.readString();
        this.pass = in.readString();
        this.rank = in.readString();
        this.sick = in.readString();
        this.sickout = in.readString();
        this.total = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Parade> CREATOR = new Parcelable.Creator<Parade>() {
        @Override
        public Parade createFromParcel(Parcel source) {
            return new Parade(source);
        }

        @Override
        public Parade[] newArray(int size) {
            return new Parade[size];
        }
    };
}