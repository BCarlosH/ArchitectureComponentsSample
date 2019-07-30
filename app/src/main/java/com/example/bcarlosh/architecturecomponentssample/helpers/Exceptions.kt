package com.example.bcarlosh.architecturecomponentssample.helpers

import com.example.bcarlosh.architecturecomponentssample.ui.error.ErrorType
import java.io.IOException


class NoConnectivityException : IOException(ErrorType.CONNECTIVITY.name)