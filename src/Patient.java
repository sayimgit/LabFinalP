public class Patient {
    private int patientID;
    private String name;
    private int age;
    private String phoneNumber;

    public Patient(int patientID, String name, int age, String phoneNumber) {
        this.patientID = patientID;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public int getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientID + ", Name: " + name + ", Age: " + age + ", Phone Number: " + phoneNumber;
    }
}
