package cz.damat.thebeercounter.featureCounter.scene.counter

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
 * Created by MD on 29.12.22.
 */
@StringRes
private val InitialProductName = R.string.beer

class CounterScreenViewModel(
    private val productRepository: ProductRepository,
    private val resources: Resources
) : BaseViewModel<CounterViewState, CounterEvent, CounterCommand>(CounterViewState()) {

    init {
        val shownProductsFlow = productRepository.getShownProductsFlow()

        ioScope.launch {
            if (shownProductsFlow.first().isEmpty()) {
                // if there are no products in the database, add the initial one
                productRepository.addInitialProduct(resources.getString(InitialProductName))
            }
        }

        shownProductsFlow
            .onEach { updateState { copy(products = it.toImmutableList()) } }
            .launchIn(ioScope)
    }

    override fun onEvent(event: CounterEvent) {
        when (event) {
            is CounterEvent.OnProductClicked -> onProductClick(event.id)
            is CounterEvent.OnMenuItemClicked -> onMenuItemClick(event.menuItem, event.id)
            is CounterEvent.OnCountSet -> onCountSet(event.id, event.count)
            CounterEvent.OnClearAllClicked -> onClearAllClicked()
            CounterEvent.OnClearAllConfirmed -> onClearAllConfirmed()
            CounterEvent.OnAddNewClicked -> onAddNewClicked()
            is CounterEvent.OnNewProductAdded -> onNewProductAdded(event.name)
        }
    }

    private fun onProductClick(id: Int) {
        ioScope.launch {
            productRepository.incrementProductCount(id)
            sendCommand(CounterCommand.PerformHapticFeedback)
        }
    }

    private fun onMenuItemClick(menuItem: MenuItem, id: Int) {
        ioScope.launch {
            when (menuItem) {
                MenuItem.Edit -> {
                    defaultScope.launch {
                        sendCommand(CounterCommand.OpenEdit(id))
                    }
                }
                MenuItem.Reset -> {
                    productRepository.setProductCount(id, 0, HistoryItemType.RESET)
                }
                MenuItem.Hide -> {
                    productRepository.hideProduct(id)
                }
                MenuItem.SetCount -> {
                    currentState().products?.firstOrNull { it.id == id }?.let {
                        defaultScope.launch {
                            sendCommand(CounterCommand.ShowSetCountDialog(it))
                        }
                    }
                }
            }
        }
    }

    private fun onCountSet(id: Int, count: Int) {
        ioScope.launch {
            productRepository.setProductCount(id, count, HistoryItemType.MANUAL)
        }
    }

    private fun onClearAllClicked() {
        defaultScope.launch {
            sendCommand(CounterCommand.ShowClearAllConfirmDialog)
        }
    }

    private fun onClearAllConfirmed() {
       ioScope.launch {
              productRepository.clearAllAndAddInitialProduct(resources.getString(InitialProductName))
       }
    }

    private fun onAddNewClicked() {
        ioScope.launch {
            productRepository.getShownProductsFlow()
            sendCommand(CounterCommand.ShowAddNewDialog)
        }
    }

    private fun onNewProductAdded(name: String) {
        ioScope.launch {
            val product = Product(
                name = name,
                count = 0,
                shown = true,
                price = null,
            )
            productRepository.saveProductAndIncrementCount(product)
        }
    }
}
