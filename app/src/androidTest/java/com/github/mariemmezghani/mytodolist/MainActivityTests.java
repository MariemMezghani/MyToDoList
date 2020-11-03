package com.github.mariemmezghani.mytodolist;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
    public void testAddingNoTask(){
        launchAddTaskActivity();
        onView(withId(R.id.addButton)).perform(click());
        onView(withId(R.id.main)).check(matches(isDisplayed()));
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


    @Test
    public void testAddTask() {
        launchAddTaskActivity();
        onView(withId(R.id.editTextTaskDescription)).perform(typeText("Test"));
        onView(withId(R.id.addButton)).perform(click());

        onView(new RecyclerViewMatcher(R.id.toDoRecyclerView)
                .atPositionOnView(getRVcount()-1,R.id.taskDescription))
                .check(matches(withText("Test")));

    }

    public void launchAddTaskActivity(){
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.main)).check(matches(isDisplayed()));
        onView(withId(R.id.add_fab)).perform(click());
        onView(withId(R.id.addTaskActivity)).check(matches(isDisplayed()));
    }
    public  class RecyclerViewMatcher {
        private final int recyclerViewId;

        private RecyclerViewMatcher(int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
        }

        public Matcher<View> atPosition(final int position) {
            return atPositionOnView(position, -1);
        }

        private Matcher<View> atPositionOnView(final int position, final int targetViewId) {

            return new TypeSafeMatcher<View>() {
                Resources resources = null;
                View childView;

                public void describeTo(Description description) {
                    String idDescription = Integer.toString(recyclerViewId);
                    if (this.resources != null) {
                        try {
                            idDescription = this.resources.getResourceName(recyclerViewId);
                        } catch (Resources.NotFoundException var4) {
                            idDescription = String.format("%s (resource name not found)",
                                    new Object[]{Integer.valueOf
                                            (recyclerViewId)});
                        }
                    }

                    description.appendText("with id: " + idDescription);
                }

                public boolean matchesSafely(View view) {

                    this.resources = view.getResources();

                    if (childView == null) {
                        RecyclerView recyclerView =
                                (RecyclerView) view.getRootView().findViewById(recyclerViewId);
                        if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                            childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                        } else {
                            return false;
                        }
                    }

                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        View targetView = childView.findViewById(targetViewId);
                        return view == targetView;
                    }

                }
            };
        }
    }
}

