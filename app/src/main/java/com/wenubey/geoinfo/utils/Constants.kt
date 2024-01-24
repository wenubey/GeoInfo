package com.wenubey.geoinfo.utils

object Constants {

    //ROOM CONSTANTS
    const val DATABASE_NAME = "CountryDB"
    const val CACHE_TABLE_NAME = "countriesCache"

    //URL CONSTANTS
    const val BASE_URL_COUNTRIES = "https://restcountries.com/v3.1/"
    const val BASE_URL_HISTORIES = "https://api.api-ninjas.com/v1/"
    const val BASE_URL_DEEP_LINK = "https://geoinfo.com/detail"
    const val BASE_URL_WIKIPEDIA = "https://en.wikipedia.org/wiki/"

    // LOG CONSTANTS
    const val TAG = "TAG"

    //DI NAMES
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"




    //STATE HANDLE
    const val SELECTED_TAB_INDEX_KEY = "selected_tab_index_key"

    //MESSAGES
    const val UNDEFINED = "Undefined"
    const val CANCEL_OPERATION = "You canceled the operation"
    const val ACCESS_REVOKED_MESSAGE = "Your access has been revoked."
    const val SENSITIVE_OPERATION_MESSAGE =
        "This operation is sensitive and requires recent authentication. Log in again before retrying this request."
    const val VERIFY_EMAIL_MESSAGE = "We've sent you an email with a link to verify the email."
    const val USER_SUCCESSFULLY_UPDATE_MESSAGE = "User successfully updated."
    const val USER_UPDATE_ERROR_MESSAGE = "Something went wrong"
    const val ALREADY_VERIFIED = "Already verified?"
    const val SPAM_EMAIL = "If not, please also check the spam folder."
    const val EMAIL_NOT_VERIFIED_MESSAGE = "Your email is not verified."
    const val RESET_PASSWORD_MESSAGE = "We\'ve sent you an email with a link to reset the password"

    //SCREEN TITLES
    const val FORGOT_PASSWORD_SCREEN_TITLE = "Forgot Password"
    const val SIGN_IN_SCREEN_TITLE = "Sign In"
    const val PROFILE_SCREEN_TITLE = "Profile"
    const val SIGN_UP_SCREEN_TITLE = "Sign Up"
    const val MAP_SCREEN_TITLE = "Map"
    const val COUNTRY_LIST_SCREEN_TITLE = "Countries"
    const val VERIFY_EMAIL_SCREEN_TITLE = "Verify Screen"

    //PREVIEW
    const val PREVIEW_CONTENT ="Text for previewing UI components"
    const val PREVIEW_HEADER = "Preview Header"

    val fakeCountryCode = mapOf<String?, String?>("\uD83C\uDDF5\uD83C\uDDF1" to "+48")
}