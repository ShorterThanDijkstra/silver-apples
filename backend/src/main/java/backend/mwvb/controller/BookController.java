package backend.mwvb.controller;

import backend.mwvb.entity.Root;
import backend.mwvb.entity.Word;
import backend.mwvb.service.BookService;
import backend.mwvb.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
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
        List<Root> roots = bookService.rootsInUnit(index);
        return Response.success(roots);
    }

    @GetMapping("/words/{rootId}")
    public Response<List<Word>> wordsInRoot(@PathVariable("rootId") Integer rootId) {
        List<Word> words = bookService.wordsInRoot(rootId);
        return Response.success(words);
    }
}
