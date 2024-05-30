package com.zwc.cocblacklisthelper.module.formationmanage.item

import androidx.databinding.ObservableField
import com.zwc.baselibrary.base.BaseViewModel
import com.zwc.baselibrary.base.MultiItemViewModel
import com.zwc.baselibrary.binding.command.BindingAction
import com.zwc.baselibrary.binding.command.BindingCommand
import com.zwc.databaselibrary.entity.Formation
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FormationItemViewModel(
    viewModel: BaseViewModel<*>,
    val data: Formation,
    onLongCLick: (item: FormationItemViewModel) -> Unit
) :
    MultiItemViewModel<BaseViewModel<*>>(viewModel) {
    var imageObservableFile = ObservableField(data.imageFilePath)
    var descriptionObservableFile = ObservableField(data.description)
    var typeObservableFile = ObservableField(getTypeText())
    var timeObservableFile = ObservableField(getDateByMillisecond(data.dataTime,"yyyy-MM-dd HH:mm:ss"))
    var onLongClickCommand: BindingCommand<*> = BindingCommand<Any?>(BindingAction {
        onLongCLick(this)
    })

    private fun getTypeText(): String {
        // 0:冲杯，1:联赛，2:部落战，3：艺术，4：牛批
        return when (data.type) {
            0 -> "冲杯"
            1 -> "联赛"
            2 -> "部落战"
            3 -> "艺术"
            4 -> "牛批"
            else -> "未知"
        }

    }

    fun getDateByMillisecond(millionSeconds: Long, dateFormat: String): String {
        val sDateFormat = SimpleDateFormat(dateFormat, Locale.CHINA)
        val curDateTime = Date(millionSeconds)
        return sDateFormat.format(curDateTime)
    }
}