package cz.damat.thebeercounter.scene.counter

import cz.damat.thebeercounter.common.base.BaseViewModel
import cz.damat.thebeercounter.repository.ProductRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


/**
 * Created by MD on 29.12.22.
 */
class CounterScreenViewModel(private val productRepository: ProductRepository) : BaseViewModel<CounterViewState, CounterEvent, CounterCommand>(CounterViewState()) {

    init {
        productRepository.productDao.getShownProductsFlow()
            .onEach { updateState { copy(products = it.toImmutableList()) } }
            .launchIn(ioScope)
    }

    override fun onEvent(event: CounterEvent) {
        when (event) {
            is CounterEvent.OnProductClicked -> onProductClick(event.id)
            is CounterEvent.OnMenuItemClicked -> onMenuItemClick(event.menuItem, event.id)
            is CounterEvent.OnCountSet -> onCountSet(event.id, event.count)
        }
    }

    private fun onProductClick(id: Int) {
        ioScope.launch {
            productRepository.productDao.incrementProductCount(id)
        }
    }

    private fun onMenuItemClick(menuItem: MenuItem, id: Int) {
        ioScope.launch {
            when (menuItem) {
                MenuItem.Reset -> {
                    productRepository.resetProductCount(id)
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
            productRepository.setProductCount(id, count)
        }
    }
}
