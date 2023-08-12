package com.example.dictionary.common.helpers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R

abstract class SwipeToDeleteTouchHelper (
   context: Context
): ItemTouchHelper.SimpleCallback(
    0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
){
    private val deleteIcon = ContextCompat.getDrawable(context,R.drawable.baseline_delete_white_24)!!
    private val background = ColorDrawable(Color.RED)
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        return false
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
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val view = viewHolder.itemView
        val backgroundCornerOffset = 0

        val iconMargin = (view.height - deleteIcon.intrinsicHeight) /2
        val iconTop = view.top + ((view.height - deleteIcon.intrinsicHeight) / 2)
        val iconBottom = iconTop + deleteIcon.intrinsicHeight

        if(dX > 0){ //swipe to right

            val iconLeft = view.left + iconMargin
            val iconRight = iconLeft  + deleteIcon.intrinsicWidth
            deleteIcon.setBounds(iconLeft,iconTop,iconRight,iconBottom)

            background.setBounds(
                view.left,
                view.top,
                view.left + (dX.toInt()) + backgroundCornerOffset,
                view.bottom
            )
        }else if(dX < 0) { // swipe to left

            val iconRight = view.right - iconMargin
            val iconLeft = iconRight - deleteIcon.intrinsicWidth

            deleteIcon.setBounds(iconLeft,iconTop,iconRight,iconBottom)

            background.setBounds(
                view.right + (dX.toInt()) - backgroundCornerOffset,
                view.top,
                view.right,
                view.bottom
            )

        }else {
            background.setBounds(0,0,0,0)
            deleteIcon.setBounds(0,0,0,0)
        }

        background.draw(c)
        deleteIcon.draw(c)
    }


}