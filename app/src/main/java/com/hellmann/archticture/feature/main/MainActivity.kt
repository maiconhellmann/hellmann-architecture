package com.hellmann.archticture.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hellmann.archticture.R
import com.hellmann.archticture.databinding.FragmentArticleListBinding
import com.hellmann.archticture.util.extensions.toast
import com.hellmann.archticture.util.extensions.visible
import com.hellmann.archticture.feature.viewmodel.ViewState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val androidJobAdapter: ArticlesAdapter by inject()

    private lateinit var binding: FragmentArticleListBinding

    companion object {
        fun launchIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.fragment_article_list)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.getJobs()

        viewModel.state.observe(this, Observer { state ->
            when(state) {
                is ViewState.Success -> {
                    androidJobAdapter.articles = state.data
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
        toast(throwable.toString())
        Log.e("MainActivity", "Error", throwable)
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(context)
        adapter = androidJobAdapter
    }

    private fun setVisibilities(showProgressBar: Boolean = false, showList: Boolean = false, showError: Boolean = false) {
        binding.progressBar.visible(showProgressBar)
        binding.recyclerView.visible(showList)
        binding.btnTryAgain.visible(showError)
    }

}
