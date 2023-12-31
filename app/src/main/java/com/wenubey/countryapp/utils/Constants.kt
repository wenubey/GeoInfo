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

    //CONTENT DESCRIPTIONS
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
    const val ADD_FAV_CONTENT_DESCRIPTION = "You can log out or request a revoke or go to forgot password screen."
    const val SHARE_CONTENT_DESCRIPTION = "You can share country info when you clicked this button."
    const val ADD_REMOVE_FAV_CONTENT_DESCRIPTION = "You can add/remove favorite when you clicked this button."

    //TEXTS
    const val REVOKE_ACCESS_MESSAGE = "You need to re-authenticate before revoking the access."
    const val ALREADY_VERIFIED = "Already verified?"
    const val SPAM_EMAIL = "If not, please also check the spam folder."
    const val CANCEL_OPERATION = "You canceled the operation"
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
    const val SHARE = "Share!"
    const val FAVORITE = "Add/Remove favorite"

    //MESSAGES
    const val ACCESS_REVOKED_MESSAGE = "Your access has been revoked."

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
    const val COUNTRY_LIST_SCREEN_TITLE = "Countries"
}