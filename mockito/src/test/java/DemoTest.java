import com.example.mockito.DemoDao;
import com.example.mockito.DemoService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public class DemoTest {

    @Test
    public void demoTest() {
        DemoDao mockDao = mock(DemoDao.class);

//        Mockito.when(mockDao.getRandom()).thenReturn(10);
        Mockito.when(mockDao.getRandom()).thenThrow(new Exception("测试报错了"));

        Assert.assertEquals(10, mockDao.getRandom());

        DemoService demoService = new DemoService(mockDao);
//        Assert.assertEquals(10, demoService.getRandom());
    }
}
