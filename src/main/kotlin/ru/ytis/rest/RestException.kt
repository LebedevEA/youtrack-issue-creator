package ru.ytis.rest

import okio.IOException

class RestException(msg: String) : IOException(msg)
