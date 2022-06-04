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

public class SessionTest {
    @Test
    public void testSession() {
        Lecture lecture1 = new Lecture("Ainsworth", DayOfWeek.MONDAY, LocalTime.of(15, 30), LocalTime.of(17, 30), "Ashesh Mahidadia");
        
        assertEquals(DayOfWeek.MONDAY, lecture1.getDay());
        assertEquals(LocalTime.of(15, 30), lecture1.getStart());
        assertEquals(LocalTime.of(17, 30), lecture1.getEnd());
        assertEquals("Ashesh Mahidadia", lecture1.getLecturer());

        Tutorial tutorial1 = new Tutorial("Electrical Building", DayOfWeek.TUESDAY, LocalTime.of(11, 00), LocalTime.of(13, 00), "Simon Haddad");
        Lab lab1 = new Lab("J17", DayOfWeek.WEDNESDAY, LocalTime.of(17, 15), LocalTime.of(20, 15), "Braedon Wooding", "John Doe");

        assertEquals("Simon Haddad", tutorial1.getTutor());
        assertEquals("Braedon Wooding", lab1.getTutor());
        assertEquals("John Doe", lab1.getLabAssistant());

        lab1.setTutor("new Tutor");
        lab1.setLabAssistant("new Lab Assistant");

        assertEquals("new Tutor", lab1.getTutor());
        assertEquals("new Lab Assistant", lab1.getLabAssistant());

        assertEquals("J17", lab1.getLocation());
    }
}
