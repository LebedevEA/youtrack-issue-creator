package ru.ytis

import ru.ytis.rest.IssueClient

object Main {
    const val youTrackUrl = "http://lebedev:8080"
    const val token = "perm:YWRtaW4=.NDUtMA==.sLddt8EYifIxJHzZ5rN6NjzLE4915R"
}

fun main() {
    try {
        val youTrackServer = Cli.readYouTrackServer()
        val project = Cli.selectProject(youTrackServer)
        val issue = Cli.readIssue().project(project).build()
        IssueClient(youTrackServer).sendIssue(issue)
    } catch (e: Exception) {
        println(e.message)
    }
}
