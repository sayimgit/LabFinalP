import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static List<Doctor> doctors = new ArrayList<>();
    private static List<Patient> patients = new ArrayList<>();
    private static List<Appointment> appointments = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize default doctors if no doctors are loaded
        doctors = FileHandler.loadDoctors();
        patients = FileHandler.loadPatients();
        appointments = FileHandler.loadAppointments(doctors, patients);

        if (doctors.isEmpty()) {
            addDefaultDoctors();
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Show Doctors by Department");
            System.out.println("2. Show Patients");
            System.out.println("3. Add Doctor");
            System.out.println("4. Delete Doctor");
            System.out.println("5. Add Patient");
            System.out.println("6. Reserve Appointment");
            System.out.println("7. Show Appointments");
            System.out.println("8. Cancel Appointment");
            System.out.println("9. Mark Appointment as Served");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

            switch (choice) {
                case 1:
                    showDoctorsByDepartment();
                    break;
                case 2:
                    showPatients();
                    break;
                case 3:
                    addDoctor();
                    break;
                case 4:
                    deleteDoctor();
                    break;
                case 5:
                    addPatient();
                    break;
                case 6:
                    reserveAppointment();
                    break;
                case 7:
                    showAppointments();
                    break;
                case 8:
                    cancelAppointment();
                    break;
                case 9:
                    markAppointmentAsServed();
                    break;
                case 10:
                    FileHandler.saveDoctors(doctors);
                    FileHandler.savePatients(patients);
                    FileHandler.saveAppointments(appointments);
                    System.out.println("Data saved. Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void showDoctorsByDepartment() {
        Map<String, List<Doctor>> doctorsByDepartment = doctors.stream()
            .collect(Collectors.groupingBy(Doctor::getSpecialization));

        for (Map.Entry<String, List<Doctor>> entry : doctorsByDepartment.entrySet()) {
            System.out.println(entry.getKey() + " Doctors:");
            for (Doctor doctor : entry.getValue()) {
                System.out.println("Doctor ID: " + doctor.getDoctorID() +
                                   ", Name: " + doctor.getName() +
                                   ", Specialization: " + doctor.getSpecialization());
            }
        }
    }

    private static void showPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients available.");
        } else {
            System.out.println("Patients:");
            for (Patient patient : patients) {
                patient.printPatientDetails();
            }
        }
    }

    private static void addDoctor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter doctor name: ");
        String name = scanner.nextLine();
        System.out.print("Enter department (ENT, Gastrology, Medicine): ");
        String department = scanner.nextLine();

        int doctorID = doctors.stream()
                .mapToInt(Doctor::getDoctorID)
                .max()
                .orElse(0) + 1;

        Doctor newDoctor;
        switch (department) {
            case "ENT":
                newDoctor = new ENT(doctorID, name);
                break;
            case "Gastrology":
                newDoctor = new Gastrology(doctorID, name);
                break;
            case "Medicine":
                newDoctor = new Medicine(doctorID, name);
                break;
            default:
                System.out.println("Invalid department.");
                return;
        }

        doctors.add(newDoctor);
        System.out.println("Doctor added successfully!");
    }

    private static void deleteDoctor() {
        Scanner scanner = new Scanner(System.in);
        showDoctorsByDepartment();  // Show all doctors to select from
        System.out.print("Enter Doctor ID to delete: ");
        int doctorID = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Doctor doctorToRemove = doctors.stream()
                .filter(doctor -> doctor.getDoctorID() == doctorID)
                .findFirst()
                .orElse(null);

        if (doctorToRemove != null) {
            doctors.remove(doctorToRemove);
            // Remove all appointments with this doctor
            appointments.removeIf(app -> app.getDoctor().equals(doctorToRemove));
            System.out.println("Doctor deleted successfully!");
        } else {
            System.out.println("Doctor not found.");
        }
    }

    private static void addPatient() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patient age: ");
        int age = scanner.nextInt();
        scanner.nextLine();  // Clear buffer
        System.out.print("Enter patient phone: ");
        String phone = scanner.nextLine();

        int patientID = patients.stream()
                .mapToInt(Patient::getPatientID)
                .max()
                .orElse(0) + 1;

        Patient newPatient = new Patient(patientID, name, age, phone);
        patients.add(newPatient);
        System.out.println("Patient added successfully!");
    }

    private static void reserveAppointment() {
        Scanner scanner = new Scanner(System.in);

        // Display all unserved patients
        List<Patient> unservedPatients = patients.stream()
                .filter(patient -> appointments.stream()
                        .noneMatch(appointment -> appointment.getPatient().equals(patient) && appointment.isServed()))
                .collect(Collectors.toList());

        if (unservedPatients.isEmpty()) {
            System.out.println("No unserved patients available.");
            return;
        }

        // Show unserved patients
        System.out.println("Unserved Patients:");
        for (Patient patient : unservedPatients) {
            System.out.println("Patient ID: " + patient.getPatientID() + ", Name: " + patient.getName());
        }

        // Prompt user to select a patient
        System.out.print("Enter Patient ID to reserve an appointment: ");
        int patientID = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Patient selectedPatient = unservedPatients.stream()
                .filter(patient -> patient.getPatientID() == patientID)
                .findFirst()
                .orElse(null);

        if (selectedPatient == null) {
            System.out.println("Invalid Patient ID.");
            return;
        }

        // Display all doctors
        System.out.println("Doctors:");
        for (Doctor doctor : doctors) {
            System.out.println("Doctor ID: " + doctor.getDoctorID() + ", Name: " + doctor.getName() +
                               ", Specialization: " + doctor.getSpecialization());
        }

        // Prompt user to select a doctor
        System.out.print("Enter Doctor ID to reserve an appointment: ");
        int doctorID = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Doctor selectedDoctor = doctors.stream()
                .filter(doctor -> doctor.getDoctorID() == doctorID)
                .findFirst()
                .orElse(null);

        if (selectedDoctor == null) {
            System.out.println("Invalid Doctor ID.");
            return;
        }

        // Check if the doctor can take more appointments
        long doctorAppointmentsCount = appointments.stream()
                .filter(app -> app.getDoctor().equals(selectedDoctor) && !app.isServed())
                .count();

        if (doctorAppointmentsCount >= 2) {  // Assuming doctor can take up to 2 patients a day
            System.out.println("The doctor has reached the maximum number of appointments for today.");
            return;
        }

        // Prompt user for appointment date
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String dateString = scanner.nextLine();
        LocalDate appointmentDate;

        try {
            appointmentDate = LocalDate.parse(dateString);
        } catch (Exception e) {
            System.out.println("Invalid date format.");
            return;
        }

        // Create and add the new appointment
        int newAppointmentID = appointments.stream()
                .mapToInt(Appointment::getAppointmentID)
                .max()
                .orElse(0) + 1;  // Generate new appointment ID

        Appointment newAppointment = new Appointment(newAppointmentID, selectedDoctor, selectedPatient, appointmentDate, false);
        appointments.add(newAppointment);

        System.out.println("Appointment reserved successfully!");
    }

    private static void showAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
        } else {
            System.out.println("Appointments:");
            for (Appointment appointment : appointments) {
                appointment.printAppointmentDetails();
            }
        }
    }

    private static void cancelAppointment() {
        Scanner scanner = new Scanner(System.in);
        showAppointments();  // Show all appointments to select from
        System.out.print("Enter Appointment ID to cancel: ");
        int appointmentID = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Appointment appointmentToCancel = appointments.stream()
                .filter(appointment -> appointment.getAppointmentID() == appointmentID)
                .findFirst()
                .orElse(null);

        if (appointmentToCancel != null) {
            appointments.remove(appointmentToCancel);
            System.out.println("Appointment canceled successfully!");
        } else {
            System.out.println("Appointment not found.");
        }
    }

    private static void markAppointmentAsServed() {
        Scanner scanner = new Scanner(System.in);
        showAppointments();  // Show all appointments to select from
        System.out.print("Enter Appointment ID to mark as served: ");
        int appointmentID = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Appointment appointmentToMark = appointments.stream()
                .filter(appointment -> appointment.getAppointmentID() == appointmentID)
                .findFirst()
                .orElse(null);

        if (appointmentToMark != null) {
            appointmentToMark.setServed(true);
            System.out.println("Appointment marked as served!");
        } else {
            System.out.println("Appointment not found.");
        }
    }

    private static void addDefaultDoctors() {
        doctors.add(new ENT(1, "Asif"));
        doctors.add(new Gastrology(2, "Abrar"));
        doctors.add(new Medicine(3, "Sayim"));
    }
}
