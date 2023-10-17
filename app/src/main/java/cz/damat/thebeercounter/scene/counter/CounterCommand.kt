package cz.damat.thebeercounter.scene.counter

import cz.damat.thebeercounter.commonUI.base.ViewCommand
import cz.damat.thebeercounter.room.entity.Product


/**
 * Created by MD on 23.04.23.
 */
sealed class CounterCommand : ViewCommand {

    object ShowClearAllConfirmDialog : CounterCommand()
    object ShowAddNewDialog : CounterCommand()
    object PerformHapticFeedback : CounterCommand()
    data class ShowSetCountDialog(val product: Product) : CounterCommand()
}
