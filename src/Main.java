import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Load data from files
        List<Doctor> doctors = FileHandler.loadDoctors();
        List<Patient> patients = FileHandler.loadPatients();
        List<Appointment> appointments = FileHandler.loadAppointments(doctors, patients);

        while (true) {
            // Display menu options
            System.out.println("\n--- Doctor's Appointment Management System ---");
            System.out.println("1. View Doctors");
            System.out.println("2. View Patients");
            System.out.println("3. View Appointments");
            System.out.println("4. Add New Doctor");
            System.out.println("5. Add New Patient");
            System.out.println("6. Schedule New Appointment");
            System.out.println("7. Serve Patient");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewDoctors(doctors);
                    break;
                case 2:
                    viewPatients(patients);
                    break;
                case 3:
                    viewAppointments(appointments);
                    break;
                case 4:
                    addNewDoctor(doctors);
                    break;
                case 5:
                    addNewPatient(patients);
                    break;
                case 6:
                    scheduleNewAppointment(doctors, patients, appointments);
                    break;
                case 7:
                    servePatient(patients, appointments);
                    break;
                case 8:
                    // Save all data to files before exiting
                    FileHandler.saveDoctors(doctors);
                    FileHandler.savePatients(patients);
                    FileHandler.saveAppointments(appointments);
                    System.out.println("Exiting system...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void viewDoctors(List<Doctor> doctors) {
        System.out.println("\nDoctors:");
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }

    private static void viewPatients(List<Patient> patients) {
        System.out.println("\nPatients:");
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }

    private static void viewAppointments(List<Appointment> appointments) {
        System.out.println("\nAppointments:");
        for (Appointment appointment : appointments) {
            appointment.printAppointmentDetails();
        }
    }

    private static void addNewDoctor(List<Doctor> doctors) {
        System.out.print("Enter Doctor ID: ");
        int doctorID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Doctor Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Specialization (ENT, Gastrology, Medicine): ");
        String specialization = scanner.nextLine();

        Doctor doctor;
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
                System.out.println("Invalid specialization.");
                return;
        }
        doctors.add(doctor);
        System.out.println("New doctor added.");
    }

    private static void addNewPatient(List<Patient> patients) {
        System.out.print("Enter Patient ID: ");
        int patientID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Patient Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Patient Phone Number: ");
        String phoneNumber = scanner.nextLine();

        Patient patient = new Patient(patientID, name, age, phoneNumber);
        patients.add(patient);
        System.out.println("New patient added.");
    }

    private static void scheduleNewAppointment(List<Doctor> doctors, List<Patient> patients, List<Appointment> appointments) {
        System.out.println("\nAvailable Departments:");
        Map<String, List<Doctor>> departmentMap = new HashMap<>();
        for (Doctor doctor : doctors) {
            departmentMap.computeIfAbsent(doctor.getSpecialization(), k -> new ArrayList<>()).add(doctor);
        }

        int deptIndex = 1;
        for (String dept : departmentMap.keySet()) {
            System.out.println(deptIndex++ + ". " + dept);
        }

        System.out.print("Choose a department: ");
        int deptChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (deptChoice < 1 || deptChoice >= deptIndex) {
            System.out.println("Invalid department choice.");
            return;
        }

        String selectedDept = (String) departmentMap.keySet().toArray()[deptChoice - 1];
        List<Doctor> selectedDoctors = departmentMap.get(selectedDept);

        System.out.println("\nDoctors in " + selectedDept + " department:");
        int docIndex = 1;
        for (Doctor doctor : selectedDoctors) {
            System.out.println(docIndex++ + ". " + doctor);
        }

        System.out.print("Choose a doctor: ");
        int docChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (docChoice < 1 || docChoice >= docIndex) {
            System.out.println("Invalid doctor choice.");
            return;
        }

        Doctor selectedDoctor = selectedDoctors.get(docChoice - 1);
        System.out.print("Enter Patient ID: ");
        int patientID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Patient patient = findPatientByID(patients, patientID);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        LocalDate appointmentDate = LocalDate.parse(scanner.nextLine());

        // Check availability
        long count = appointments.stream()
            .filter(a -> a.getDoctor().equals(selectedDoctor) && a.getAppointmentDate().equals(appointmentDate))
            .count();

        if (count >= 3) {
            System.out.println("The doctor has no available slots on this date. Please choose another date.");
            return;
        }

        Appointment appointment = new Appointment(appointments.size() + 1, selectedDoctor, patient, appointmentDate);
        appointments.add(appointment);
        System.out.println("Appointment scheduled.");
    }

    private static void servePatient(List<Patient> patients, List<Appointment> appointments) {
        System.out.print("Enter Patient ID to remove: ");
        int patientID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Remove patient
        patients.removeIf(patient -> patient.getPatientID() == patientID);

        // Remove appointments associated with the patient
        appointments.removeIf(appointment -> appointment.getPatient().getPatientID() == patientID);

        System.out.println("Patient served and removed.");
    }

    private static Doctor findDoctor2ByID(List<Doctor> doctors, int doctorID) {
        for (Doctor doctor : doctors) {
            if (doctor.getDoctorID() == doctorID) {
                return doctor;
            }
        }
        return null;
    }

    private static Patient findPatientByID(List<Patient> patients, int patientID) {
        for (Patient patient : patients) {
            if (patient.getPatientID() == patientID) {
                return patient;
            }
        }
        return null;
    }
}
