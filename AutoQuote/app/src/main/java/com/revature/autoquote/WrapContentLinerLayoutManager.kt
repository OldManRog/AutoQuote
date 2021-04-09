package com.revature.autoquote.database

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

//This class only exists because I couldn't find another way to catch the error cause by thread-unsafe operations
class MyLinearLayoutManager(var context:Context?) : LinearLayoutManager(context) {
    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.e("THREAD ERROR", "Inconsistent State error caught. No Action Necessary")
        }
    }
}