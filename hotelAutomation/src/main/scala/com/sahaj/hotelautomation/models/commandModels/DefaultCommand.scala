package com.sahaj.hotelautomation.models.commandModels

import com.sahaj.hotelautomation.constants.ActionSet.ActionSet
import com.sahaj.hotelautomation.models.interfaces.Command

case class DefaultCommand(command: ActionSet, attributes: DefaultAttribs) extends Command(command, attributes)