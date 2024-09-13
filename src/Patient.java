import java.io.Serializable;

public class Patient implements Serializable {
    private int patientID;
    private String name;
    private int age;
    private String phone;

    public Patient(int patientID, String name, int age, String phone) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void printPatientDetails() {
        System.out.println("Patient ID: " + patientID +
                           ", Name: " + name +
                           ", Age: " + age +
                           ", Phone: " + phone);
    }
}
