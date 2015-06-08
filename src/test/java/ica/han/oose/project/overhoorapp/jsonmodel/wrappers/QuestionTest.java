package ica.han.oose.project.overhoorapp.jsonmodel.wrappers;

import com.owlike.genson.Genson;

import org.junit.Before;
import org.junit.Test;

import ica.han.oose.project.overhoorapp.json.constants.Get;
import ica.han.oose.project.overhoorapp.json.constants.QuestionType;
import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionList;
import ica.han.oose.project.overhoorapp.json.models.questions.get.QuestionWrapper;
import ica.han.oose.project.overhoorapp.util.requestbuilder.impl.GetRequestBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the deserialization of JSON into a Question object and all the wrapper methods.
 *
 * @author Niels Bokmans
 * @version 1.1
 * @since 18-05-2015
 */
public class QuestionTest {
    private static final Genson GENSON = new Genson();
    private QuestionWrapper testQuestion;
    private QuestionList questions;

    @Before
    public void setUp() {
        final GetRequestBuilder builder = new GetRequestBuilder(Get.MY_QUESTIONS_INDEX.getUrl());
        builder.submit();
        questions = GENSON.deserialize(builder.getResult(), QuestionList.class);
        testQuestion = questions.getQuestions()[0];
    }

    @Test
    public void testTestQuestionNotNull() {
        assertFalse(testQuestion == null);
    }

    @Test
    public void testDeserializeQuestionObjectID() {
        assertEquals("556ee3cb1c0000af01e4ea81", testQuestion.get_id().get$oid());
    }

    @Test
    public void testDeserializeCorrectAnswer() {
        assertEquals("", testQuestion.getCorrectAnswers().getResponse()[0].getOption());
    }

    @Test
    public void testDeserializeVersion() {
        assertEquals(1.0, testQuestion.getVersion(), 0);
    }

    @Test
    public void testDeserializeUpdateDate() {
        assertEquals(1433330616105L, testQuestion.getUpdateDate());
    }

    @Test
    public void testDeserializeQuestion() {
        assertEquals("dit is een vraag", testQuestion.getStatement().getText().getValue());
    }

    @Test
    public void testDeserializeIncorrectAnswers() {
        assertTrue(testQuestion.getIncorrectAnswers().length == 0);
    }

    @Test
    public void testDeserializeOwnerUID() {
        assertEquals(20, Integer.parseInt(testQuestion.getOwner().getUid()));
    }

    @Test
    public void testDeserializeTopicObjectIDByQuestion() {
        assertEquals("556ee06e1c00009201e4ea7e", testQuestion.getTopicId().get$oid());
    }

    @Test
    public void testDeserializeCreationDate() {
        assertEquals(1433330616105L, testQuestion.getCreationDate());
    }

    @Test
    public void testDeserializeInfoElements() {
        assertTrue(testQuestion.getInfo().length == 0);
    }

    @Test
    public void testDeserializeQuestionType() {
        assertEquals("listanswer", testQuestion.getQuestionType());
    }

    @Test
    public void testDeserializeAllQuestions() {
       assertTrue(questions.getQuestions().length > 1);
    }
}