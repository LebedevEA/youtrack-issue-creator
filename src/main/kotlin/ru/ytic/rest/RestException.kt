package ru.ytic.rest

import okio.IOException

class RestException(msg: String) : IOException(msg)
