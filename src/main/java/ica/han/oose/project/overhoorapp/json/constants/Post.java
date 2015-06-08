package ica.han.oose.project.overhoorapp.json.constants;

import ica.han.oose.project.overhoorapp.util.Config;

/**
 * A list of paths that require a POST request.
 *
 * @author Niels Bokmans
 * @since 13-04-2015
 */
public enum Post {
    LOGIN("/login"),
    REGISTER("/api/register"),
    TOKEN("/token"),
    CREATE_TOPIC("/api/topics"),
    DELETE_TOPIC("/api/topics/%s"),
    CREATE_SUMMARY("/api/summaries/create"),
    MY_USER_INFO("/api/user"),
    UPDATE_TOPIC("/api/topics"),
    ADD_CHILD_TOPIC_SUMMARY("/api/summaries/%s/child"),
    ADD_EXISTING_TOPIC_TO_SUMMARY("/api/summaries/%s/addchild"),
    CREATE_QUESTION("/api/questions"),
    CREATE_REHEARSAL_FROM_SUMMARY("/api/summaries/%s/rehearsal"),
    DELETE_TOPIC_FROM_SUMMARY("api/summaries/%s/topic"),
    CREATE_REHEARSAL_FROM_QUESTION_IDS("api/rehearsals/questions"),
    CREATE_REHEARSAL_FROM_TOPIC_IDS("/api/rehearsals/topics"),
    TOPIC_CONTRIBUTOR("/api/topics/%s/contributor"),
    DELETE_SUMMARY("/api/summaries/%s"),
    DELETE_QUESTION("/api/questions/%s"),
    ANSWER_QUESTION_IN_REHEARSAL("api/rehearsals/%s/answer");

    /**
     * Path to where the POST request should be sent.
     */
    private String url;


    Post(final String url) {
        this.url = url;
    }

    /**
     * Builds the URL with (optional parameters)
     * Example usage (no parameters): Post.LOGIN.getUrl()
     * Example usage (with parameters): Post.???.getUrl(1)
     *
     * @param parameters Parameters to put into the string
     * @return URL with parameters (if any).
     */
    public String getUrl(final String... parameters) {
        return String.format(Config.BASE_URL + url, parameters);
    }
}