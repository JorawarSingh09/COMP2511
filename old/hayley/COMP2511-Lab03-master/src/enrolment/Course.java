package enrolment;
import java.util.ArrayList;
import java.util.List;

/**
 * A course in the enrolment system.
 * @author Robert Clifton-Everest
 *
 */
public class Course {

    private String courseCode;
    private String title;
    private int uoc;
    private List<Course> prereqs;
    private ArrayList<CourseOffering> courseOfferings;


    public Course(String courseCode, String title) {
        this.title = title;
        this.courseCode = courseCode;
        this.prereqs = new ArrayList<Course>();
        this.courseOfferings = new ArrayList<CourseOffering>();
        this.uoc = 0;
    }

    public List<Course> getPrereqs() {
        return prereqs;
    }

    public void addPrereq(Course course) {
        prereqs.add(course);
    }

    public void addOffering(CourseOffering offering) {
        if (this.hasOfferingInTerm(offering.getTerm())) return;
        courseOfferings.add(offering);
    }

    public ArrayList<CourseOffering> getAllOfferings() {
        return courseOfferings;
    }

    public void removeOffering(CourseOffering offering) {
        courseOfferings.remove(offering);
    }

    public boolean hasOfferingInTerm(String term) {
        for (CourseOffering offering : courseOfferings) {
            if (offering.getTerm().equals(term)) return true;
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setUOC(int uoc) {
        this.uoc = uoc;
    }

    public int getUOC() {
        return uoc;
    }

}
