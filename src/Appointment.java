import java.io.Serializable;
import java.time.LocalDate;

public class Appointment implements Serializable {
    private int appointmentID;
    private Doctor doctor;
    private Patient patient;
    private LocalDate date;
    private boolean served;

    public Appointment(int appointmentID, Doctor doctor, Patient patient, LocalDate date, boolean served) {
        this.appointmentID = appointmentID;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.served = served;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isServed() {
        return served;
    }

    public void setServed(boolean served) {
        this.served = served;
    }

    public void printAppointmentDetails() {
        System.out.println("Appointment ID: " + appointmentID +
                           ", Doctor: " + doctor.getName() +
                           ", Patient: " + patient.getName() +
                           ", Date: " + date +
                           ", Served: " + served);
    }
}
