package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

public class ServiceTest {
    static Validator<Student> studentValidator = new StudentValidator();
    static Validator<Homework> homeworkValidator = new HomeworkValidator();
    static Validator<Grade> gradeValidator = new GradeValidator();

    static StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
    static HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
    static GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

    static Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

    @BeforeAll
    static void init() {
        System.out.println("Testing started");

        service.saveStudent("1", "Stud1", 222);
        service.saveStudent("2", "Stud2", 222);
        service.saveStudent("3", "Stud2", 222);
    }

    @Test
    public void testSaveStudent() {
        String id = "4";
        String name = "name";
        int group = 223;

        boolean succeeded = (service.saveStudent(id, name, group) == 1);
        Assertions.assertTrue(succeeded);
    }

    @Test
    public void testFindAllStudents() {
        int count = 0;
        for (Student student : service.findAllStudents()) {
            count++;
        }
        Assertions.assertEquals(4, count);
    }

    @Test
    public void testHomeworkExtendDeadline() {
        String id = "5";
        service.saveHomework(id, "description", 10, 2);

        boolean succeeded = service.extendDeadline(id, 2) == 1;
        Assertions.assertFalse(succeeded);
    }

    @Test
    public void saveGradeTest(){
        Assertions.assertEquals(1, service.saveGrade("3", "1", 9, 7, "ok"));
    }

    @ParameterizedTest
    @ValueSource(strings = { "1", "2", "3", "4" })
    public void testDeleteStudent(String id) {
        Assertions.assertEquals(1, service.deleteStudent(id));
    }
}