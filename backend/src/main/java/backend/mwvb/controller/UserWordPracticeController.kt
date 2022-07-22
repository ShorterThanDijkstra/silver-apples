package backend.mwvb.controller

import backend.mwvb.entity.UserWordPractice
import backend.mwvb.service.UserWordPracticeService
import backend.mwvb.util.Response
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/v1.0/book/word-practice")
class UserWordPracticeController(
    private val userWordPracticeService: UserWordPracticeService
) {
    @PostMapping("/practice")
    fun practice(@RequestBody practice: UserWordPractice): Response<String> {
        userWordPracticeService.add(practice)
        return Response.success("上传成功")
    }

    @GetMapping("/practices/{wordId}")
    fun allPracticesOfWord(@PathVariable("wordId") wordId: String): Response<List<UserWordPractice>> {
        val practices = userWordPracticeService.allPracticesOfWord(wordId.toInt())
        return Response.success(practices)
    }
}