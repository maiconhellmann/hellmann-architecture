package com.hellmann.archticture

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.hellmann.archticture.feature.list.ArticleListFragment
import com.hellmann.archticture.feature.list.ArticleListFragmentDirections
import com.hellmann.archticture.feature.list.ArticlesAdapter
import com.hellmann.archticture.feature.viewmodel.ViewState
import com.hellmann.domain.entity.Article
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    private lateinit var mockNavController: NavController
    private lateinit var articleList: FragmentScenario<ArticleListFragment>

    @Before
    fun setUp() {
        mockNavController = Mockito.mock(NavController::class.java)
    }

    @Test
    fun `articleListFragment test`() {
        articleList = launchFragmentInContainer<ArticleListFragment>().onFragment {
            Navigation.setViewNavController(it.requireView(), mockNavController)

            //Loading
            it.viewModel.state.postValue(ViewState.Loading)

            assert(it.view?.findViewById<View>(R.id.progressBar)?.isVisible == true)

            //Post a list with one item
            it.viewModel.state.postValue(
                ViewState.Success(
                    listOf(
                        Article(
                            "", "", "http://www.google.com"))))

            //Not loading anymore
            assert(it.view?.findViewById<View>(R.id.progressBar)?.isVisible == false)

            it.view?.findViewById<RecyclerView>(R.id.recyclerView)?.apply {
                val index = 0

                //Url of the current item
                val url = (adapter as ArticlesAdapter).articles[index].url ?: ""

                //Click current item
                onView(withId(R.id.recyclerView)).perform(
                    actionOnItemAtPosition<ArticlesAdapter.ViewHolder>(
                        index, click()))

                //navigation of the url of the item
                val directions = ArticleListFragmentDirections.actionOpenWebview(url)

                //verify destination
                Mockito.verify(mockNavController).navigate(directions)
            }
        }
    }
}
