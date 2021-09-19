package ru.ytis.issue

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

abstract class CustomField(private val name: String, private val type: String) {
    fun toPostJson(): JsonNode =
        ObjectMapper().createObjectNode()
            .put("name", name)
            .put("\$type", type)
            .set("value", value())

    abstract fun value(): JsonNode
}

data class Assignee(private val login: String)
    : CustomField("Assignee", "SingleUserIssueCustomField") {
    override fun value(): JsonNode =
        ObjectMapper().createObjectNode()
            .put("login", login)
}

data class Priority(private val valueName: String)
    : CustomField("Priority", "SingleEnumIssueCustomField") {
    override fun value(): JsonNode =
        ObjectMapper().createObjectNode()
            .put("name", valueName)
}

data class IssueType(private val valueName: String)
    : CustomField("Type", "SingleEnumIssueCustomField") {
    override fun value(): JsonNode =
        ObjectMapper().createObjectNode()
            .put("name", valueName)
}
