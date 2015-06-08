package ica.han.oose.project.overhoorapp.knowledge;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ica.han.oose.project.overhoorapp.exceptions.NameIncorrectException;
import ica.han.oose.project.overhoorapp.exceptions.NameToShortException;

import static org.junit.Assert.assertEquals;

/**
 * Created by coen on 21-5-2015.
 */
public class TopicActivityTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    TopicActivity topicActivity;

    @Before
    public void setUp() {
        topicActivity = new TopicActivity();
    }


    @Test
    public void testCheckNameCorrectName() {

       // assertEquals(topicActivity.checkName("ikbentoegestaan"), true);
    }

    @Test
    public void testCheckNameNotEnoughCharacters() {
   //     exception.expect(NameToShortException.class);
       // topicActivity.checkName("ik");
    }

    @Test
    public void testCheckNameCharacterNowAllowed() {
      //  exception.expect(NameIncorrectException.class);
       // topicActivity.checkName("$%^^");
    }


    @Test
    public void testEditTopic() {

    }

    @Test
    public void testStartQuiz() {

    }

    @Test
    public void testSaveNewTopicData() {

    }

    @Test
    public void testEditTopicData() {

    }

}