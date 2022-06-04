package enrolment;
import java.util.ArrayList;

public class Enrolment {

    private CourseOffering offering;
    private Grade grade;
    private Student student;
    private ArrayList<Session> sessions;

    public Enrolment(CourseOffering offering, Student student) {
        this.offering = offering;
        this.student = student;
        this.sessions = new ArrayList<Session>();
        this.grade = new Grade(0, "LE");
    }

    public Course getCourse() {
        return offering.getCourse();
    }

    public Student getStudent() {
        return student;
    }

    public CourseOffering getCourseOffering() {
        return offering;
    }

    public String getTerm() {
        return offering.getTerm();
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Grade getGrade() {
        return grade;
    }

    public boolean hasPassed() {
        return grade.isPassingMark();
    }

    public void addSession(Session newSession) {
        // only add new session if a session of that type has not been added yet
        boolean containsLecture = false;
        boolean containsTutorial = false;
        boolean containsLab = false;
        for (Session session : this.sessions) {
            if (session.getClass().equals(Lecture.class)) {
                containsLecture = true;
            } else if (session.getClass().equals(Tutorial.class)) {
                containsTutorial = true;
            } else if (session.getClass().equals(Lab.class)) {
                containsLab = true;
            }
        }
        if (newSession.getClass().equals(Lecture.class) && containsLecture == true) {
            return;
        } else if (newSession.getClass().equals(Tutorial.class) && containsTutorial == true) {
            return;
        } else if (newSession.getClass().equals(Lab.class) && containsLab == true) {
            return;
        }

        if (offering.getSessions().contains(newSession)) sessions.add(newSession);
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public boolean checkSessionsFulfilled() {
        boolean containsLecture = false;
        boolean containsTutorial = false;
        boolean containsLab = false;
        for (Session session : this.sessions) {
            if (session.getClass().equals(Lecture.class)) {
                containsLecture = true;
            } else if (session.getClass().equals(Tutorial.class)) {
                containsTutorial = true;
            } else if (session.getClass().equals(Lab.class)) {
                containsLab = true;
            }
        }
        return (containsLecture && containsTutorial && containsLab);
    }

}
