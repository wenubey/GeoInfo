package com.wenubey.countryapp.utils

object Constants {

    //ROOM CONSTANTS
    const val DATABASE_NAME = "CountryDB"
    const val CACHE_TABLE_NAME = "countriesCache"

    //URL CONSTANTS
    const val BASE_URL_COUNTRIES = "https://restcountries.com/v3.1/"
    const val BASE_URL_HISTORIES = "https://api.api-ninjas.com/v1/"
    const val BASE_URL_DEEP_LINK = "https://countryapp.com/detail"
    const val BASE_URL_WIKIPEDIA = "https://en.wikipedia.org/wiki/"

    // LOG CONSTANTS
    const val TAG = "TAG"

    //DI NAMES
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"

    //DB REF
    const val USERS = "users"

    //LABELS
    const val EMAIL_LABEL = "Email"
    const val PASSWORD_LABEL = "Password"
    const val DISPLAY_NAME_LABEL = "Display Name"

    // Navigation Constants
    const val PROFILE_SCREEN = "profileScreen"
    const val MAP_SCREEN = "mapScreen"

    //CONTENT DESCRIPTIONS
    const val BACK_BUTTON_DESCRIPTION = "Back to previous screen"
    const val PASSWORD_VISIBILITY_DESCRIPTION = "Password visibility on/off"
    const val OPEN_MENU_DESCRIPTION = "This buttons opens the menu"
    const val GOOGLE_SIGN_IN_BUTTON_DESCRIPTION = "Sign in with Google"
    const val FACEBOOK_SIGN_UP_BUTTON_DESCRIPTION = "Sign in with Facebook"
    const val TWITTER_SIGN_UP_BUTTON_DESCRIPTION = "Sign in with Facebook"
    const val PROFILE_PHOTO_DESCRIPTION = "Your profile photo"
    const val PROFILE_UPDATE_FAB_DESCRIPTION = "Update your profile"
    const val ASC_DESC_DESCRIPTION = "Sort order country list"
    const val COUNTRY_FLAG_CONTENT_DESCRIPTION = "This is a country flag."
    const val POPULATION_CONTENT_DESCRIPTION = "Selected country's population"
    const val AREA_CONTENT_DESCRIPTION = "Selected country's area"
    const val SORT_OPTION_ICON_DESCRIPTION = "Sort options icon"
    const val NAVIGATION_BACK_CONTENT_DESCRIPTION = "Navigate to the previous screen."
    const val COUNTRY_KNOWN_AS_CONTENT_DESCRIPTION = "Selected country known as."
    const val COUNTRY_CAPITAL_CONTENT_DESCRIPTION = "Selected country's capital."
    const val COUNTRY_TOP_LEVEL_DOMAINS_CONTENT_DESCRIPTION =
        "Selected country's top level domains."
    const val COUNTRY_CODE_CONTENT_DESCRIPTION = "Selected country's code (CCA2)."
    const val COUNTRY_PHONE_CODE_CONTENT_DESCRIPTION = "Selected country's phone code."
    const val COUNTRY_CONTINENTS_CONTENT_DESCRIPTION = "Selected country's continents."
    const val COUNTRY_REGION_CONTENT_DESCRIPTION = "Selected country's region."
    const val COUNTRY_SUBREGION_CONTENT_DESCRIPTION = "Selected country's subregion."
    const val COUNTRY_LANGUAGES_CONTENT_DESCRIPTION = "Selected country's languages spoken."
    const val COUNTRY_TIMEZONES_CONTENT_DESCRIPTION = "Selected country's timezone where live in."
    const val COUNTRY_DEMONYMS_CONTENT_DESCRIPTION = "Selected country's demonyms."
    const val COUNTRY_DEMONYMS_GENDER_CONTENT_DESCRIPTION = "Selected country's genders for demonyms."
    const val COUNTRY_TRANSLATIONS_CONTENT_DESCRIPTION = "Selected country's translations from other countries."
    const val COUNTRY_MAP_CONTENT_DESCRIPTION = "Selected country's map location."
    const val WIKIPEDIA_CONTENT_DESCRIPTION = "This button will allow you to go wikipedia for selected country's information."
    const val COUNTRY_DROPDOWN_MENU_CONTENT_DESCRIPTION = "This button will open a dropdown menu."
    const val COUNTRY_SELECTED_ICON_DESCRIPTION = "You change flag to coat of arms image with this button."
    const val COUNTRY_TRANSLATIONS_EXPANDED_CONTENT_DESCRIPTION = "You can expand for translations from other countries."
    const val TABS_CONTENT_DESCRIPTION = "You can go to these tab pressing this buttons."
    const val PROFILE_SCREEN_ACCOUNT_SETTINGS_CONTENT_DESCRIPTION = "You can log out or request a revoke or go to forgot password screen."

