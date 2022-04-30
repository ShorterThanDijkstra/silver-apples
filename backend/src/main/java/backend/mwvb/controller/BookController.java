package backend.mwvb.controller;

import backend.mwvb.entity.Root;
import backend.mwvb.service.BookService;
import backend.mwvb.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService service) {
        bookService = service;
    }

    @GetMapping("/roots/{index}")
    public Response<List<Root>> rootsInUnit(@PathVariable("index") Integer index) {
        List<Root> names = bookService.rootsInUnit(index);
        return Response.success(names);
    }
}
