package cz.damat.thebeercounter.featureCounter.scene.edit

import cz.damat.thebeercounter.commonUI.base.ViewCommand
import cz.damat.thebeercounter.commonlib.room.entity.Product


/**
 * Created by MD on 07.11.23.
 */
sealed class EditCommand : ViewCommand {

    object NavigateBack : EditCommand()
}
