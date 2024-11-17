import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The Appointment class represents an appointment in the hospital management system.
 * It includes details about the patient, doctor, date and time, status, prescribed medications,
 * and consultation notes.
 */
public class Appointment implements Serializable{
    private static final long serialVersionUID = 1L;
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private LocalDateTime dateTime;
    private String status;
    private Map<Medication, Integer> prescribedMedication;
    private String medicationStatus;
    private String consultationNotes;
    private static Scanner sc = new Scanner(System.in);

    /**
     * Constructor for Appointment.
     *
     * @param appointmentID      The unique ID of the appointment.
     * @param patientID          The ID of the patient.
     * @param doctorID           The ID of the doctor.
     * @param dateTime           The date and time of the appointment.
     * @param comments           Initial comments or notes for the appointment.
     */
    public Appointment(String appointmentID, String patientID, String doctorID, LocalDateTime dateTime, String comments) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.dateTime = dateTime;
        this.status = "Pending";
        this.prescribedMedication = new HashMap<>();
        this.medicationStatus = null;
        this.consultationNotes = null;
        //if (comments != null && !comments.isEmpty()) {
            //this.consultationNotes = comments; // Use consultationNotes for initial comments
        //}
    }
    
    /**
 * Retrieves the unique identifier of the appointment.
 * This ID is used to uniquely distinguish each appointment in the system.
 *
 * @return The unique ID of the appointment as a {@code String}.
 */
    public String getAppointmentID() {
        return appointmentID;
    }

    /** 
     * @return The ID of the doctor assigned to the appointment.
     */
    public String getDoctorID() {
        return doctorID;
    }

    /** 
     * @return The ID of the patient associated with the appointment.
     */
    public String getPatientID(){
        return patientID;
    }

     /** 
     * @return The date and time of the appointment.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /** 
     * @return The current medication status of the appointment.
     */
    public String getMedicationStatus() {
        return medicationStatus;
    }

    /** 
     * @return A map of prescribed medications and their quantities.
     */
    public Map<Medication, Integer> getPrescribedMedication() {
        return this.prescribedMedication;
    }

    /** 
     * @return Consultation notes for the appointment.
     */
    public String getConsultationNotes(){
        return consultationNotes;
    }
    
    /** 
     * Confirms the appointment if it is in a "Pending" state.
     */
    public void confirm() {
        if (!status.equals("Pending")) {
            System.out.println("Only pending appointments can be confirmed.");
            return;
        }
        this.status = "Confirmed";
    }
    
    /** 
     * Cancels the appointment unless it is already completed or canceled.
     */
    public void cancel() {
        if (status.equals("Completed")) {
            System.out.println("Completed appointments cannot be canceled.");
            return;
        }
        if (status.equals("Cancelled")) {
            System.out.println("Appointment is already canceled.");
            return;
        }
        this.status = "Cancelled";
    }
    
    /**
     * Completes the appointment by recording prescribed medications and consultation notes.
     *
     * @param inventory      The inventory instance to fetch medication details.
     * @param appointmentID  The ID of the appointment being completed.
     */
    public void complete(Inventory inventory, String appointmentID) {
        if (!status.equals("Confirmed")) {
            System.out.println("Only confirmed appointments can be completed.");
            return;
        }

        System.out.println("""
                Prescribe Medication for Appointment (if any): 
                1. Yes
                2. No
                Choose options (1-2): """);
        int option = sc.nextInt();
    
        switch (option) {
            case 1:
                inventory.displayInventory(false);
                System.out.println("Medication ID: ");
                String medicationID = sc.next();
                System.out.println("Quantity: ");
                int quantity = sc.nextInt();
                Medication medication = inventory.findMedicationByID(medicationID);
                if (medication == null) {
                    System.out.println("Medication Not Found. Please Try Again.");
                    return;
                }
                this.prescribedMedication.put(medication, quantity);
                this.medicationStatus = "Pending to Dispense";
                break;
            case 2:
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    
        System.out.println("Consultation Notes for Appointment (if any): ");
        sc.nextLine();
        this.consultationNotes = sc.nextLine();
        this.status = "Completed";
        System.out.println("Outcome recorded successfully for Appointment ID: " + appointmentID);
    }
    
    /** 
     * Marks the medication as dispensed.
     */
    public void completeDispense() {
        this.medicationStatus = "Dispense Complete";
    }

    /** 
     * @return The current status of the appointment.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Updates the date and time of the appointment if it is pending or confirmed.
     *
     * @param newDateTime The new date and time for the appointment.
     */
    public void updateDateTime(LocalDateTime newDateTime) {
        if (!status.equals("Pending") && !status.equals("Confirmed")) {
            System.out.println("Only pending or confirmed appointments can be rescheduled.");
            return;
        }
        System.out.println("Updating appointment date and time from " + this.dateTime + " to " + newDateTime);
        this.dateTime = newDateTime;
    }
    
    /** 
     * Provides a string representation of the appointment with all details.
     *
     * @return A string representation of the appointment.
     */
    @Override
    public String toString() {
        return "Appointment [Appointment ID=" + appointmentID 
            + ", Patient ID=" + patientID 
            + ", Doctor ID=" + doctorID
            + ", Date and Time=" + dateTime 
            + ", Status=" + status 
            + (medicationStatus != null ? ", Medication Status=" + medicationStatus : "")
            + (consultationNotes != null ? ", Consultation Notes=" + consultationNotes : "")
            + "]";
    }
}
