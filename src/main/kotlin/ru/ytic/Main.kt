package ru.ytic

import ru.ytic.rest.IssueClient

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
