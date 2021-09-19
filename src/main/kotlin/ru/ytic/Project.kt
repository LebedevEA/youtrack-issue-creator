package ru.ytic

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

data class Project(val name: String, val id: String) {
    fun toPostJson(): JsonNode =
        ObjectMapper().createObjectNode()
            .put("id", id)

    companion object {
        fun fromJson(json: JsonNode): Project {
            val name = json.get("name").textValue().trim('"')
            val id = json.get("id").textValue()
            return Project(name, id)
        }
    }
}
