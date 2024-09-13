import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String DOCTORS_FILE = "doctors.txt";
    private static final String PATIENTS_FILE = "patients.txt";
    private static final String APPOINTMENTS_FILE = "appointments.txt";

    // Load doctors from the file
    public static List<Doctor> loadDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DOCTORS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int doctorID = Integer.parseInt(parts[0]);
                String name = parts[1];
                String specialization = parts[2];
                Doctor doctor;
                switch (specialization) {
                    case "ENT Specialist":
                        doctor = new ENT(doctorID, name);
                        break;
                    case "Gastrologist":
                        doctor = new Gastrology(doctorID, name);
                        break;
                    case "General Medicine":
                        doctor = new Medicine(doctorID, name);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown specialization: " + specialization);
                }
                doctors.add(doctor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    // Save doctors to the file
    public static void saveDoctors(List<Doctor> doctors) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOCTORS_FILE))) {
            for (Doctor doctor : doctors) {
                bw.write(doctor.getDoctorID() + "," + doctor.getName() + "," + doctor.getSpecialization());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load patients from the file
    public static List<Patient> loadPatients() {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATIENTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int patientID = Integer.parseInt(parts[0]);
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);
                String phone = parts[3];
                Patient patient = new Patient(patientID, name, age, phone);
                patients.add(patient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return patients;
    }

    // Save patients to the file
    public static void savePatients(List<Patient> patients) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENTS_FILE))) {
            for (Patient patient : patients) {
                bw.write(patient.getPatientID() + "," + patient.getName() + "," + patient.getAge() + "," + patient.getPhone());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load appointments from the file
    public static List<Appointment> loadAppointments(List<Doctor> doctors, List<Patient> patients) {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(APPOINTMENTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int appointmentID = Integer.parseInt(parts[0]);
                int doctorID = Integer.parseInt(parts[1]);
                int patientID = Integer.parseInt(parts[2]);
                LocalDate date = LocalDate.parse(parts[3]);
                boolean served = Boolean.parseBoolean(parts[4]);

                Doctor doctor = doctors.stream()
                        .filter(d -> d.getDoctorID() == doctorID)
                        .findFirst()
                        .orElse(null);
                Patient patient = patients.stream()
                        .filter(p -> p.getPatientID() == patientID)
                        .findFirst()
                        .orElse(null);

                if (doctor != null && patient != null) {
                    Appointment appointment = new Appointment(appointmentID, doctor, patient, date, served);
                    appointments.add(appointment);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    // Save appointments to the file
    public static void saveAppointments(List<Appointment> appointments) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENTS_FILE))) {
            for (Appointment appointment : appointments) {
                bw.write(appointment.getAppointmentID() + "," +
                         appointment.getDoctor().getDoctorID() + "," +
                         appointment.getPatient().getPatientID() + "," +
                         appointment.getDate() + "," +
                         appointment.isServed());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
