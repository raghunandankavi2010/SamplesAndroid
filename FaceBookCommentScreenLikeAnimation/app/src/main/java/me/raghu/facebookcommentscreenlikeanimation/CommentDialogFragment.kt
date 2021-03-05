package me.raghu.facebookcommentscreenlikeanimation

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import kotlinx.android.synthetic.main.facebook_comment.*


class CommentDialogFragment : DialogFragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var dataset: Array<String>
    private var isScrolling: Boolean = false
    private var isUp: Boolean = false
    private var isDown: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(
            DialogFragment.STYLE_NORMAL,
            android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen
        )
        return super.onCreateDialog(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initDataset()
        return inflater.inflate(R.layout.facebook_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBounceScrollView.damping = 1.0f
        linearLayoutManager = LinearLayoutManager(activity)

        with(rv) {
            layoutManager = linearLayoutManager
            adapter = CustomAdapter(dataset)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }

        mBounceScrollView.setOnDismissListener(object : BounceScrollView.OnDismissListener {
            override fun onDismiss(dimissable: Boolean) {
                if (dimissable)
                    this@CommentDialogFragment.dismiss()
            }
        })


        var isSrollingUp = false
        var isSrollingDown = false
        var canSwipe = false
        var y = 0

     rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                 y = dy
               isSrollingUp = dy > 0
                    val lastItem = linearLayoutManager.findLastVisibleItemPosition()
                val firstItem: Int = linearLayoutManager.findFirstCompletelyVisibleItemPosition()

                // first item and scrolling up
                if( firstItem == 0 && dy > 0) {
                    mBounceScrollView.requestDisallowInterceptTouchEvent(false)
                    Log.i("ScrollView can scroll", "" + false)
                    mBounceScrollView.setScrollable(false)
                } else if( firstItem == 0 && dy < 0) {
                    mBounceScrollView.requestDisallowInterceptTouchEvent(true)
                    Log.i("ScrollView can scroll", "" + true)
                    mBounceScrollView.setScrollable(true)
                }

                // last item and scrolling down
             /*   if( lastItem == dataset.size - 1 && dy < 0) {
                    mBounceScrollView.setScrollable(false)
                } else if( lastItem == dataset.size - 1 && dy > 0) {
                    mBounceScrollView.setScrollable(true)
                }*/

            /*  if (lastItem == dataset.size - 1 || firstItem == 0) {
                   mBounceScrollView.setScrollable(true)
                    canSwipe = true
                    Log.i("ScrollView can scroll", "" + true)
                } else {
                    mBounceScrollView.setScrollable(false)
                     canSwipe = false
                    Log.i("ScrollView can scroll", "" + false)
                }*/
            }

         override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
             super.onScrollStateChanged(recyclerView, newState)

             if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {

             } else if (newState == SCROLL_STATE_DRAGGING) {

             } else {

             }
         }
        })




          /*  override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                } else {
                    // Do something
                }
            }*/

    }

    private fun initDataset() {
        dataset = Array(DATASET_COUNT) { i -> "This is element # $i" }
    }

    companion object {
        private val DATASET_COUNT = 60
    }
}