package cz.damat.thebeercounter.scene.counter

import android.content.res.Resources
import androidx.annotation.StringRes
import cz.damat.thebeercounter.R
import cz.damat.thebeercounter.common.base.BaseViewModel
import cz.damat.thebeercounter.repository.ProductRepository
import cz.damat.thebeercounter.room.model.HistoryItemType
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by MD on 29.12.22.
 */
@StringRes
private const val InitialProductName = R.string.beer

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
        }
    }

    private fun onProductClick(id: Int) {
        ioScope.launch {
            productRepository.incrementProductCount(id)
        }
    }

    private fun onMenuItemClick(menuItem: MenuItem, id: Int) {
        ioScope.launch {
            when (menuItem) {
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
}
