package backend.mwvb.service.impl;

import backend.mwvb.entity.Root;
import backend.mwvb.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Test
    void rootsInUnit() {
        List<Root> roots = bookService.rootsInUnit(15);
        assertThat(roots.size(), equalTo(Root.ROOTS_IN_UNIT_COUNT));
    }
}