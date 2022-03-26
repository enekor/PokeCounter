package com.example.rachasevo.ui.listado.adaptador

import android.graphics.Canvas
import android.graphics.Color
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.rachasevo.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

open class SwipeGesture(): ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(
            c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
        ).addBackgroundColor(Color.RED)
            .addActionIcon(R.drawable.ic_delete)
            .create().decorate()
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

}