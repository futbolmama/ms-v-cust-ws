package dev.futbolmama.msvcustws;

import org.springframework.boot.SpringApplication;

public class TestMsVCustWsApplication {

    public static void main(String[] args) {
        SpringApplication.from(MsVCustWsApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
