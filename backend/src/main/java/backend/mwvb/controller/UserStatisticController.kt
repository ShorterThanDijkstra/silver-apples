package backend.mwvb.controller

import backend.mwvb.entity.UserWordActivity
import backend.mwvb.entity.UserWordPractice
import backend.mwvb.service.UserStatisticService
import backend.mwvb.util.Response
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/v1.0/user-statistic")
class UserStatisticController(
    private val userStatisticService: UserStatisticService
) {
    @GetMapping("/word-activities/{username}")
    fun wordActivities(@PathVariable("username") username: String): Response<List<UserWordActivity>> {
        val result = userStatisticService.wordActivities(username)
        return Response.success(result)
    }
    @GetMapping("/words/{username}")
    fun wordPracticesOfUser(@PathVariable username: String):Response<List<UserWordPractice>>{
        val result = userStatisticService.wordPracticesOfUser(username)
        return Response.success(result)
    }
}