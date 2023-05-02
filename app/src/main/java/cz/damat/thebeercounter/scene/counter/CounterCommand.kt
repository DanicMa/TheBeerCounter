package cz.damat.thebeercounter.scene.counter

import cz.damat.thebeercounter.common.base.ViewCommand
import cz.damat.thebeercounter.room.model.Product


/**
 * Created by MD on 23.04.23.
 */
sealed class CounterCommand : ViewCommand {

    object ShowClearAllConfirmDialog : CounterCommand()
    data class ShowSetCountDialog(val product: Product) : CounterCommand()
}
