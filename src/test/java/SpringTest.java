import Action.StudentManage;
import Service.Inteface.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringTest {

    @Test
    public void instanceSpring() {
        AbstractApplicationContext ctx = new FileSystemXmlApplicationContext("E:\\workplace\\Java EE\\实验一\\src\\main\\resources\\applicationContext.xml");
        StudentManage studentManage = (StudentManage) ctx.getBean("studentManage");
        StudentService studentService = (StudentService) ctx.getBean("studentServiceImpl");
        System.out.println(studentManage);
        System.out.println(studentService);
        ctx.close();
    }

}
