package com.sahaj.hotelautomation.models.commandModels

import com.sahaj.hotelautomation.constants.ActionSet.ActionSet
import com.sahaj.hotelautomation.models.interfaces.Command

case class NoMovementCommand(command: ActionSet, attributes: MovementAttribs) extends Command(command, attributes)