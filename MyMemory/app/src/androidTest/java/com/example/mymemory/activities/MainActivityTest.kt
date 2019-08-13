package com.example.mymemory.activities


import android.view.View
import android.view.ViewGroup
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.mymemory.R
import com.example.mymemory.activities.CustomMatchers.Companion.withItemCount
import com.example.mymemory.persistence.MemoryDatabase
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    companion object {
        @BeforeClass
        @JvmStatic
        fun clearDatabase() {
            InstrumentationRegistry.getTargetContext().deleteDatabase("Memory_database")
        }
    }

    @Test
    fun createNewMemory_showsNewMemoryInRecyclerAndDetailView() {
        clickMyMemoriesButton()
        clickMoreOptionsButton()
        clickRemoveAllMemories()
        clickCreateNewMemoryButton()
        enterTitle()
        enterText()
        clickSaveButton()
        clickCreatedMemory()

        onView(withId(R.id.add_edit_memory_fragment_container)).check(matches(hasDescendant(withText("Testtitel"))))
        onView(withId(R.id.add_edit_memory_fragment_container)).check(matches(hasDescendant(withText("Testtekst"))))

    }

    @Test
    fun swipeAwayMemory_RemovesMemoryFromDBTest() {
        clickMyMemoriesButton()

        clickMoreOptionsButton()

        clickRemoveAllMemories()

        clickMoreOptionsButton()

        clickLoadSampleData()

        swipeLeftOnFirstItem()

        Espresso.pressBack()

        clickMyMemoriesButton()


        onView(withId(R.id.memory_list)).check(matches(withItemCount(1)))

    }

    private fun swipeLeftOnFirstItem() {
        val relativeLayout = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.memory_list),
                        childAtPosition(
                            withId(R.id.frameLayout),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        relativeLayout.perform(swipeLeft())
    }

    private fun clickLoadSampleData() {
        val appCompatTextView2 = onView(
            allOf(
                withId(R.id.title), withText("load sample memories"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView2.perform(click())
    }

    private fun clickCreatedMemory() {
        val relativeLayout = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.memory_list),
                        childAtPosition(
                            withId(R.id.frameLayout),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        relativeLayout.perform(click())
    }

    private fun clickSaveButton() {
        val actionMenuItemView = onView(
            allOf(
                withId(R.id.save_memory), withContentDescription("Save"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())
    }

    private fun enterText() {
        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.edit_text),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.add_edit_memory_fragment_container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("Testtekst"), closeSoftKeyboard())
    }

    private fun enterTitle() {
        val appCompatEditText = onView(
            allOf(
                withId(R.id.edit_title),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.add_edit_memory_fragment_container),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Testtitel"), closeSoftKeyboard())
    }

    private fun clickCreateNewMemoryButton() {
        val floatingActionButton = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())
    }

    private fun clickRemoveAllMemories() {
        val appCompatTextView = onView(
            allOf(
                withId(R.id.title), withText("Delete All Memories"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())
    }

    private fun clickMoreOptionsButton() {
        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())
    }

    private fun clickMyMemoriesButton() {
        val appCompatButton = onView(
            allOf(
                withId(R.id.findMemoryBtn), withText("My memories"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }




}
