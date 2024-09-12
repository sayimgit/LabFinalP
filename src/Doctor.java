public abstract class Doctor {
    private int doctorID;
    private String name;

    public Doctor(int doctorID, String name) {
        this.doctorID = doctorID;
        this.name = name;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public String getName() {
        return name;
    }

    public abstract String getSpecialization();

    public abstract void displayAvailability();

    @Override
    public String toString() {
        return "Doctor ID: " + doctorID + ", Name: " + name + ", Specialization: " + getSpecialization();
    }
}
