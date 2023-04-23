package cz.damat.thebeercounter.scene.counter

import cz.damat.thebeercounter.common.base.BaseViewModel
import cz.damat.thebeercounter.repository.ProductRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * Created by MD on 29.12.22.
 */
class CounterScreenViewModel(val productRepository: ProductRepository) : BaseViewModel<CounterViewState, CounterEvent, CounterCommand>(CounterViewState()) {

    init {
        //todo
        productRepository.productDao.getShownProductsFlow()
            .onEach { updateState { copy(products = it.toImmutableList()) } }
            .launchIn(ioScope)

    }

    override fun onEvent(event: CounterEvent) {
        //todo
    }
}