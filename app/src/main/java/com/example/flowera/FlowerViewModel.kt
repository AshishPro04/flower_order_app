package com.example.flowera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlowerViewModel : ViewModel() {
    private val _isTulip: MutableLiveData<Boolean> = MutableLiveData()
    val isTulip: LiveData<Boolean> = _isTulip
    private val _tulipCount: MutableLiveData<Int> = MutableLiveData(0)
    val tulipCount: LiveData<Int> = _tulipCount
    val tulipPrice: Int = 4

    private val _isRose: MutableLiveData<Boolean> = MutableLiveData()
    val isRose: LiveData<Boolean> = _isRose
    private val _roseCount: MutableLiveData<Int> = MutableLiveData(0)
    val roseCount: LiveData<Int> = _roseCount
    val rosePrice: Int = 2

    private val _isDaisy: MutableLiveData<Boolean> = MutableLiveData()
    val isDaisy: LiveData<Boolean> = _isDaisy
    private val _daisyCount: MutableLiveData<Int> = MutableLiveData(0)
    val daisyCount: LiveData<Int> = _daisyCount
    val daisyPrice: Int = 1

    private val _isLavender: MutableLiveData<Boolean> = MutableLiveData()
    val isLavender: LiveData<Boolean> = _isLavender
    private val _lavenderCount: MutableLiveData<Int> = MutableLiveData(0)
    val lavenderCount: LiveData<Int> = _lavenderCount
    val lavenderPrice: Int = 4
    private val _currentFlower: MutableLiveData<Int> = MutableLiveData(0)
    val currentFlower: LiveData<Int> = _currentFlower

    private val _currentName: MutableLiveData<String> = MutableLiveData("")
    val currentName: LiveData<String> = _currentName

    private val _currentCount: MutableLiveData<Int> = MutableLiveData(0)
    val currentCount: LiveData<Int> = _currentCount

    private val _totalPrice: MutableLiveData<Int> = MutableLiveData(0)
    val totalPrice:LiveData<Int> = _totalPrice

    private lateinit var updateName: () -> Unit

    fun tulipAvailable(value: Boolean) {
        _isTulip.value = value
        _tulipCount.value = if (value) 1 else 0
    }

    fun roseAvailable(value: Boolean) {
        _isRose.value = value
        _roseCount.value = if (value) 1 else 0
    }

    fun daisyAvailable(value: Boolean) {
        _isDaisy.value = value
        _daisyCount.value = if (value) 1 else 0
    }

    fun isLavenderAvailable(value: Boolean) {
        _isLavender.value = value
        _lavenderCount.value = if (value) 1 else 0
    }

    fun setUpdateName(callback: () -> Unit) {
        updateName = callback
        updateName()
    }

    fun increment() {
        when (currentFlower.value) {
            1 -> _tulipCount.value?.let { _tulipCount.value = it + 1 }
            2 -> _roseCount.value?.let { _roseCount.value = it + 1 }
            3 -> _daisyCount.value?.let { _daisyCount.value = it + 1 }
            4 -> _lavenderCount.value?.let { _lavenderCount.value = it + 1 }
        }
        updateCount()
        calculateTotal()
    }

    fun decrement() {
        when (currentFlower.value) {
            1 -> _tulipCount.value?.let { _tulipCount.value = if (it != 0) it - 1 else 0 }
            2 -> _roseCount.value?.let { _roseCount.value = if (it != 0) it - 1 else 0  }
            3 -> _daisyCount.value?.let { _daisyCount.value = if (it != 0) it - 1 else 0  }
            4 -> _lavenderCount.value?.let { _lavenderCount.value =if (it != 0) it - 1 else 0  }
        }
        updateCount()
        calculateTotal()
    }

    fun nextFlower() : Boolean {
        if (!(isTulip.value == true || isLavender.value == true || isDaisy.value == true || isRose.value == true)) {
            return false
        }
        for( i in 1 .. (4 - (_currentFlower.value ?: 4) + 1)) {
            Log.d("TEST0", "currentValue" + _currentFlower.value)
            when (_currentFlower.value) {
                0,1,2,3 -> {
                    _currentFlower.value?.let {
                        _currentFlower.value = it.inc()
                    }
                    updateName()
                    updateCount()
                    Log.d("TEST1", "currentValue" + _currentFlower.value)
                    if (checkNextFlower(_currentFlower.value ?: 1)) {
                        return true
                    }
                }
                4 -> {
                    if (checkNextFlower(_currentFlower.value ?: 1)) {
                        return false
                    }
                }
            }
        }
        prevFlower()
        calculateTotal()
        return false
    }

    private fun updateCount() {
        _currentCount.value = when (_currentFlower.value) {
            1 -> tulipCount.value
            2 -> roseCount.value
            3 -> daisyCount.value
            4 -> lavenderCount.value
            else -> 0
        }
    }

    fun prevFlower(): Boolean {
        if (!(isTulip.value == true || isLavender.value == true || isDaisy.value == true || isRose.value == true)) {
            return false
        }
        for (i in 1 .. (_currentFlower.value ?: 0)){
            Log.d("TESTX0", "currentValue" + _currentFlower.value)
            when (_currentFlower.value){
                4, 3, 2, -> {
                    _currentFlower.value?.let {
                        _currentFlower.value = it - 1
                    }
                    updateName()
                    updateCount()
                    Log.d("TESTX0", "currentValue" + _currentFlower.value)
                    if (checkNextFlower(_currentFlower.value ?: 1)) {
                        return true
                    }
                }
                1 -> {
                    if (checkNextFlower(_currentFlower.value ?: 1)) {
                        return true
                    }
                }
            }

        }
        nextFlower()
        calculateTotal()
        return false
    }
    fun setCurrentName(name: String) {
        _currentName.value = name
    }

    fun closeCurrentFlower() {
        if (_currentFlower.value == 1){
            _currentFlower.value = 0
        } else {
            prevFlower()
        }
    }
    private fun checkNextFlower(order: Int): Boolean {
        return when (order) {
            1 -> isTulip.value ?: false
            2 -> isRose.value ?: false
            3 -> isDaisy.value ?: false
            4 -> isLavender.value ?: false
            else -> false
        }
    }

    fun calculateTotal(){
        _totalPrice.value =  (tulipPrice * (tulipCount.value ?: 0)
                + rosePrice * (roseCount.value ?: 0)
                + daisyPrice * (daisyCount.value ?: 0)
                + lavenderPrice * (lavenderCount.value ?: 0))
    }

    fun resetCurrentFlower() {
        _currentFlower.value = 0
    }
    fun resetOrder(){
        _currentFlower.value = 0
        tulipAvailable(false)
        roseAvailable(false)
        daisyAvailable(false)
        isLavenderAvailable(false)
    }
}