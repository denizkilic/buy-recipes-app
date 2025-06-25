package com.deniz.recipes.exceptions

// DBOperationsException is a custom exception class.
class DBOperationsException (
    code: String,
    message: String
) : BaseVndException(code = code, message = message)