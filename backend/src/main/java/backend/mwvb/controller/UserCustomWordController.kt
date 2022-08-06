package backend.mwvb.controller

import backend.mwvb.entity.UserCustomWord
import backend.mwvb.service.UserCustomWordService
import backend.mwvb.util.Response
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin
@RequestMapping("/api/v1.0/user/custom-word")
class UserCustomWordController(private val userCustomWordService: UserCustomWordService) {
    @PostMapping("/")
    fun addWord(@RequestBody userCustomWord: UserCustomWord): Response<String> {
        userCustomWordService.add(userCustomWord)
        return Response.success("上传成功")
    }

    @GetMapping("/")
    fun words(): Response<List<UserCustomWord>> {
        val words = userCustomWordService.customWordsOfCurrentUser()
        return Response.success(words)
    }

    @DeleteMapping("/{wordId}")
    fun deleteWordById(@PathVariable(value = "wordId") wordId: Int): Response<String> {
        userCustomWordService.deleteCustomWordOffCurrent(wordId)
        return Response.success("删除成功")
    }
}