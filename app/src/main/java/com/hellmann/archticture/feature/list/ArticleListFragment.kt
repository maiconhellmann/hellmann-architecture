package com.hellmann.archticture.feature.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hellmann.archticture.R
import com.hellmann.archticture.databinding.FragmentArticleListBinding
import com.hellmann.archticture.feature.viewmodel.ViewState
import com.hellmann.archticture.util.extensions.toast
import com.hellmann.archticture.util.extensions.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleListFragment : Fragment() {

    val viewModel: ArticleViewModel by viewModel()
    private val articlesAdapter: ArticlesAdapter by inject()

    lateinit var binding: FragmentArticleListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_article_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupViewModel()
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.onTryAgainRequired()
        }
    }

    @VisibleForTesting
    fun setupViewModel() {
        viewModel.state.observe(this.viewLifecycleOwner, Observer { state ->
            when (state) {
                is ViewState.Success -> {
                    articlesAdapter.articles = state.data
                    setVisibilities(showList = true)
                }
                is ViewState.Loading -> {
                    setVisibilities(showProgressBar = true)
                }
                is ViewState.Failed -> {
                    setVisibilities(showError = true)
                    showError(state.throwable)
                }
            }
        })
    }

    private fun showError(throwable: Throwable) {
        view?.context?.toast(throwable.toString())
        Log.e("MainActivity", "Error", throwable)
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(context)
        adapter = articlesAdapter
    }

    private fun setVisibilities(
        showProgressBar: Boolean = false,
        showList: Boolean = false,
        showError: Boolean = false,
        isRefreshing: Boolean = false
    ) {
        binding.progressBar.visible(showProgressBar)
        binding.recyclerView.visible(showList)
        binding.btnTryAgain.visible(showError)
        binding.swipeRefresh.isRefreshing = isRefreshing
    }
}
