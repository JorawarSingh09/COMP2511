package enrolment;

public class Grade {
    private int mark;
    private String grade;

    public Grade(int mark, String grade) {
        this.mark = mark;
        this.grade = grade;
    }

    public boolean isPassingMark() {
        return (mark >= 50);
    }
}
