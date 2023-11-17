package com.wenubey.countryapp.utils

object Constants {

    //ROOM CONSTANTS
    const val DATABASE_NAME = "CountryDB"
    const val CACHE_TABLE_NAME = "countriesCache"
    const val FAV_TABLE_NAME = "countriesFav"

    //API CALL CONSTANTS
    const val BASE_URL_COUNTRIES = "https://restcountries.com/v3.1/"
    const val BASE_URL_HISTORIES = "https://api.api-ninjas.com/v1/"

    // LOG CONSTANTS
    const val TAG = "TAG"

    // ERROR MESSAGES
    const val UNKNOWN_ERROR = "An unknown error occurred"

    //DI NAMES
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"

    //DB REF
    const val USERS = "users"

    //LABELS
    const val EMAIL_LABEL = "Email"
    const val PASSWORD_LABEL = "Password"
    const val DISPLAY_NAME_LABEL = "Display Name"

    //CONTENT DESCRIPTIONS
    const val BACK_BUTTON_DESCRIPTION = "Back to previous screen"
    const val PASSWORD_VISIBILITY_DESCRIPTION = "Password visibility on/off"
    const val OPEN_MENU_DESCRIPTION = "This buttons opens the menu"
    const val GOOGLE_SIGN_IN_BUTTON_DESCRIPTION = "Sign in with Google"
    const val FACEBOOK_SIGN_UP_BUTTON_DESCRIPTION = "Sign in with Facebook"
    const val TWITTER_SIGN_UP_BUTTON_DESCRIPTION = "Sign in with Facebook"
    const val PROFILE_PHOTO_DESCRIPTION = "Your profile photo"
    const val PROFILE_UPDATE_FAB_DESCRIPTION = "Update your profile"

    //TEXTS
    const val FORGOT_PASSWORD = "Forgot password?"
    const val NO_ACCOUNT = "Don't you have an account? Sign Up!"
    const val REVOKE_ACCESS_MESSAGE = "You need to re-authenticate before revoking the access."
    const val ALREADY_USER = "Already a user? Sign in."
    const val ALREADY_VERIFIED = "Already verified?"
    const val SPAM_EMAIL = "If not, please also check the spam folder."
    const val CANCEL_OPERATION = "You canceled the operation"
    const val CANT_CHANGE_PASSWORD = "Sorry, you log in different provider than email, you can not change or forgot your password."
    const val SEARCH_COUNTRIES_PLACEHOLDER ="Search countries"

    //MESSAGES
    const val RESET_PASSWORD_MESSAGE = "We've sent you an email with a link to reset the password."
    const val ACCESS_REVOKED_MESSAGE = "Your access has been revoked."
    const val EMAIL_NOT_VERIFIED_MESSAGE = "Your email is not verified."
    const val SENSITIVE_OPERATION_MESSAGE =
        "This operation is sensitive and requires recent authentication. Log in again before retrying this request."
    const val VERIFY_EMAIL_MESSAGE = "We've sent you an email with a link to verify the email."
    const val USER_SUCCESSFULLY_UPDATE_MESSAGE = "User successfully updated."
    const val USER_UPDATE_ERROR_MESSAGE = "Something went wrong"

    //SCREEN TITLES
    const val FORGOT_PASSWORD_SCREEN_TITLE = "Forgot Password"
    const val SIGN_IN_SCREEN_TITLE = "Sign In"
    const val PROFILE_SCREEN_TITLE = "Profile"
    const val SIGN_UP_SCREEN_TITLE = "Sign Up"
    const val MAP_SCREEN_TITLE = "Map"
    const val VERIFY_EMAIL_SCREEN_TITLE = "Verify Screen"

    // BUTTONS
    const val SIGN_IN = "Sign in"
    const val RESET_PASSWORD = "Reset"
    const val SIGN_UP = "Sign up"
    const val SIGN_OUT = "Sign out"
    const val REVOKE_ACCESS = "Revoke Access"
    const val SIGN_IN_WITH_GOOGLE = "Sign in with Google"
    const val SIGN_UP_WITH_TWITTER = "Sign in with Twitter"
    const val SAVE = "Save"

    //USER FIELDS
    const val PROFILE_INFO = "Profile Info"
}