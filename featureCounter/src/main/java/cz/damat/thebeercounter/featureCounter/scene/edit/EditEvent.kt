package cz.damat.thebeercounter.featureCounter.scene.edit

import cz.damat.thebeercounter.commonUI.base.ViewEvent


/**
 * Created by MD on 07.11.23.
 */
internal typealias OnEvent = (EditEvent) -> Unit

sealed class EditEvent : ViewEvent {
}
