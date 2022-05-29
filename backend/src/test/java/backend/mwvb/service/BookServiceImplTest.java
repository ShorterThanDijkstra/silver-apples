package backend.mwvb.service;

import backend.mwvb.entity.Root;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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