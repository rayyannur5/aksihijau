package com.aksihijau.ui.fiturcampaign.newsletter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aksihijau.R
import com.aksihijau.databinding.ActivityNewsListBinding
import com.aksihijau.ui.fiturcampaign.detail.CampaignDetailActivity

class NewsListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewsListBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setTitle("Newsletter")
        }
        binding.toolbar.root.setTitleTextColor(resources.getColor(R.color.white))

        val rvNewsletter : RecyclerView = binding.rvListNews
        rvNewsletter.layoutManager = LinearLayoutManager(this)
        newsAdapter = ListNewsAdapter(ArrayList())
        rvNewsletter.adapter = newsAdapter

        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        val slug = intent.getStringExtra(CampaignDetailActivity.EXTRA_SLUG)
        slug?.let {
            newsViewModel.getNewsletter(it)
        }

        newsViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        newsViewModel.isSuccess.observe(this, { isSuccess ->
            // Handle success state here
        })

        newsViewModel.reports.observe(this, { reports ->
            reports?.let {
                newsAdapter.setData(it)

                val noDataView = binding.noData.root
                if (reports.isEmpty()) {
                    noDataView.visibility = View.VISIBLE
                } else {
                    noDataView.visibility = View.GONE
                }
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}