package com.github.maerd_zinbiel.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest(classes = {BookProcessApplication.class})
class WebApplicationTests {

    @Test
    void book2Database() throws IOException {
        BookProcessApplication.main(new String[]{});
    }

}
