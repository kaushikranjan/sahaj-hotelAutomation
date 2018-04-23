package com.sahaj.hotelautomation.models.interfaces

import com.sahaj.hotelautomation.constants.ActionSet.ActionSet
import com.sahaj.hotelautomation.models.Hotel

abstract class Action(id: ActionSet) {
  def instantiate(mainCorridorLength: Int, subCorridorLength: Int, floorLength: Int) : Option[Hotel]
  def execute(hotel: Hotel, floor: Int, subCorridor: Int) : Option[Hotel]
}
