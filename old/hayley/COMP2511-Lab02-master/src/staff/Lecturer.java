package staff;

public class Lecturer extends StaffMember {
    private String school;
    private String academicStatus;

    /**
     * 
     * @param name
     * @param salary
     * @param hireDate
     * @param endDate
     */
    public Lecturer(String name, double salary, String hireDate, String endDate) {
        super(name, salary, hireDate, endDate);
    }

    /**
     * 
     * @param name
     * @param salary
     * @param hireDate
     * @param endDate
     * @param school
     * @param academicStatus
     */
    public Lecturer(String name, double salary, String hireDate, String endDate, String school, String academicStatus) {
        this(name, salary, hireDate, endDate);
        this.school = school;
        this.academicStatus = academicStatus;
    }

    /**
     * 
     * @return school
     */
    public String getSchool() {
        return school;
    }

    /**
     * 
     * @return academicStatus
     */
    public String getAcademicStatus() {
        return academicStatus;
    }

    /**
     * 
     * @param school
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * 
     * @param academicStatus
     */
    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }

    @Override
    /**
     * Override toString()
     */
    public String toString() {
        return super.toString() + "School: " + school + "\nAcademic Status: " + academicStatus + "\n";
    }

    @Override
    /**
     * override equals method from staffmember
     */
    public boolean equals(Object object) {
        if (!super.equals(object)) return false;

        if (object instanceof Lecturer == false) return false;

        Lecturer comparison = (Lecturer) object;

        if (this.school == comparison.getSchool() &&
            this.academicStatus == comparison.getAcademicStatus()) return true;

        return false;
    }
}
