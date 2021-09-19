package ru.ytis.rest

import java.net.URL

class YouTrackServer(url: String, val token: String) {
    val availableProjectsUrl = URL("${url.trimEnd('/')}/api/admin/projects?fields=id,name")
    val issuesUrl = URL("${url.trimEnd('/')}/api/issues")
}
