package enrolment;
import java.util.ArrayList;
import java.util.List;

public class CourseOffering {

    private Course course;
    private String term;
    private ArrayList<Session> sessions;
    private ArrayList<Enrolment> enrolments;

    public CourseOffering(Course course, String term) {
        this.course = course;
        this.term = term;
        this.sessions = new ArrayList<>();
        this.enrolments = new ArrayList<>();
        this.course.addOffering(this);
    }

    public void addSession(Session session) {
        if (!sessions.contains(session)) sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public Course getCourse() {
        return course;
    }

    public String getTerm() {
        return term;
    }

    public void enrolStudent(Student student) {
        if (!student.hasCompleted(course.getPrereqs())) return;

        Enrolment newEnrolment = new Enrolment(this, student);
        student.addEnrolment(newEnrolment);
        enrolments.add(newEnrolment);
    }

    public boolean hasStudent(Student student) {
        for (Enrolment enrolment : this.enrolments) {
            if (enrolment.getStudent().equals(student)) return true;
        }
        return false;
    }
}
