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

public class CourseOfferingTest {
    @Test
    public void testCreateCourseOffering() {
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals");
        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");
        comp1511.addOffering(comp1511Offering);
        assertEquals(true, comp1511.hasOfferingInTerm("19T1"));
    }

    @Test 
    public void testRemoveCourseOffering() {
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals"); 
        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");
        comp1511.addOffering(comp1511Offering);
        assertTrue(comp1511.hasOfferingInTerm("19T1"));

        comp1511.removeOffering(comp1511Offering);
        assertTrue(!comp1511.hasOfferingInTerm("19T1"));
    }

    @Test
    public void testAddDuplicateCourseOfferings() {
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals"); 
        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");
        comp1511.addOffering(comp1511Offering);
        comp1511.addOffering(comp1511Offering);

        assertEquals(1, comp1511.getAllOfferings().size());
    }

    @Test
    public void testSessions() {
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals"); 
        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");
        comp1511.addOffering(comp1511Offering);

        Lecture lecture1 = new Lecture("Ainsworth", DayOfWeek.MONDAY, LocalTime.of(15, 30), LocalTime.of(17, 30), "Ashesh Mahidadia");
        Tutorial tutorial1 = new Tutorial("Electrical Building", DayOfWeek.TUESDAY, LocalTime.of(11, 00), LocalTime.of(13, 00), "Simon Haddad");
        Lab lab1 = new Lab("J17", DayOfWeek.WEDNESDAY, LocalTime.of(17, 15), LocalTime.of(20, 15), "Braedon Wooding", "John Doe");

        comp1511Offering.addSession(lecture1);
        comp1511Offering.addSession(tutorial1);
        comp1511Offering.addSession(lab1);

        assertEquals(3, comp1511Offering.getSessions().size());

        comp1511Offering.removeSession(lecture1);

        assertEquals(2, comp1511Offering.getSessions().size());
    }

    @Test 
    public void testAddDuplicateSessions() {
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals"); 
        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");
        comp1511.addOffering(comp1511Offering);

        Lecture lecture1 = new Lecture("Ainsworth", DayOfWeek.MONDAY, LocalTime.of(15, 30), LocalTime.of(17, 30), "Ashesh Mahidadia");
        comp1511Offering.addSession(lecture1);
        comp1511Offering.addSession(lecture1);

        assertEquals(1, comp1511Offering.getSessions().size());
    }

    @Test 
    public void testEnrolStudent() {
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals"); 
        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");
        comp1511.addOffering(comp1511Offering);

        Student student1 = new Student("z3141592");
        comp1511Offering.enrolStudent(student1);
        assertTrue(comp1511Offering.hasStudent(student1));
    }
}
