package cz.damat.thebeercounter.scene.counter

import cz.damat.thebeercounter.common.base.ViewState
import cz.damat.thebeercounter.model.db.Drink
import kotlinx.collections.immutable.ImmutableList


/**
 * Created by MD on 23.04.23.
 */
data class CounterViewState(
    val drinks : ImmutableList<Drink>? = null
) : ViewState
