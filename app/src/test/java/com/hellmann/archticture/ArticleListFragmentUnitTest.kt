package com.hellmann.archticture

import android.os.Build
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.SmallTest
import com.hellmann.archticture.feature.list.ArticleListFragment
import com.hellmann.archticture.feature.list.ArticleListFragmentDirections
import com.hellmann.archticture.feature.list.ArticleViewModel
import com.hellmann.archticture.feature.list.ArticlesAdapter
import com.hellmann.archticture.feature.viewmodel.ViewState
import com.hellmann.domain.entity.Article
import com.hellmann.domain.usecase.GetArticlesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@SmallTest
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class ArticleListFragmentUnitTest {

    @MockK(relaxed = true)
    private lateinit var mockNavController: NavController
    private lateinit var articleList: FragmentScenario<ArticleListFragment>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `articleListFragment test`() {
        articleList = launchFragmentInContainer<ArticleListFragment>().onFragment {
            val useCase = mockk<GetArticlesUseCase> {
                coEvery { execute(any()) } returns emptyList()
            }

            it.binding.viewModel = ArticleViewModel(useCase, Dispatchers.IO)
            it.setupViewModel()

            val recyclerView = it.view?.findViewById<RecyclerView>(R.id.recyclerView)
            val progressBar = it.view?.findViewById<View>(R.id.progressBar)

            Navigation.setViewNavController(it.requireView(), mockNavController)
            //Loading
            (it.viewModel.state as MutableLiveData).postValue(ViewState.Loading)

            assert(progressBar?.isVisible == true)

            //Post a list with one item
            (it.viewModel.state as MutableLiveData).postValue(
                ViewState.Success(
                    listOf(
                        Article(
                            "test", "test", "http://www.google.com"))))

            //Not loading anymore
            assert(progressBar?.isVisible == false)
            assert(recyclerView?.isVisible == true)

            val adapter  = (recyclerView?.adapter as ArticlesAdapter)

            assert(adapter.articles.isNotEmpty())

            val index = 0

            //Url of the current item
            val url = adapter.articles[index].url ?: ""

            // Clicks on an item
            onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition<ArticlesAdapter.ViewHolder>(index, forceClick()))

            //navigation of the url of the item
            val directions = ArticleListFragmentDirections.actionOpenWebview(url)

            //verify destination
            verify {
                mockNavController.navigate(directions)
            }
        }
    }
}

// TODO maiconhellmann - move to a utils package
fun forceClick(): ViewAction? {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return allOf(isClickable(), isEnabled(), isDisplayed())
        }

        override fun getDescription(): String {
            return "force click"
        }

        override fun perform(uiController: UiController, view: View) {
            view.performClick() // perform click without checking view coordinates.
            uiController.loopMainThreadUntilIdle()
        }
    }
}
