import java.time.LocalDate;

public class Appointment {
    private int appointmentID;
    private Doctor doctor;
    private Patient patient;
    private LocalDate appointmentDate;

    public Appointment(int appointmentID, Doctor doctor, Patient patient, LocalDate appointmentDate) {
        this.appointmentID = appointmentID;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void printAppointmentDetails() {
        System.out.println("Appointment ID: " + appointmentID);
        System.out.println("Doctor: " + doctor);
        System.out.println("Patient: " + patient);
        System.out.println("Date: " + appointmentDate);
    }
}
