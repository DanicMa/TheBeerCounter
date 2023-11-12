package cz.damat.thebeercounter.featureCounter.scene.counter

import cz.damat.thebeercounter.commonUI.base.ViewStateDTO
import cz.damat.thebeercounter.commonlib.room.entity.Product
import kotlinx.collections.immutable.ImmutableList


/**
 * Created by MD on 23.04.23.
 */
data class CounterViewState(
    val products : ImmutableList<Product>? = null
) : ViewStateDTO
