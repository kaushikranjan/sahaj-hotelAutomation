package com.sahaj.hotelautomation.services.actions

import java.util.UUID

import com.sahaj.hotelautomation.constants.{ActionSet, Configs, CorridorType}
import com.sahaj.hotelautomation.models._
import com.sahaj.hotelautomation.models.interfaces.{Action, Appliance, Corridor}

object DefaultAction extends Action(id = ActionSet.DEFAULT){

  private def applianceListForCorridor(
               lightInfo: (Int, Double, Boolean, Boolean),
               acInfo: (Int, Double, Boolean, Boolean),
               floorIndex: Int, corridorType: CorridorType.Value, corridorIndex: Int) = {

    val lights = new Array[Appliance](lightInfo._1)
    val acs = new Array[Appliance](acInfo._1)

    var i = 0
    for (i <- 0 to lightInfo._1-1) {
      lights(i) = Light(UUID.randomUUID().toString, null, null, lightInfo._2, floorIndex, corridorType, corridorIndex)
      if (lightInfo._3)
        lights(i).switchOn()
      if (lightInfo._4)
        lights(i).enableChange()
    }

    for (i <- 0 to acInfo._1-1) {
      acs(i) = AC(UUID.randomUUID().toString, null, null, acInfo._2, floorIndex, corridorType, corridorIndex)
      if (acInfo._3)
        acs(i).switchOn()
      if (acInfo._4)
        acs(i).enableChange()
    }

    lights.++(acs)
  }

  private def corridorListForFloor(lightInfo: (Int, Double, Boolean, Boolean),
                           acInfo: (Int, Double, Boolean, Boolean), corridorLength: Int,
                           floorIndex: Int, corridorType: CorridorType.Value) = {
    val corridors = new Array[Corridor](corridorLength)
    var i = 0
    if (corridorType == CorridorType.MAIN_CORRIDOR) {
      for (i <- 0 to corridorLength-1) {
        val appliances = applianceListForCorridor(lightInfo, acInfo, floorIndex, CorridorType.MAIN_CORRIDOR, i)
        corridors(i) = MainCorridor(i.toString, appliances)
      }
    } else if (corridorType == CorridorType.SUB_CORRIDOR) {
      for (i <- 0 to corridorLength-1) {
        val appliances = applianceListForCorridor(lightInfo, acInfo, floorIndex, CorridorType.SUB_CORRIDOR, i)
        corridors(i) = SubCorridor(i.toString, appliances)
      }
    }
    corridors
  }

  override def execute(hotel: Hotel, floor: Int, subCorridor: Int): Option[Hotel] = None

  override def instantiate(mainCorridorLength: Int,
                       subCorridorLength: Int, floorLength: Int) : Option[Hotel] = {
    val mainCorridorLight = (Configs.MAIN_CORRIDOR_NO_OF_LIGHT, Configs.LIGHT_POWER, true, false)
    val mainCorridorAC = (Configs.MAIN_CORRIDOR_NO_OF_AC, Configs.AC_POWER, true, false)

    val subCorridorLight = (Configs.SUB_CORRIDOR_NO_OF_LIGHT, Configs.LIGHT_POWER, false, true)
    val subCorridorAC = (Configs.SUB_CORRIDOR_NO_OF_AC, Configs.AC_POWER, true, true)


    val floors = new Array[Floor](floorLength)
    var i = 0
    for (i <- 0 until floorLength) {
      val mainCorridors = corridorListForFloor(mainCorridorLight, mainCorridorAC, mainCorridorLength, i, CorridorType.MAIN_CORRIDOR)
      val subCorridors = corridorListForFloor(subCorridorLight, subCorridorAC, subCorridorLength, i, CorridorType.SUB_CORRIDOR)
      floors(i) = Floor(mainCorridors, subCorridors)
    }
    Some(Hotel(floors))

  }

}
