package staff;

/**
 * A staff member
 * @author Robert Clifton-Everest
 *
 */
public class StaffMember {
    private String name;
    private double salary;
    private String hireDate;
    private String endDate;

    /**
     * 
     * @param name
     * @param salary
     * @param hireDate
     * @param endDate
     */
    public StaffMember(String name, double salary, String hireDate, String endDate) {
        this.name = name;
        this.salary = salary;
        this.hireDate = hireDate;
        this.endDate = endDate;
    }
    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * 
     * @param salary
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * 
     * @return hireDate
     */
    public String getHireDate() {
        return hireDate;
    }

    /**
     * 
     * @return endDate
     */
    public String getEndDate() {
        return endDate;
    }

    @Override
    /**
     * formats the staffmember data as a string
     */
    public String toString() {
        return "Name: " + name + "\nSalary: " + salary + "\nHire Date: " + hireDate + "\nEnd Date: " + endDate + "\n";
    }

    @Override
    /**
     * compare an object to the instance
     */
    public boolean equals(Object object) {

        if (object instanceof StaffMember == false) return false;

        StaffMember comparison = (StaffMember) object;

        if (this.name == comparison.getName() &&
            this.salary == comparison.getSalary() &&
            this.hireDate == comparison.getHireDate() &&
            this.endDate == comparison.getEndDate()) return true;

        return false;
    }
}
