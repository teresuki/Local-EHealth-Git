package Models;

public class Doctor {
    String firstName;
    String lastName;
    String address;
    String clinicName;

    public Doctor(String firstName, String lastName, String address, String clinicName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.clinicName = clinicName;
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
}
