package cz.damat.thebeercounter.featureCounter.scene.counter

import cz.damat.thebeercounter.commonUI.base.ViewCommand
import cz.damat.thebeercounter.commonlib.room.entity.Product


/**
 * Created by MD on 23.04.23.
 */
sealed class CounterCommand : ViewCommand {

    object ShowClearAllConfirmDialog : CounterCommand()
    object ShowAddNewDialog : CounterCommand()
    object PerformHapticFeedback : CounterCommand()
    data class ShowSetCountDialog(val product: Product) : CounterCommand()
    data class OpenEdit(val productId: Int) : CounterCommand()
}
