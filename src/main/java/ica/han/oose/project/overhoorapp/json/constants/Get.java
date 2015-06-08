package ica.han.oose.project.overhoorapp.json.constants;

import ica.han.oose.project.overhoorapp.util.Config;

/**
 * A list of paths that require a GET request.
 *
 * @author Niels Bokmans
 * @since 13-04-2015
 */
public enum Get {

    WEBSOCKET(""),
    LOGOUT("/logout"),
    LANDING("/landing"),
    REGISTER("/register"),
    LOGIN("/login"),
    FOLLOW("/user/follow/%s"),
    UNFOLLOW("/user/unfollow/%s"),
    MY_SUMMARIES_INDEX("/api/summaries"),     //onduidelijk wat deze precies retourneert?
    MY_SUMMARIES_LIST("/api/summaries/list"),
    SUMMARY_BY_ID("/api/summaries/%s"),
    MY_REHEARSALS_INDEX("/api/rehearsals"),                      //idem
    MY_REHEARSALS_LIST("/api/rehearsals/list"),
    REHEARSAL_BY_ID("/api/rehearsals/%s"),
    REHEARSAL_ITEMS_BY_ID("/api/rehearsals/%s/items"),
    REHEARSAL_STATUS_BY_ID("/api/rehearsals/%s/status"),
    REHEARSAL_CURRENT_QUESTION_BY_ID("/api/rehearsals/%s/question"),
    RESET_REHEARSAL_BY_ID("/api/rehearsals/%s/reset"),
    MY_QUESTIONS_INDEX("/api/questions"),              //idem
    MY_QUESTIONS_LIST("/api/questions/list"),
    QUESTION_BY_ID("/api/questions/%s"),
    QUESTION_STATEMENT_BY_ID("/api/questions/%s/statement"),
    QUESTION_INFO_BY_ID("/api/questions/%s/info"),
    MY_TOPICS_INDEX("/api/topics"),
    MY_TOPICS_LIST("/api/topics/list"),
    TOPIC_BY_ID("/api/topics/%s"),
    TOPIC_QUESTIONS_BY_ID("/api/topics/%s/questions"),
    TOPICS_BY_SUMMARY_ID("/api/summaries/:%s/topics"),
    JS_ROUTES("/jsroutes.js"),
    ASSET_BY_FILE_NAME("/assets/%s"),
    GET_USER_INFO("/api/user"),
    FIND_BY_USERNAME("/api/users/find?username=%s"),
    FIND_BY_FIRSTNAME("/api/users/find?firstname=%s"),
    FIND_BY_LASTNAME("/api/users/find?lastname=%s");


    /**
     * Path to where the GET request should be sent.
     */
    private String url;


    Get(final String url) {
        this.url = url;
    }

    /**
     * Builds the URL with (optional parameters)
     * Example usage (no parameters): Get.MY_QUESTION_INDEX.getUrl()
     * Example usage (with parameters): Get.QUESTION_INFO_BY_ID.getUrl(1)
     *
     * @param parameters Parameters to put into the string
     * @return URL with parameters (if any).
     */
    public String getUrl(final String... parameters) {
        return String.format(Config.BASE_URL + url, parameters);
    }
}
