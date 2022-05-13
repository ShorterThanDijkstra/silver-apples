package backend.mwvb.controller;

import backend.mwvb.entity.*;
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

    @GetMapping("/intro")
    public Response<TheIntro>  intro(){
        return Response.success(bookService.intro());
    }
    @GetMapping("/roots")
    public Response<List<List<Root>>> allRoots() {
        List<List<Root>> allRoots = bookService.allRoots();
        return Response.success(allRoots);
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

    @GetMapping("/quizzes/{unitIndex}")
    public Response<List<Quiz>> quizzesInUnit(@PathVariable("unitIndex") Integer unitIndex) {
        List<Quiz> quizzes = bookService.quizzesInUnit(unitIndex);
        return Response.success(quizzes);
    }

    @GetMapping("/quizzes")
    public Response<List<List<Quiz>>> allQuizzes() {
        List<List<Quiz>> allQuizzes = bookService.allQuizzes();
        return Response.success(allQuizzes);
    }

    @GetMapping("/units/{unitIndex}")
    public Response<Unit> unit(@PathVariable("unitIndex") Integer unitIndex) {
        Unit unit = bookService.unit(unitIndex);
        return Response.success(unit);
    }
}
