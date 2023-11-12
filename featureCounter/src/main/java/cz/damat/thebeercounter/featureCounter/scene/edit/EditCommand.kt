package cz.damat.thebeercounter.featureCounter.scene.edit

import cz.damat.thebeercounter.commonUI.base.ViewCommand


/**
 * Created by MD on 07.11.23.
 */
sealed class EditCommand : ViewCommand {

    object NavigateBack : EditCommand()
}
