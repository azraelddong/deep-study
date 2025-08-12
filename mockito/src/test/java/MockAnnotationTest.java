import com.example.mockito.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockAnnotationTest {

    @Mock
    private DemoService demoService;

    @Test
    public void test() {
//        when(demoService.getRandom()).thenReturn(100);
//        Assert.assertEquals(10, demoService.getRandom());

//        doThrow(new Exception("测试失败")).when(demoService).getRandom();
//        try {
//            demoService.getRandom();
////            Assert.fail();
//        } catch (Exception e) {
////            e.printStackTrace();
//            Assert.assertEquals("测试失败", e.getMessage());
//        }

    }
}
