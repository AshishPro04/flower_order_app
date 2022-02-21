package com.example.flowera

class Flower(var enable: Boolean,var count: Int) {
    fun enableFlower(enable: Boolean) {
        this.enable = enable
        count = if (enable) 0 else 1
    }
}