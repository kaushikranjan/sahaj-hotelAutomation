package com.sahaj.hotelautomation.constants

import com.sahaj.hotelautomation.models.Floor

object PowerConsumptionUtility {

  def maxPowerConsumption(mainCorridors: Int, subCorridors: Int) = {
    (mainCorridors * Configs.MAIN_CORRIDOR_POWER) + (subCorridors * Configs.SUB_CORRIDOR_POWER)
  }

  def powerBeingConsumedInAFloor(floor: Floor) = {
    val powerConsumptionAcrossMainCorridors = floor.mainCorridors.map(x => {
      x.appliances.filter(app => app.getCurrentState())
        .map(y => y.powerConsumption).foldLeft(0.toDouble){(a,b) => a+b}
    }).foldLeft(0.toDouble){(a,b) => a+b}
    val powerConsumptionAcrossSubCorridors = floor.subCorridors.map(x => {
      x.appliances.filter(app => app.getCurrentState())
        .map(y => y.powerConsumption).foldLeft(0.toDouble){(a,b) => a+b}
    }).foldLeft(0.toDouble){(a,b) => a+b}

    powerConsumptionAcrossMainCorridors + powerConsumptionAcrossSubCorridors
  }

}
