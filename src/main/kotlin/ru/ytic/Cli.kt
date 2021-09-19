package ru.ytic

import ru.ytic.issue.Assignee
import ru.ytic.issue.Issue
import ru.ytic.issue.IssueType
import ru.ytic.issue.Priority
import ru.ytic.rest.ProjectsClient
import ru.ytic.rest.YouTrackServer
import java.io.IOException

object Cli {
    fun readYouTrackServer(): YouTrackServer {
        val url = readField("Enter YouTrack server url: ")
        val token = readField("Enter your permanent token: ")
        return YouTrackServer(url, token)
    }

    fun selectProject(youTrackServer: YouTrackServer): Project {
        val projects = ProjectsClient(youTrackServer).getProjects()
        val id = readField("Enter project's id (known projects are ${
            projects.joinToString(separator = ", ", prefix = "[", postfix = "]")
        }): ")
        return projects.find { it.id == id } ?: run {
            println("Could not find project matching given id, try again")
            return selectProject(youTrackServer)
        }
    }

    fun readIssue(): Issue.Builder {
        val builder = Issue.Builder()

        readField("Enter issue summary: ") { builder.summary(it) }
        readField("Enter problem's language: ") { builder.language(it) }
        readField("Enter problem's location: ") { builder.location(it) }
        readField("Enter problem's category: ") { builder.category(it) }
        readField("Enter problem's type: ") { builder.qodanaType(it) }

        readField("Enter comment (leave blank to send no comment): ") {
            if (it.isNotEmpty()) builder.comment(it)
        }

        readField("Enter issue assignee login (leave blank to keep unassigned): ") {
            if (it.isNotEmpty()) builder.assignee(Assignee(it))
        }

        readField("Enter issue priority (leave blank to keep normal): ") {
            if (it.isNotEmpty()) builder.priority(Priority(it))
        }

        readField("Enter issue type (leave blank to keep as a bug): ") {
            if (it.isNotEmpty()) builder.issueType(IssueType(it))
        }

        return builder
    }

    private fun readField(msg: String, handleInput: (String) -> Unit = {}): String {
        print(msg)
        return readLine()?.also(handleInput) ?: throw IOException("Could not read from console")
    }
}
