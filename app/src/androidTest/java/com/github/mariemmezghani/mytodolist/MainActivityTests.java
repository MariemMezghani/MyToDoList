package com.github.mariemmezghani.mytodolist;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTests {
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void activityDisplayedTest() {
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.main)).check(matches(isDisplayed()));
    }

    @Test
    public void buttonDisplayesTest() {
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.add_fab)).check(matches(isDisplayed()));
    }

    @Test
    public void validateIntentSentToPackage() {
        onView(withId(R.id.add_fab)).perform(click());
        intended(hasComponent(AddTaskActivity.class.getName()));
    }

    @Test
    public void rightTaskOpenedTest() {
        if (getRVcount() > 0) {
            onView(withId(R.id.toDoRecyclerView))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

            onView(withId(R.id.editTextTaskDescription)).check(matches(withText(getFirstTask())));
        }
    }
    //returns first task in the recyclerview
    private String getFirstTask() {
        RecyclerView recyclerView = (RecyclerView) intentsTestRule.getActivity().findViewById(R.id.toDoRecyclerView);
        TextView text1 = recyclerView.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.taskDescription);
        return text1.getText().toString();

    }
    // returns number of items in the recycler view
    private int getRVcount() {
        RecyclerView recyclerView = (RecyclerView) intentsTestRule.getActivity().findViewById(R.id.toDoRecyclerView);
        return recyclerView.getAdapter().getItemCount();
    }

}

