package backend.mwvb.test_util

import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.RandomUtils

fun randomSentence(): String? {
    val wordCount = RandomUtils.nextInt(2, 15)
    val sb = StringBuilder()
    for (i in 0 until wordCount) {
        sb.append(RandomStringUtils.randomAlphanumeric(2, 6))
    }
    return sb.toString()
}