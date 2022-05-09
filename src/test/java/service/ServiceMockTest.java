package service;

import domain.Homework;
import domain.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

public class ServiceMockTest {

    @Mock
    StudentXMLRepository studentXMLRepository;
    @Mock
    HomeworkXMLRepository homeworkXMLRepository;
    @Mock
    GradeXMLRepository gradeXMLRepository;

    Service service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new Service(studentXMLRepository, homeworkXMLRepository, gradeXMLRepository);
    }

    @Test
    public void testSaveStudent() {
        String id = "1";
        String name = "name";
        int group = 23;

        Student student = new Student(id, name, group);
        Mockito.when(studentXMLRepository.save(student)).thenReturn(student);

        Assert.assertEquals(1, service.saveStudent(id, name, group));
    }

    @Test
    public void testHomeworkExtendDeadline() {
        String id = Mockito.anyString();
        Homework homework = new Homework(id, "description", 2, 5);

        Mockito.when(homeworkXMLRepository.findOne(id)).thenReturn(homework);
        Mockito.when(homeworkXMLRepository.update(homework)).thenReturn(homework);

        boolean succeeded = service.extendDeadline(id, 2) == 1;
        Assert.assertFalse(succeeded);
    }

    @Test
    public void testStudentDeletion() {
        String id = "2";
        service.deleteStudent(id);
        Mockito.verify(studentXMLRepository).delete(id);
    }
}
