package cz.damat.thebeercounter.scene.counter

import cz.damat.thebeercounter.common.base.ViewState
import cz.damat.thebeercounter.room.model.Product
import kotlinx.collections.immutable.ImmutableList


/**
 * Created by MD on 23.04.23.
 */
data class CounterViewState(
    val products : ImmutableList<Product>? = null
) : ViewState
