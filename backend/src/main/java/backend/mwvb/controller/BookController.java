package backend.mwvb.controller;

import backend.mwvb.entity.*;
import backend.mwvb.exception.IllegalRequestException;
import backend.mwvb.service.BookService;
import backend.mwvb.util.Response;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService service) {
        bookService = service;
    }

    private void validateUnitIndex(Integer unitIndex) throws IllegalRequestException {

        if (unitIndex < 1 || unitIndex > Unit.UNIT_COUNT) {
            throw new IllegalRequestException(
                    "unit index should be less or equal than "
                            + Unit.UNIT_COUNT
                            + " and greater than 0"
            );
        }
    }

    @GetMapping("/intro")
    @ApiResponse(content = @Content(mediaType = "application/json"))
    public Response<TheIntro> intro() {
        return Response.success(bookService.intro());
    }

    @GetMapping("/roots")
    @ApiResponse(content = @Content(mediaType = "application/json"))
    public Response<List<List<Root>>> allRoots() {
        List<List<Root>> allRoots = bookService.allRoots();
        return Response.success(allRoots);
    }

    @GetMapping("/roots/{unitIndex}")
    @ApiResponse(content = @Content(mediaType = "application/json"))

    public Response<List<Root>> rootsInUnit(@Parameter(description = "should be in range [1,30]", required = true)
                                            @PathVariable("unitIndex") Integer unitIndex) throws IllegalRequestException {
        validateUnitIndex(unitIndex);
        List<Root> roots = bookService.rootsInUnit(unitIndex);
        return Response.success(roots);
    }

    @GetMapping("/words/{rootId}")
    @ApiResponse(content = @Content(mediaType = "application/json"))
    public Response<List<Word>> wordsInRoot(@PathVariable("rootId") Integer rootId) {
        List<Word> words = bookService.wordsInRoot(rootId);
        return Response.success(words);
    }

    @GetMapping("/quizzes/{unitIndex}")
    @ApiResponse(content = @Content(mediaType = "application/json"))
    @Min(1)
    @Max(Unit.UNIT_COUNT)
    public Response<List<Quiz>> quizzesInUnit(@Parameter(description = "should be in range [1,30]", required = true)
                                              @PathVariable("unitIndex") Integer unitIndex) throws IllegalRequestException {
        validateUnitIndex(unitIndex);
        List<Quiz> quizzes = bookService.quizzesInUnit(unitIndex);
        return Response.success(quizzes);
    }

    @GetMapping("/quizzes")
    @ApiResponse(content = @Content(mediaType = "application/json"))
    public Response<List<List<Quiz>>> allQuizzes() {
        List<List<Quiz>> allQuizzes = bookService.allQuizzes();
        return Response.success(allQuizzes);
    }

    @GetMapping("/units/{unitIndex}")
    @Min(1)
    @Max(Unit.UNIT_COUNT)
    @ApiResponse(content = @Content(mediaType = "application/json"))
    public Response<Unit> unit(@Parameter(description = "should be in range [1,30]", required = true)
                               @PathVariable("unitIndex") Integer unitIndex) throws IllegalRequestException {
        validateUnitIndex(unitIndex);
        Unit unit = bookService.unit(unitIndex);
        return Response.success(unit);
    }
}
