package com.sahaj.hotelautomation.services.actions

import com.sahaj.hotelautomation.constants.{ActionSet, ApplianceType, CorridorType, PowerConsumptionUtility}
import com.sahaj.hotelautomation.models.Hotel
import com.sahaj.hotelautomation.models.interfaces.{Action, Appliance}

object NoMovementAction extends Action(id = ActionSet.MOVEMENT){

  override def execute(hotel: Hotel, floorNumber: Int, subCorridorNumber: Int): Option[Hotel] = {
    //(Number of Main corridors * 15) + (Number of sub corridors * 10)
    val floor = hotel.floors(floorNumber-1)
    val subCorridor = hotel.floors.apply(floorNumber-1).subCorridors.apply(subCorridorNumber-1)

    val modifiedAppliances = subCorridor.appliances
      .filter(x => x.applianceType == ApplianceType.LIGHT)
      .map((x) => {
        x.enableChange()
        x.switchOff()
        subCorridor.appliances.indexWhere(app => app.id == x.id) -> x
      })

    modifiedAppliances.foreach(x =>
      if (x._2.corridorType == CorridorType.MAIN_CORRIDOR)
        hotel.floors(x._2.floorNumber).mainCorridors(x._2.corridorIndex).appliances(x._1) = x._2
      else if (x._2.corridorType == CorridorType.SUB_CORRIDOR)
        hotel.floors(x._2.floorNumber).subCorridors(x._2.corridorIndex).appliances(x._1) = x._2
    )


    val thresholdPower = PowerConsumptionUtility.maxPowerConsumption(floor.mainCorridors.length, floor.subCorridors.length)
    var powerConsumed = PowerConsumptionUtility.powerBeingConsumedInAFloor(floor)
    var powerSurge = powerConsumed - thresholdPower
    val appliances = floor.subCorridors
      .map(x => x.appliances.filter(app =>
        !app.getCurrentState() && app.getStateChangeEnabled() && (app.applianceType != ApplianceType.LIGHT)))
      .foldLeft(Array[Appliance]()){(a,b) => a.++(b)}


    for (app <- appliances) {
      if (powerSurge + app.powerConsumption <= 0) {
        powerSurge += app.powerConsumption
        app.switchOn()
        if (app.corridorType == CorridorType.MAIN_CORRIDOR) {
          val index = hotel.floors(app.floorNumber).mainCorridors(app.corridorIndex).appliances.indexWhere(x => x.id == app.id)
          hotel.floors(app.floorNumber).mainCorridors(app.corridorIndex).appliances(index) = app
        }
        else if (app.corridorType == CorridorType.SUB_CORRIDOR) {
          val index = hotel.floors(app.floorNumber).subCorridors(app.corridorIndex).appliances.indexWhere(x => x.id == app.id)
          hotel.floors(app.floorNumber).subCorridors(app.corridorIndex).appliances(index) = app
        }
      }
    }
    None
  }

  override def instantiate(mainCorridorLength: Int,
                           subCorridorLength: Int, floorLength: Int) : Option[Hotel] = None
}
