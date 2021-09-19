package ru.ytis.rest

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import ru.ytis.issue.Issue

class IssueClient(private val youTrackServer: YouTrackServer) {
    private val client = OkHttpClient()
    private fun makeRequest(issue: Issue): Request {
        return Request.Builder()
            .url(youTrackServer.issuesUrl)
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer ${youTrackServer.token}")
            .addHeader("Content-Type", "application/json")
            .post(issue.toPostJson().toRequestBody("application/json; charset=utf-8".toMediaType()))
            .build()
    }

    fun sendIssue(issue: Issue) {
        client.newCall(makeRequest(issue)).execute().use {
            when (it.message) {
                "OK" -> println("Issue sent")
                else -> throw RestException("Something went wrong, while sending issue, more info:\n$it")
            }
        }
    }
}
