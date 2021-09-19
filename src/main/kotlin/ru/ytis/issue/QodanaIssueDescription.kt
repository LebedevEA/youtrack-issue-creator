package ru.ytis.issue

import ru.ytis.Project

class QodanaIssueDescription private constructor(
    private val language: String,
    private val location: String,
    private val category: String,
    private val type: String,
    private val comment: String?
) {
    override fun toString(): String {
        return when (comment) {
            null -> toStringBase()
            else -> toStringBase() + "\n* Comment: $comment"
        }
    }

    private fun toStringBase(): String {
        return """
            * Language: $language
            * Location: `$location`
            * Qodana category: $category
            * Qodana type: $type
            """.trimIndent()
    }

    class Builder {
        private var language: String? = null
        private var location: String? = null
        private var category: String? = null
        private var type: String? = null
        private var comment: String? = null

        fun language(language: String) = apply { this.language = language }
        fun location(location: String) = apply { this.location = location }
        fun category(category: String) = apply { this.category = category }
        fun type(type: String) = apply { this.type = type }
        fun comment(comment: String?) = apply { this.comment = comment }

        fun build(): QodanaIssueDescription {
            return QodanaIssueDescription(
                checkNotNull(language) { "language == null" },
                checkNotNull(location) { "location == null" },
                checkNotNull(category) { "category == null" },
                checkNotNull(type) { "type == null" },
                comment
            )
        }
    }
}
