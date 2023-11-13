package cz.damat.thebeercounter.featureCounter.scene.edit

import cz.damat.thebeercounter.commonUI.R
import cz.damat.thebeercounter.commonUI.base.BaseViewModel
import cz.damat.thebeercounter.commonUI.base.State
import cz.damat.thebeercounter.commonlib.room.entity.Product
import cz.damat.thebeercounter.componentCounter.domain.repository.ProductRepository
import kotlinx.coroutines.launch

/**
 * Created by MD on 07.11.22.
 */
class EditScreenViewModel(
    private val productId: Int?,
    private val productRepository: ProductRepository,
) : BaseViewModel<EditViewState, EditEvent, EditCommand>(EditViewState(isForAdding = productId == null)) {

    init {
        ioScope.launch {
            if (productId != null) {
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
            } else {
                updateState {
                    copy(
                        productCount = "1",
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
        currentState().let { state ->
            if (!state.isSaveButtonEnabled) {
                return
            }
            ioScope.launch {
                updateState { copy(state = State.Loading) }

                if (productId != null) {
                    productRepository.updateProductNameAndSetCount(
                        productId,
                        state.productName,
                        state.productCount.toIntOrNull() ?: 0
                    )
                } else {
                    val product = Product(
                        name = state.productName,
                        count = state.productCount.toIntOrNull() ?: 1,
                        shown = true,
                        price = null,
                    )
                    productRepository.saveProduct(product)
                }

                updateState { copy(state = State.Content) }
                sendCommand(EditCommand.NavigateBack)
            }
        }
    }
}
