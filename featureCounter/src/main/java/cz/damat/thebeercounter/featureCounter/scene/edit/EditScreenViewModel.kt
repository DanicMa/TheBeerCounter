package cz.damat.thebeercounter.featureCounter.scene.edit

import android.content.res.Resources
import androidx.annotation.StringRes
import cz.damat.thebeercounter.commonUI.R
import cz.damat.thebeercounter.commonUI.base.BaseViewModel
import cz.damat.thebeercounter.componentCounter.domain.repository.ProductRepository
import cz.damat.thebeercounter.commonlib.room.entity.HistoryItemType
import cz.damat.thebeercounter.commonlib.room.entity.Product
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by MD on 07.11.22.
 */
class EditScreenViewModel(
    private val productId: Int,
    private val productRepository: ProductRepository,
) : BaseViewModel<EditViewState, EditEvent, EditCommand>(EditViewState()) {

    init {
    }

    override fun onEvent(event: EditEvent) {
//        when (event) {
//        }
    }

}
