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

public class CourseTest {
    @Test
    public void testCreateCourse() {
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals");
        assertEquals("COMP1511", comp1511.getCourseCode());
        assertEquals("Programming Fundamentals", comp1511.getTitle());
    }

    @Test
    public void testAddUOC() {
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals");
        comp1511.setUOC(6);
        assertEquals(6, comp1511.getUOC());
    }

    @Test
    public void testcoursePrereqs() {
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals");
        Course comp1531 = new Course("COMP1531", "Software Engineering Fundamentals");
        comp1531.addPrereq(comp1511);
        Course comp2521 = new Course("COMP2521", "Data Structures and Algorithms");
        comp2521.addPrereq(comp1511);
        comp2521.addPrereq(comp1531);

        assertEquals(1, comp1531.getPrereqs().size());
        assertEquals(2, comp2521.getPrereqs().size());
    }
}
