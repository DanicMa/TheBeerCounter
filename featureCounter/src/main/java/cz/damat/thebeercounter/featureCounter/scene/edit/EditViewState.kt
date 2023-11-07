package cz.damat.thebeercounter.featureCounter.scene.edit

import cz.damat.thebeercounter.commonUI.base.ViewState
import cz.damat.thebeercounter.commonlib.room.entity.Product
import kotlinx.collections.immutable.ImmutableList


/**
 * Created by MD on 07.11.23.
 */
data class EditViewState(
    val productName: String = "",
    val productCount: Int = 1,
) : ViewState
