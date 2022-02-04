package Models;

public class Doctor {
    String firstName;
    String lastName;
    String address;
    String clinicName;
    double clinicLongitude;
    double clinicLatitude;

    public Doctor(String firstName, String lastName, String address, String clinicName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.clinicName = clinicName;
    }

    public Doctor(String firstName, String lastName, String address, String clinicName, double clinicLongitude, double clinicLatitude)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.clinicName = clinicName;
        this.clinicLongitude = clinicLongitude;
        this.clinicLatitude = clinicLatitude;
    }
//    Getter
    public String getFirstName() {
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getAddress() {
        return address;
    }
    public String getClinicName(){
        return clinicName;
    }
    public double getClinicLongitude() {return clinicLongitude;}
    public double getClinicLatitude() {return clinicLatitude;}

    //    Setter
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setClinicName(String clinicName){
        this.clinicName = clinicName;
    }
    public void setClinicLongitude(double clinicLongitude) {this.clinicLongitude = clinicLongitude;}
    public void setClinicLatitude(double clinicLatitude) {this.clinicLatitude = clinicLatitude;}
}