    //TEXTS
    const val FORGOT_PASSWORD = "Forgot password?"
    const val NO_ACCOUNT = "Don't you have an account? Sign Up!"
    const val REVOKE_ACCESS_MESSAGE = "You need to re-authenticate before revoking the access."
    const val ALREADY_USER = "Already a user? Sign in."
    const val ALREADY_VERIFIED = "Already verified?"
    const val SPAM_EMAIL = "If not, please also check the spam folder."
    const val CANCEL_OPERATION = "You canceled the operation"
    const val CANT_CHANGE_PASSWORD =
        "Sorry, you log in different provider than email, you can not change or forgot your password."
    const val SEARCH_COUNTRIES_PLACEHOLDER = "Search countries"
    const val MAP_SEARCH_BAR_PLACEHOLDER = "Search countries, regions..."
    const val UNDEFINED = "Undefined"
    const val BASIC_INFORMATION = "Basic Information"
    const val GEOGRAPHICAL_INFORMATION = "Geographical Information"
    const val CULTURAL_INFORMATION = "Cultural Information"
    const val ECONOMICAL_INFORMATION = "Economical Information"
    const val HISTORICAL_INFORMATION = "Historical Information"
    const val KNOWN_AS = "Known as:"
    const val CAPITAL = "Capital:"
    const val POPULATION = "Population:"
    const val TOP_LEVEL_DOMAINS = "Top level domains:"
    const val COUNTRY_CODE = "Country code:"
    const val PHONE_CODE = "Phone code:"
    const val AREA = "Area:"
    const val CONTINENTS = "Continents:"
    const val REGION = "Region:"
    const val SUBREGION = "Subregion:"
    const val LANGUAGES = "Languages:"
    const val TIMEZONES = "Timezones:"
    const val DEMONYMS = "Demonyms:"
    const val CURRENCIES = "Currencies:"
    const val TRANSLATIONS = "Translations"
    const val MORE_INFO_WIKIPEDIA = "More info on Wikipedia"
    const val GO_TO_COUNTRY_LOCATION = "Go to country's location on Map"
    const val PREVIEW_CONTENT = "Text for previewing UI components."
    const val PREVIEW_HEADER = "Preview Header"

    //MESSAGES
    const val RESET_PASSWORD_MESSAGE = "We've sent you an email with a link to reset the password."
    const val ACCESS_REVOKED_MESSAGE = "Your access has been revoked."
    const val EMAIL_NOT_VERIFIED_MESSAGE = "Your email is not verified."
    const val SENSITIVE_OPERATION_MESSAGE =
        "This operation is sensitive and requires recent authentication. Log in again before retrying this request."
    const val VERIFY_EMAIL_MESSAGE = "We've sent you an email with a link to verify the email."
    const val USER_SUCCESSFULLY_UPDATE_MESSAGE = "User successfully updated."
    const val USER_UPDATE_ERROR_MESSAGE = "Something went wrong"
    const val FAV_SUCCESS_MESSAGE = "Country successfully added to favorites"
    const val FAV_DELETE_MESSAGE = "Country successfully deleted from favorites"
    const val FAV_ALL_DELETE_MESSAGE = "All countries successfully deleted from favorites"


    //SCREEN TITLES
    const val FORGOT_PASSWORD_SCREEN_TITLE = "Forgot Password"
    const val SIGN_IN_SCREEN_TITLE = "Sign In"
    const val PROFILE_SCREEN_TITLE = "Profile"
    const val SIGN_UP_SCREEN_TITLE = "Sign Up"
    const val MAP_SCREEN_TITLE = "Map"
    const val COUNTRY_LIST_SCREEN_TITLE = "Countries"
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