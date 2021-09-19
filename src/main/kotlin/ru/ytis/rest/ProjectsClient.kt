package ru.ytis.rest

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.ytis.Project

class ProjectsClient(private val youTrackServer: YouTrackServer) {
    private val client = OkHttpClient()
    private val request: Request
        get() {
            return Request.Builder()
                .url(youTrackServer.availableProjectsUrl)
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer ${youTrackServer.token}")
                .addHeader("Content-Type", "application/json")
                .get()
                .build()
        }

    fun getProjects(): List<Project> {
        val list = ArrayList<Project>()
        ObjectMapper().readTree(getProjectsJson()).forEach {
            list += Project.fromJson(it)
        }
        return list
    }

    private fun getProjectsJson(): String {
        client.newCall(request).execute().use {
            return it.body?.string() ?:
                throw RestException("Something went wrong, while sending issue, more info:\n$it")
        }
    }
}
