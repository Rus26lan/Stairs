package com.rundgrun.stairs.ui.presentation

import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener

open class SeekBarAdapter(private val action: (progress: Int) -> Unit) : OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        action.invoke(progress)
    }
    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }
    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
}