package com.example.rvpayloadsdemo

import java.util.*

data class GameItem(
    val homeTeamName: String,
    val awayTeamName: String,
    val homeTeamScore: Int,
    val awayTeamScore: Int,
    val id: String = UUID.randomUUID().toString()
) {
}
