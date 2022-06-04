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

public class StudentTest {
    @Test
    public void testCreateStudent() {
        Student newStudent = new Student("z5312096");
        assertEquals("z5312096", newStudent.getZID());
    }   

    @Test
    public void testStudentEnrolments() {
        Student newStudent = new Student("z5312096");
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals");
        Course comp1531 = new Course("COMP1531", "Software Engineering Fundamentals");
        comp1531.addPrereq(comp1511);

        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");
        CourseOffering comp1531Offering = new CourseOffering(comp1531, "19T1");

        comp1511Offering.enrolStudent(newStudent);
        assertTrue(newStudent.isEnrolledIn(comp1511Offering));
        comp1531Offering.enrolStudent(newStudent);
        assertTrue(!newStudent.isEnrolledIn(comp1531Offering));
    }

    @Test
    public void testSessionRequirements() {
        Student newStudent = new Student("z5312096");
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals");

        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");

        Lecture lecture1 = new Lecture("Ainsworth", DayOfWeek.MONDAY, LocalTime.of(15, 30), LocalTime.of(17, 30), "Ashesh Mahidadia");
        Tutorial tutorial1 = new Tutorial("Electrical Building", DayOfWeek.TUESDAY, LocalTime.of(11, 00), LocalTime.of(13, 00), "Simon Haddad");
        Lab lab1 = new Lab("J17", DayOfWeek.WEDNESDAY, LocalTime.of(17, 15), LocalTime.of(20, 15), "Braedon Wooding", "John Doe");

        comp1511Offering.addSession(lecture1);
        comp1511Offering.addSession(tutorial1);
        comp1511Offering.addSession(lab1);

        Grade passingGrade = new Grade(50, "PS");
        newStudent.giveCourseGrade(passingGrade, comp1511Offering);

        assertEquals(null, newStudent.getCourseGrade(comp1511Offering));
    }

    @Test
    public void testMeetsMultiplePrereqs() {
        Student newStudent = new Student("z5312096");
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals");
        Course comp1531 = new Course("COMP1531", "Software Engineering Fundamentals");
        comp1531.addPrereq(comp1511);
        Course comp2521 = new Course("COMP2521", "Data Structures and Algorithms");
        comp2521.addPrereq(comp1511);
        comp2521.addPrereq(comp1531);

        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");
        CourseOffering comp1531Offering = new CourseOffering(comp1531, "19T1");
        CourseOffering comp2521Offering = new CourseOffering(comp2521, "19T2");

        Lecture lecture1 = new Lecture("Ainsworth", DayOfWeek.MONDAY, LocalTime.of(15, 30), LocalTime.of(17, 30), "Ashesh Mahidadia");
        Tutorial tutorial1 = new Tutorial("Electrical Building", DayOfWeek.TUESDAY, LocalTime.of(11, 00), LocalTime.of(13, 00), "Simon Haddad");
        Lab lab1 = new Lab("J17", DayOfWeek.WEDNESDAY, LocalTime.of(17, 15), LocalTime.of(20, 15), "Braedon Wooding", "John Doe");

        comp1511Offering.addSession(lecture1);
        comp1511Offering.addSession(tutorial1);
        comp1511Offering.addSession(lab1);

        comp1531Offering.addSession(lecture1);
        comp1531Offering.addSession(tutorial1);
        comp1531Offering.addSession(lab1);

        comp1511Offering.enrolStudent(newStudent);
        newStudent.addEnrolmentSession(comp1511Offering, lecture1);
        newStudent.addEnrolmentSession(comp1511Offering, tutorial1);
        newStudent.addEnrolmentSession(comp1511Offering, lab1);

        Grade passingGrade = new Grade(50, "PS");
        newStudent.giveCourseGrade(passingGrade, comp1511Offering);

        assertEquals(passingGrade, newStudent.getCourseGrade(comp1511Offering));

        comp1531Offering.enrolStudent(newStudent);
        newStudent.addEnrolmentSession(comp1531Offering, lecture1);
        newStudent.addEnrolmentSession(comp1531Offering, tutorial1);
        newStudent.addEnrolmentSession(comp1531Offering, lab1);

        newStudent.giveCourseGrade(passingGrade, comp1531Offering);

        comp2521Offering.enrolStudent(newStudent);
        assertTrue(newStudent.isEnrolledIn(comp2521Offering));
    }

    @Test
    public void testFailedSomePrereqs() {
        Student newStudent = new Student("z5312096");
        Course comp1511 = new Course("COMP1511", "Programming Fundamentals");
        Course comp1531 = new Course("COMP1531", "Software Engineering Fundamentals");
        comp1531.addPrereq(comp1511);
        Course comp2521 = new Course("COMP2521", "Data Structures and Algorithms");
        comp2521.addPrereq(comp1511);
        comp2521.addPrereq(comp1531);

        CourseOffering comp1511Offering = new CourseOffering(comp1511, "19T1");
        CourseOffering comp1531Offering = new CourseOffering(comp1531, "19T1");
        CourseOffering comp2521Offering = new CourseOffering(comp2521, "19T2");

        Lecture lecture1 = new Lecture("Ainsworth", DayOfWeek.MONDAY, LocalTime.of(15, 30), LocalTime.of(17, 30), "Ashesh Mahidadia");
        Tutorial tutorial1 = new Tutorial("Electrical Building", DayOfWeek.TUESDAY, LocalTime.of(11, 00), LocalTime.of(13, 00), "Simon Haddad");
        Lab lab1 = new Lab("J17", DayOfWeek.WEDNESDAY, LocalTime.of(17, 15), LocalTime.of(20, 15), "Braedon Wooding", "John Doe");

        comp1511Offering.addSession(lecture1);
        comp1511Offering.addSession(tutorial1);
        comp1511Offering.addSession(lab1);

        comp1531Offering.addSession(lecture1);
        comp1531Offering.addSession(tutorial1);
        comp1531Offering.addSession(lab1);

        comp1511Offering.enrolStudent(newStudent);
        newStudent.addEnrolmentSession(comp1511Offering, lecture1);
        newStudent.addEnrolmentSession(comp1511Offering, tutorial1);
        newStudent.addEnrolmentSession(comp1511Offering, lab1);

        Grade passingGrade = new Grade(50, "PS");
        newStudent.giveCourseGrade(passingGrade, comp1511Offering);

        comp1531Offering.enrolStudent(newStudent);
        newStudent.addEnrolmentSession(comp1531Offering, lecture1);
        newStudent.addEnrolmentSession(comp1531Offering, tutorial1);
        newStudent.addEnrolmentSession(comp1531Offering, lab1);

        Grade failingGrade = new Grade(49, "FL");
        newStudent.giveCourseGrade(failingGrade, comp1531Offering);

        comp2521Offering.enrolStudent(newStudent);
        assertTrue(!newStudent.isEnrolledIn(comp2521Offering));
    }
}
