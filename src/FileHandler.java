import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    // Save the list of doctors to a file
    public static void saveDoctors(List<Doctor> doctors) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("doctors.txt"))) {
            for (Doctor doctor : doctors) {
                writer.write(doctor.getDoctorID() + "," + doctor.getName() + "," + doctor.getSpecialization() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving doctors: " + e.getMessage());
        }
    }

    // Load the list of doctors from a file
    public static List<Doctor> loadDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("doctors.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length != 3) {
                    System.out.println("Invalid doctor data: " + line);
                    continue;
                }
                int doctorID = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                String specialization = tokens[2];

                Doctor doctor;
                // Create the appropriate type of doctor based on specialization
                switch (specialization.toLowerCase()) {
                    case "ent":
                        doctor = new ENT(doctorID, name);
                        break;
                    case "gastrology":
                        doctor = new Gastrology(doctorID, name);
                        break;
                    case "medicine":
                        doctor = new Medicine(doctorID, name);
                        break;
                    default:
                        continue;  // Skip unknown specializations
                }
                doctors.add(doctor);
            }
        } catch (IOException e) {
            System.out.println("Error loading doctors: " + e.getMessage());
        }
        return doctors;
    }

    // Save the list of patients to a file
    public static void savePatients(List<Patient> patients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("patients.txt"))) {
            for (Patient patient : patients) {
                writer.write(patient.getPatientID() + "," + patient.getName() + "," + patient.getAge() + "," + patient.getPhoneNumber() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving patients: " + e.getMessage());
        }
    }

    // Load the list of patients from a file
    public static List<Patient> loadPatients() {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("patients.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] tokens = line.split(",");
                if (tokens.length != 4) {
                    System.out.println("Invalid patient data: " + line);
                    continue;
                }

                int patientID = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                int age = Integer.parseInt(tokens[2]);
                String phoneNumber = tokens[3];

                Patient patient = new Patient(patientID, name, age, phoneNumber);
                patients.add(patient);
            }
        } catch (IOException e) {
            System.out.println("Error loading patients: " + e.getMessage());
        }
        return patients;
    }

    // Save the list of appointments to a file
    public static void saveAppointments(List<Appointment> appointments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("appointments.txt"))) {
            for (Appointment appointment : appointments) {
                writer.write(appointment.getAppointmentID() + "," + appointment.getDoctor().getDoctorID() + "," + appointment.getPatient().getPatientID() + "," + appointment.getAppointmentDate().toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving appointments: " + e.getMessage());
        }
    }

    // Load the list of appointments from a file
    public static List<Appointment> loadAppointments(List<Doctor> doctors, List<Patient> patients) {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("appointments.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length != 4) {
                    System.out.println("Invalid appointment data: " + line);
                    continue;
                }

                int appointmentID = Integer.parseInt(tokens[0]);
                int doctorID = Integer.parseInt(tokens[1]);
                int patientID = Integer.parseInt(tokens[2]);
                LocalDate appointmentDate = LocalDate.parse(tokens[3]);

                Doctor doctor = findDoctorByID(doctors, doctorID);
                Patient patient = findPatientByID(patients, patientID);

                if (doctor != null && patient != null) {
                    Appointment appointment = new Appointment(appointmentID, doctor, patient, appointmentDate);
                    appointments.add(appointment);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading appointments: " + e.getMessage());
        }
        return appointments;
    }

    // Helper method to find a doctor by ID
    private static Doctor findDoctorByID(List<Doctor> doctors, int doctorID) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorID() == doctorID) {
                return doctor;
            }
        }
        return null;
    }

    // Helper method to find a patient by ID
    private static Patient findPatientByID(List<Patient> patients, int patientID) {
        for (Patient patient : patients) {
            if (patient.getPatientID() == patientID) {
                return patient;
            }
        }
        return null;
    }
}
