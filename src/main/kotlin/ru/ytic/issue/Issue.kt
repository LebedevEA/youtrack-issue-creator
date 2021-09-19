package ru.ytic.issue

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import ru.ytic.Project

class Issue private constructor(
    private val project: Project,
    private val summary: String,
    private val description: QodanaIssueDescription,
    private val customFields: List<CustomField>
) {
    fun toPostJson(): String {
        val mapper = ObjectMapper()
        val post = mapper.createObjectNode()
        post.set("project", project.toPostJson())
        post.put("summary", summary)
            .put("description", description.toString())
            .set("customFields", customFields.toPostJson())
        return post.toString()
    }

    private fun List<CustomField>.toPostJson(): ArrayNode {
        val mapper = ObjectMapper()
        val array = mapper.createArrayNode()
        this.forEach { array.add(it.toPostJson()) }
        return array
    }

    override fun toString(): String {
        return "Issue(project=$project, summary='$summary', description=$description, customFields=$customFields)"
    }

    class Builder {
        private var project: Project? = null
        private var summary: String? = null

        private var description = QodanaIssueDescription.Builder()

        private var assignee: Assignee? = null
        private var priority: Priority? = null
        private var issueType: IssueType? = null

        fun project(project: Project) = apply { this.project = project }
        fun summary(summary: String) = apply { this.summary = summary }

        fun language(language: String) = apply { description.language(language) }
        fun location(location: String) = apply { description.location(location) }
        fun category(category: String) = apply { description.category(category) }
        fun qodanaType(type: String) = apply { description.type(type) }
        fun comment(comment: String?) = apply { description.comment(comment) }

        fun assignee(assignee: Assignee) = apply { this.assignee = assignee }
        fun priority(priority: Priority) = apply { this.priority = priority }
        fun issueType(issueType: IssueType) = apply { this.issueType = issueType }

        fun build(): Issue {
            return Issue(
                checkNotNull(project) { "project == null" },
                checkNotNull(summary) { "summary == null" },
                description.build(),
                listOfNotNull(assignee, priority, issueType)
            )
        }
    }
}
