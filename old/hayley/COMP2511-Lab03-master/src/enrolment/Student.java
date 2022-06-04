package enrolment;
import java.util.*;

public class Student {

    private String zid;
    private ArrayList<Enrolment> enrolments;

	public Student(String zid) {
        this.zid = zid;
        enrolments = new ArrayList<>();
    }

    public boolean hasCompleted(List<Course> prereqs) {
        ArrayList<Course> completedPrereqs = new ArrayList<Course>();
        for (Enrolment enrolment : this.enrolments) {
            if (prereqs.contains(enrolment.getCourse()) && enrolment.hasPassed()) {
                completedPrereqs.add(enrolment.getCourse());
            }
        }
        if (completedPrereqs.containsAll(prereqs)) return true;
        else return false;
    }

    public void addEnrolmentSession(CourseOffering course, Session newSession) {
        for (Enrolment enrolment : this.enrolments) {
            if (enrolment.getCourseOffering().equals(course)) {
                // students cannot get their grade if they arent enrolled in the correct sessions
                enrolment.addSession(newSession);
            }
        }
    }

    public void giveCourseGrade(Grade newGrade, CourseOffering course) {
        for (Enrolment enrolment : this.enrolments) {
            if (enrolment.getCourseOffering().equals(course)) {
                // students cannot get their grade if they arent enrolled in the correct sessions
                if (!enrolment.checkSessionsFulfilled()) return;
                enrolment.setGrade(newGrade);
            }
        }
    }

    // testing function
    public Grade getCourseGrade(CourseOffering course) {
        for (Enrolment enrolment : this.enrolments) {
            if (enrolment.getCourseOffering().equals(course)) {
                return enrolment.getGrade();
            }
        }
        return null;
    }

    public boolean isEnrolledIn(CourseOffering course) {
        for (Enrolment enrolment : this.enrolments) {
            if (enrolment.getCourseOffering().equals(course)) {
                return true;
            }
        }
        return false;
    }

    public void addEnrolment(Enrolment newEnrolment) {
        enrolments.add(newEnrolment);
    }

	public String getZID() {
		return zid;
	}
}
