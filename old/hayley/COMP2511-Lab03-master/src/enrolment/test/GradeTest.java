package enrolment.test;

import enrolment.Course;
import enrolment.CourseOffering;
import enrolment.Lecture;
import enrolment.Tutorial;
import enrolment.Lab;
import enrolment.Student;
import enrolment.Grade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class GradeTest {
    @Test
    public void testCreateGrade() {
        Grade newGrade = new Grade(50, "PS");
        assertTrue(newGrade.isPassingMark());
    }

    @Test
    public void testFailingGrade() {
        Grade failingGrade = new Grade(49, "FL");
        assertTrue(!failingGrade.isPassingMark());
    }
}
