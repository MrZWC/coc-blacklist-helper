package com.zwc.cocblacklisthelper.module.formationmanage

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.zwc.baselibrary.base.BaseActivity
import com.zwc.cocblacklisthelper.BR
import com.zwc.cocblacklisthelper.R
import com.zwc.cocblacklisthelper.databinding.ActivityFormationManageBinding
import com.zwc.cocblacklisthelper.module.formationmanage.view.AddFormationDialog
import com.zwc.cocblacklisthelper.module.formationmanage.view.BaseScreen
import com.zwc.cocblacklisthelper.module.formationmanage.view.ImageDetailsDialog
import com.zwc.cocblacklisthelper.module.formationmanage.view.ScreenMessagePupWindow
import com.zwc.cocblacklisthelper.widget.dialog.FormationMenuDialog
import io.github.idonans.core.util.DimenUtil
import io.github.idonans.lang.util.ViewUtil

/**
 *阵型管理界面
 * @constructor Create empty Formation manage activity
 */
class FormationManageActivity :
    BaseActivity<ActivityFormationManageBinding, FormationManageViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private var addDialog: AddFormationDialog? = null
    private val screenStatusList = mutableListOf<BaseScreen>()
    private var selectScreenStatusBean: BaseScreen? = null
    override fun initData() {
        super.initData()
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
            ) {
                outRect.top = DimenUtil.dp2px(4f)
            }
        })
        //0:冲杯，1:联赛，2:部落战，3：艺术，4：牛批
        screenStatusList.add(BaseScreen(true, "全部", -1))
        screenStatusList.add(BaseScreen(false, "冲杯", 0))
        screenStatusList.add(BaseScreen(false, "联赛", 1))
        screenStatusList.add(BaseScreen(false, "部落战", 2))
        screenStatusList.add(BaseScreen(false, "艺术", 3))
        screenStatusList.add(BaseScreen(false, "牛批", 4))
        screenStatusList.add(BaseScreen(false, "网红", 5))
        binding.screenStatusBtn.setText("全部")
        ViewUtil.onClick(binding.screenStatusBtn) {
            val messagePupWindow = ScreenMessagePupWindow(this@FormationManageActivity)
            messagePupWindow.showPopupWindow(binding.screenStatusBtn, screenStatusList, 0)
            messagePupWindow.setOnItemClickListener(object :
                ScreenMessagePupWindow.OnItemClickListener {
                override fun onItemClick(screen: BaseScreen) {
                    if (screen.data.equals(selectScreenStatusBean?.data)) {
                        return
                    }
                    for (baseScreen in screenStatusList) {
                        baseScreen.selected = false
                    }
                    screen.selected = true
                    selectScreenStatusBean = screen
                    binding.screenStatusBtn.setText(screen.title)
                    viewModel.loadData(screen.data)
                }
            })
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.uc.showAddDialogObservable.observe(this) {
            addDialog = AddFormationDialog(this) {
                viewModel.loadData()
            }
            addDialog!!.show()
        }
        viewModel.uc.showEditDialogObservable.observe(this) {
            val dialog = FormationMenuDialog(this, it.data) {
                viewModel.delete(it)
            }
            dialog.show()
        }
        viewModel.uc.showImageDetailDialogObservable.observe(this) {
            val dialog = ImageDetailsDialog(this, it)
            dialog.show()
        }
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_formation_manage
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (addDialog != null) {
            addDialog!!.onActivityResult(requestCode, resultCode, data)
        }
    }
}