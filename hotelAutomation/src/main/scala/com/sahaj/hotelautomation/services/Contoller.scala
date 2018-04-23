package com.sahaj.hotelautomation.services

import com.sahaj.hotelautomation.models.Hotel
import com.sahaj.hotelautomation.constants.{ActionSet, Utility}
import com.sahaj.hotelautomation.models.commandModels.{DefaultCommand, MovementCommand, NoMovementCommand}
import com.sahaj.hotelautomation.models.interfaces.Command
import com.sahaj.hotelautomation.services.actions.{DefaultAction, MovementAction, NoMovementAction}


object Contoller {

  var hotel : Hotel = null

  def run(cmd: Command) = {
    cmd match {
      case DefaultCommand(ActionSet.DEFAULT, x) =>
        hotel = DefaultAction.instantiate(x.mainCorridors, x.subCorridors, x.floor).get
        Utility.printObject(hotel, ActionSet.DEFAULT)
      case MovementCommand(ActionSet.MOVEMENT, x) =>
        MovementAction.execute(hotel, x.floor, x.subCorridor)
        Utility.printObject(hotel, ActionSet.MOVEMENT)
      case NoMovementCommand(ActionSet.NO_MOVEMENT, x) =>
        NoMovementAction.execute(hotel, x.floor, x.subCorridor)
        Utility.printObject(hotel, ActionSet.NO_MOVEMENT)
      case x => {
        throw new Exception("Invalid action")
      }
    }

  }
}
