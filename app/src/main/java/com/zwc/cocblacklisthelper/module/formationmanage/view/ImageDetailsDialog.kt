package com.zwc.cocblacklisthelper.module.formationmanage.view

import android.graphics.Bitmap
import android.graphics.PointF
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.FragmentActivity
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.ImageViewState
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.zwc.cocblacklisthelper.databinding.DialogImageDetailsLayoutBinding
import com.zwc.viewdialog.ViewDialog

class ImageDetailsDialog(
    val activity: FragmentActivity,
    val data: String,
) {
    private val binding: DialogImageDetailsLayoutBinding
    private val mViewDialog: ViewDialog

    init {
        val viewGroup = activity.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        binding = DialogImageDetailsLayoutBinding.inflate(activity.layoutInflater, viewGroup, false)
        mViewDialog =
            ViewDialog.Builder(activity).setParentView(viewGroup).setContentView(binding.root)
                .setCancelable(true).create()
        initData()
    }

    private fun initData() {
        /*        Glide.with(activity).asBitmap().load(data)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            displayLongPic(resource, binding.longImg)

                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }

                    })*/
        displayLongPic1(binding.longImg)
        binding.longImg.setOnClickListener {
            hide()
        }
    }

    private fun displayLongPic(bitmap: Bitmap, longImg: SubsamplingScaleImageView) {
        longImg.isQuickScaleEnabled = true
        longImg.isZoomEnabled = true
        longImg.isPanEnabled = true
        longImg.maxScale = 3f
        longImg.setDoubleTapZoomDuration(100)
        longImg.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE)
        longImg.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
        longImg.setImage(ImageSource.bitmap(bitmap), ImageViewState(0f, PointF(0f, 0f), 0))
    }

    private fun displayLongPic1(longImg: SubsamplingScaleImageView) {
        longImg.isQuickScaleEnabled = true
        longImg.isZoomEnabled = true
        longImg.isPanEnabled = true
        longImg.maxScale = 3f
        longImg.setDoubleTapZoomDuration(100)
        longImg.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE)
        longImg.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
        longImg.setImage(ImageSource.uri(data))
    }

    fun show() {
        mViewDialog.show()
    }

    fun hide() {
        mViewDialog.hide(false)
    }
}