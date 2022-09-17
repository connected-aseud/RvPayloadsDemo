package com.example.rvpayloadsdemo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rvpayloadsdemo.GameItem
import com.example.rvpayloadsdemo.R
import com.example.rvpayloadsdemo.TAG

class GamesAdapter(
    private val onHomeScoreClick: (GameItem) -> Unit,
    private val onAwayScoreClick: (GameItem) -> Unit,
) : ListAdapter<GameItem, GamesAdapter.GameViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false),
            onHomeScoreClick,
            onAwayScoreClick,
        )
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class GameViewHolder(
        view: View,
        val onHomeScoreClick: (GameItem) -> Unit,
        val onAwayScoreClick: (GameItem) -> Unit,
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: GameItem) {
            Log.d(TAG, "---------------------- bind ----------------------")

            itemView.findViewById<TextView>(R.id.tv_team_name_home).text = item.homeTeamName.also {
                Log.d(TAG, "updated tv_team_name_home: $it")
            }

            itemView.findViewById<TextView>(R.id.tv_team_name_away).text = item.awayTeamName.also {
                Log.d(TAG, "updated tv_team_name_away: $it")
            }

            itemView.findViewById<TextView>(R.id.tv_team_score_home).apply {
                setOnClickListener { onHomeScoreClick(item) }
                text = item.homeTeamScore.toString().also {
                    Log.d(TAG, "updated tv_team_score_home: $it")
                }
            }

            itemView.findViewById<TextView>(R.id.tv_team_score_away).apply {
                setOnClickListener { onAwayScoreClick(item) }
                text = item.awayTeamScore.toString().also {
                    Log.d(TAG, "updated tv_team_score_away: $it")
                }
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<GameItem>() {
        override fun areItemsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
            return oldItem == newItem
        }
    }
}