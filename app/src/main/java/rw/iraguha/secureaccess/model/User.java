package rw.iraguha.secureaccess.model;

public class User {

    public String firstname;
    public String lastname;
    public String idnumber;
    public String phonenumber;
    public Integer rank;
    public Boolean isActive;
    public Boolean isAdmin;

    public User() {
    }

    public User(String firstname, String lastname, String idnumber, String phonenumber, Integer rank, Boolean isActive, Boolean isAdmin) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.idnumber = idnumber;
        this.phonenumber = phonenumber;
        this.rank = rank;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
