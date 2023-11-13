package cz.damat.thebeercounter.featureCounter.scene.edit

import cz.damat.thebeercounter.commonUI.R
import cz.damat.thebeercounter.commonUI.base.BaseViewModel
import cz.damat.thebeercounter.commonUI.base.State
import cz.damat.thebeercounter.commonlib.room.entity.Product
import cz.damat.thebeercounter.componentCounter.domain.repository.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by MD on 07.11.22.
 */
class EditScreenViewModel(
    private val productId: Int?,
    private val productRepository: ProductRepository,
) : BaseViewModel<EditViewState, EditEvent, EditCommand>(EditViewState()) {

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
        currentState().let {
            ioScope.launch {
                updateState { copy(state = State.Loading) }

                val count = it.productCount.toIntOrNull() ?: 0 // todo validations

                if (productId != null) {
                    productRepository.updateProductNameAndSetCount(
                        productId,
                        it.productName,
                        count
                    )
                } else {
                    val product = Product(
                        name = it.productName,
                        count = count,
                        shown = true,
                        price = null,
                    )
                    productRepository.saveProductAndIncrementCount(product)
                }

                updateState { copy(state = State.Content) }
                sendCommand(EditCommand.NavigateBack)
            }
        }
    }
}
