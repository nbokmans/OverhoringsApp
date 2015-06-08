package ica.han.oose.project.overhoorapp.overhoren;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ica.han.oose.project.overhoorapp.R;

/**
 * Created by Roy van Keijsteren on 27-5-2015.
 */

public class OrderQuestion extends QuestionFragment {
    /*
    * Handles the questionAswered event.
    * Sends the given answer to the server
    * Tells the activity to display the next question
    */
    View.OnClickListener nextQuestionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //todo send answer to server
            ((QuizActivity) getActivity()).nextQuestion();

        }
    };
    /*
    * Handles the questionAswered event.
    * Checks the answer and shows the user the correct and incorrect listitems by changing their background color.
    */
    View.OnClickListener answerQuestionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            List<String> newlist = new ArrayList<>();
            for (int i = 0; i < correctOrder.size(); i++) {
                //Sends the given answer to the persistenceAdapter
                sendAnswer(i, adapter.getItem(i));

                // Checks if the ordor is correct and colours the background accordingly
                if (correctOrder.get(i).equals(adapter.getItem(i))) {
                    listView.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.textboxcorrectanswer));
                } else {
                    listView.getChildAt(i).setBackground(getResources().getDrawable(R.drawable.textboxincorrectanswer));
                }
            }

            answerQuestion.setVisibility(View.INVISIBLE);
            nextQuestionButton.setVisibility(View.VISIBLE);

        }
    };
    private DragSortListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> correctOrder;
    private Button answerQuestion;
    private Button nextQuestionButton;
    /*
    * Draglistener to handle the dragging of elements to a different index in the list.
    */
    private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
        @Override
        public void drop(int from, int to) {
            if (from != to) {
                String item = adapter.getItem(from);
                adapter.remove(item);
                adapter.insert(item, to);
            }
        }
    };

    public void initOrderQuestions(List<String> correctOrder) {
        this.correctOrder = correctOrder;
    }

    /*
    * Instanciates the buttons and list to be displayed.
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final View fragment = inflater.inflate(R.layout.fragment_order_list, container, false);

        listView = (DragSortListView) fragment.findViewById(R.id.listview);

        answerQuestion = (Button) fragment.findViewById(R.id.OrderQuestionsAnswer);
        nextQuestionButton = (Button) fragment.findViewById(R.id.OrderQuestionNextButton);

        setPersistenceAdapter(fragment.getContext());

        fragment.findViewById(R.id.OrderQuestionsAnswer).setOnClickListener(answerQuestionListener);
        nextQuestionButton.setOnClickListener(nextQuestionListener);


        ArrayList<String> list = new ArrayList<String>(correctOrder);
        Collections.shuffle(list);
        setListView(list);

        return fragment;
    }

    /*
    * Configures and fills the listview with answers
    */
    private void setListView(List<String> list) {
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_basic, list);
        listView.setAdapter(adapter);
        listView.setDropListener(onDrop);

        DragSortController controller = new DragSortController(listView);
        controller.setRemoveEnabled(false);
        controller.setSortEnabled(true);
        controller.setDragInitMode(1);

        listView.setFloatViewManager(controller);
        listView.setOnTouchListener(controller);
        listView.setDragEnabled(true);
    }
}



