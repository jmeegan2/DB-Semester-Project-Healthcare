package Entities_Data;

public class Relationship {
    private String patientId;
    private String doctorId;

    public Relationship(String patientId, String doctorId) {
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}