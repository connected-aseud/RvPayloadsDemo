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
import com.example.rvpayloadsdemo.PayloadsHolder
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
        Log.d(TAG, "fun onBindViewHolder(holder: GameViewHolder, position($position): Int)")
        // No-op
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int, payloads: MutableList<Any>) {
        val payloadsHolder = payloads.singleOrNull() as? PayloadsHolder ?: PayloadsHolder.EMPTY
        Log.d(TAG, "fun onBindViewHolder(holder: GameViewHolder, position($position): Int), payloads$payloads: MutableList<Any>)")
        getItem(position)?.let {
            holder.bind(it, payloadsHolder)
        }
    }

    class GameViewHolder(
        view: View,
        val onHomeScoreClick: (GameItem) -> Unit,
        val onAwayScoreClick: (GameItem) -> Unit,
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: GameItem, payloadsHolder: PayloadsHolder = PayloadsHolder.EMPTY) {
            Log.d(TAG, "---------------------- bind($adapterPosition) ----------------------")

            if (payloadsHolder.shouldUpdate(PayloadTypes.TEAM_NAME_HOME)) {
                itemView.findViewById<TextView>(R.id.tv_team_name_home).text = item.homeTeamName.also {
                    Log.d(TAG, "updated tv_team_name_home: $it")
                }
            }

            if (payloadsHolder.shouldUpdate(PayloadTypes.TEAM_NAME_AWAY)) {
                itemView.findViewById<TextView>(R.id.tv_team_name_away).text = item.awayTeamName.also {
                    Log.d(TAG, "updated tv_team_name_away: $it")
                }
            }

            itemView.findViewById<TextView>(R.id.tv_team_score_home).apply {
                setOnClickListener { onHomeScoreClick(item) }
                if (payloadsHolder.shouldUpdate(PayloadTypes.TEAM_SCORE_HOME)) {
                    text = item.homeTeamScore.toString().also {
                        Log.d(TAG, "updated tv_team_score_home: $it")
                    }
                }
            }

            itemView.findViewById<TextView>(R.id.tv_team_score_away).apply {
                setOnClickListener { onAwayScoreClick(item) }
                if (payloadsHolder.shouldUpdate(PayloadTypes.TEAM_SCORE_AWAY)) {
                    text = item.awayTeamScore.toString().also {
                        Log.d(TAG, "updated tv_team_score_away: $it")
                    }
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

        override fun getChangePayload(oldItem: GameItem, newItem: GameItem): Any? {
            return PayloadsHolder().apply {
                if (oldItem.homeTeamName != newItem.homeTeamName) add(PayloadTypes.TEAM_NAME_HOME)
                if (oldItem.awayTeamName != newItem.awayTeamName) add(PayloadTypes.TEAM_NAME_AWAY)
                if (oldItem.homeTeamScore != newItem.homeTeamScore) add(PayloadTypes.TEAM_SCORE_HOME)
                if (oldItem.awayTeamScore != newItem.awayTeamScore) add(PayloadTypes.TEAM_SCORE_AWAY)
            }
        }
    }

    object PayloadTypes {
        const val TEAM_NAME_HOME = 1
        const val TEAM_NAME_AWAY = 2
        const val TEAM_SCORE_HOME = 3
        const val TEAM_SCORE_AWAY = 4
    }
}