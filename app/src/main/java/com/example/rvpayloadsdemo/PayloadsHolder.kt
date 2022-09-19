package com.example.rvpayloadsdemo

class PayloadsHolder {

    companion object {
        val EMPTY = PayloadsHolder()
    }

    private val payloadTypes: MutableList<Int> = mutableListOf()

    fun add(payloadType: Int) {
        payloadTypes.add(payloadType)
    }

    fun shouldUpdate(payloadType: Int): Boolean {
        return payloadTypes.isEmpty() || payloadTypes.contains(payloadType)
    }

    override fun toString(): String {
        return payloadTypes.toString()
    }
}