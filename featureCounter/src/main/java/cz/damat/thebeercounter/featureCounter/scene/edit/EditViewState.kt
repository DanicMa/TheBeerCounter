package cz.damat.thebeercounter.featureCounter.scene.edit

import cz.damat.thebeercounter.commonUI.base.ViewStateDTO
import cz.damat.thebeercounter.commonUI.base.State


/**
 * Created by MD on 07.11.23.
 */
data class EditViewState(
    val state : State = State.Loading,
    val productName: String = "",
    val productCount: Int = 1,
) : ViewStateDTO
