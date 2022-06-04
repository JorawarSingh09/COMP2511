package enrolment.test;

import enrolment.Course;
import enrolment.CourseOffering;
import enrolment.Lecture;
import enrolment.Tutorial;
import enrolment.Lab;
import enrolment.Student;
import enrolment.Grade;
import enrolment.Enrolment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class EnrolmentTest {

    @Test
    public void testGetTermOfEnrolment() {
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals");
        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");

        Student student1 = new Student("z3141592");

        Enrolment newEnrolment = new Enrolment(comp1511Offering, student1);

        assertEquals("19T1", newEnrolment.getTerm());
    }

    @Test 
    public void testGetGrade() {
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals");
        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");

        Student student1 = new Student("z3141592");

        Enrolment newEnrolment = new Enrolment(comp1511Offering, student1);

        Grade newGrade = new Grade(70, "CR");
        newEnrolment.setGrade(newGrade);

        assertEquals(newGrade, newEnrolment.getGrade());
    }

    @Test
    public void testIntegration() {

        // Create courses
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals");
        Course comp1531 = new Course("COMP1531", "Software Engineering Fundamentals");
        comp1531.addPrereq(comp1511);
        Course comp2521 = new Course("COMP2521", "Data Structures and Algorithms");
        comp2521.addPrereq(comp1511);

        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");
        CourseOffering comp1531Offering = new CourseOffering(comp1531, "19T1");
        CourseOffering comp2521Offering = new CourseOffering(comp2521, "19T2");

        assertEquals("19T1", comp1511Offering.getTerm());
        assertEquals("19T1", comp1531Offering.getTerm());
        assertEquals("19T2", comp2521Offering.getTerm());

        // TODO Create some sessions for the courses
        Lecture lecture1 = new Lecture("Ainsworth", DayOfWeek.MONDAY, LocalTime.of(15, 30), LocalTime.of(17, 30), "Ashesh Mahidadia");
        Tutorial tutorial1 = new Tutorial("Electrical Building", DayOfWeek.TUESDAY, LocalTime.of(11, 00), LocalTime.of(13, 00), "Simon Haddad");
        Lab lab1 = new Lab("J17", DayOfWeek.WEDNESDAY, LocalTime.of(17, 15), LocalTime.of(20, 15), "Braedon Wooding", "John Doe");

        Lecture lecture2 = new Lecture("Ainsworth", DayOfWeek.THURSDAY, LocalTime.of(9, 30), LocalTime.of(11, 30), "Ashesh Mahidadia");
        Tutorial tutorial2 = new Tutorial("Electrical Building", DayOfWeek.FRIDAY, LocalTime.of(14, 00), LocalTime.of(17, 00), "Jane Doe");
        Lab lab2 = new Lab("J17", DayOfWeek.MONDAY, LocalTime.of(12, 15), LocalTime.of(15, 15), "Big Chungus", "Sung Jin Woo");

        comp1511Offering.addSession(lecture1);
        comp1511Offering.addSession(tutorial1);
        comp1511Offering.addSession(lab1);

        comp1531Offering.addSession(lecture2);
        comp1531Offering.addSession(tutorial2);
        comp1531Offering.addSession(lab2);

        comp2521Offering.addSession(lecture1);
        comp2521Offering.addSession(tutorial1);
        comp2521Offering.addSession(lab1);

        // TODO Create a student
        Student student1 = new Student("z3141592");

        // TODO Enrol the student in COMP1511 for T1 (this should succeed)
        comp1511Offering.enrolStudent(student1);
        assertTrue(comp1511Offering.hasStudent(student1));

        // TODO Enrol the student in COMP1531 for T1 (this should fail as they
        // have not met the prereq)
        comp1531Offering.enrolStudent(student1);
        assertTrue(!comp1531Offering.hasStudent(student1));

        // TODO Give the student a passing grade for COMP1511
        Grade passingGrade = new Grade(50, "PS");
        student1.addEnrolmentSession(comp1511Offering, lecture1);
        student1.addEnrolmentSession(comp1511Offering, tutorial1);
        student1.addEnrolmentSession(comp1511Offering, lab1);

        student1.giveCourseGrade(passingGrade, comp1511Offering);

        // TODO Enrol the student in 2521 (this should succeed as they have met
        // the prereqs)
        comp2521Offering.enrolStudent(student1);
        assertTrue(comp2521Offering.hasStudent(student1));
    }

    @Test
    public void testDuplicateSessionTypes() {
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals");
        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");

        Student student1 = new Student("z3141592");

        Lecture lecture1 = new Lecture("Ainsworth", DayOfWeek.MONDAY, LocalTime.of(15, 30), LocalTime.of(17, 30), "Ashesh Mahidadia");
        Tutorial tutorial1 = new Tutorial("Electrical Building", DayOfWeek.TUESDAY, LocalTime.of(11, 00), LocalTime.of(13, 00), "Simon Haddad");
        Lab lab1 = new Lab("J17", DayOfWeek.WEDNESDAY, LocalTime.of(17, 15), LocalTime.of(20, 15), "Braedon Wooding", "John Doe");

        Lecture lecture2 = new Lecture("Ainsworth", DayOfWeek.THURSDAY, LocalTime.of(9, 30), LocalTime.of(11, 30), "Ashesh Mahidadia");
        Tutorial tutorial2 = new Tutorial("Electrical Building", DayOfWeek.FRIDAY, LocalTime.of(14, 00), LocalTime.of(17, 00), "Jane Doe");
        Lab lab2 = new Lab("J17", DayOfWeek.MONDAY, LocalTime.of(12, 15), LocalTime.of(15, 15), "Big Chungus", "Sung Jin Woo");

        comp1511Offering.addSession(lecture1);
        comp1511Offering.addSession(tutorial1);
        comp1511Offering.addSession(lab1);

        comp1511Offering.addSession(lecture2);
        comp1511Offering.addSession(tutorial2);
        comp1511Offering.addSession(lab2);

        Enrolment newEnrolment = new Enrolment(comp1511Offering, student1);

        newEnrolment.addSession(lecture1);
        newEnrolment.addSession(tutorial1);
        newEnrolment.addSession(lab1);

        assertTrue(newEnrolment.checkSessionsFulfilled());

        newEnrolment.addSession(lecture2);
        newEnrolment.addSession(tutorial2);
        newEnrolment.addSession(lab2);

        assertEquals(3, newEnrolment.getSessions().size());
    }
}
