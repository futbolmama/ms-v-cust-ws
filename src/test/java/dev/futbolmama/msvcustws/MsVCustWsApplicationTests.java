package dev.futbolmama.msvcustws;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class MsVCustWsApplicationTests {

    @Test
    void contextLoads() {
    }

}
