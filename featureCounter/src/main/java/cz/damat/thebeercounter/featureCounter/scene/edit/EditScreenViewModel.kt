package cz.damat.thebeercounter.featureCounter.scene.edit

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import cz.damat.thebeercounter.commonUI.R
import cz.damat.thebeercounter.commonUI.base.BaseViewModel
import cz.damat.thebeercounter.commonUI.base.State
import cz.damat.thebeercounter.componentCounter.domain.repository.ProductRepository
import cz.damat.thebeercounter.commonlib.room.entity.HistoryItemType
import cz.damat.thebeercounter.commonlib.room.entity.Product
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
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
        ioScope.launch {
            delay(3000) //todo delete after debug

            val product = productRepository.getProduct(productId)

            if (product == null) {
                updateState { copy(state = State.Error(R.string.error_product_not_found)) }
            } else {
                updateState {
                    copy(
                        productName = product.name,
                        productCount = product.count.toString(),
                        state = State.Content
                    )
                }
            }
        }
    }

    override fun onEvent(event: EditEvent) {
        when (event) {
            is EditEvent.OnProductNameChange -> updateState { copy(productName = event.productName) }
            is EditEvent.OnProductCountChange -> updateState { copy(productCount = event.productCount) }
            EditEvent.OnBackClick -> sendCommand(EditCommand.NavigateBack) //todo confirm changes discard dialog
            EditEvent.OnSaveClick -> save()
        }
    }

    private fun save() {
        currentState().let {
            ioScope.launch {
                updateState { copy(state = State.Loading) }

                delay(3000) //todo delete after debug
                productRepository.updateProductNameAndSetCount(
                    productId,
                    it.productName,
                    it.productCount.toIntOrNull() ?: 0 //todo validations
                )

                updateState { copy(state = State.Content) }
                sendCommand(EditCommand.NavigateBack)
            }
        }
    }
}
