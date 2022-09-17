package com.example.rvpayloadsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rvpayloadsdemo.adapter.GamesAdapter

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get<MainViewModel>() }
    private val adapter by lazy {
        GamesAdapter(
            onHomeScoreClick = viewModel::onHomeScoreClick,
            onAwayScoreClick = viewModel::onAwayScoreClick,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.recycler_view).let { recyclerView ->
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        viewModel.list.observe(this) { list ->
            adapter.submitList(list)
        }
    }
}