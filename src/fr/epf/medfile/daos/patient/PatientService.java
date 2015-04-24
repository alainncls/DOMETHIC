package fr.epf.medfile.daos.patient;

import java.util.List;

import fr.epf.medfile.models.Patient;

public interface PatientService {
    long addPatient(int iD, String firstName, String lastName, String socialSecurity, String birthDate, String sex, String address, String city, String zipCode, String email, String phoneNumber, String entryDate, String causes, String service, String healthInfos, String birthAddress);
    List<Patient> getPatients();
    List<Patient> getPatients(String service);
    Patient getPatient(int id);
    boolean removePatient(int id);
    boolean clearPatients();
    boolean isEmpty();
}
