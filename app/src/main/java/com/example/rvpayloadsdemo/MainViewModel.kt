package com.example.rvpayloadsdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _list = MutableLiveData(MockData.list)

    val list: LiveData<List<GameItem>> = _list

    fun onHomeScoreClick(gameItem: GameItem) {
        updateList(gameItem.copy(homeTeamScore = gameItem.homeTeamScore + 1))
    }

    fun onAwayScoreClick(gameItem: GameItem) {
        updateList(gameItem.copy(awayTeamScore = gameItem.awayTeamScore + 1))
    }

    private fun updateList(changedItem: GameItem) {
        _list.value = _list.value.orEmpty().map {
            if (changedItem.id == it.id) changedItem else it
        }
    }
}