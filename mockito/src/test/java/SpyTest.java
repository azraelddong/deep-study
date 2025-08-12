import com.example.mockito.DemoDao;
import com.example.mockito.DemoService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class SpyTest {


    @Spy
    private DemoService demoService = new DemoService(new DemoDao());

    @Test
    public void spyTest() {
        MockitoAnnotations.openMocks(this);

//        Mockito.when(demoService.getRandom()).thenReturn(100);
        Mockito.doReturn(100).when(demoService).getRandom();

        Assert.assertEquals(100, demoService.getRandom());
        System.out.println("=============> 执行成功");
    }
}
