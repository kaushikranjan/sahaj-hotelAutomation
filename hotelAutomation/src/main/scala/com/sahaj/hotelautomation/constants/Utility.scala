package com.sahaj.hotelautomation.constants

import com.sahaj.hotelautomation.models.Hotel

object Utility {

  private def stateOfAppliance(state: Boolean) = {
    if (state) "ON" else "OFF"
  }
  def printObject(hotel: Hotel, command : ActionSet.Value) = {
    val floors = hotel.floors
    println(command.toString)
    var i = 1
    for (floor <- floors) {
      println(s"Floor $i")
      i += 1

      var j = 1
      for (mainCorr <- floor.mainCorridors) {
        val mainCorrAppliances = mainCorr.appliances.map(x => {
          s"${x.applianceType.toString} : ${stateOfAppliance(x.getCurrentState())}"
        }).mkString(" ")

        println(s"Main Corridor $j $mainCorrAppliances")
        j += 1
      }

      j = 1
      for (subCorr <- floor.subCorridors) {
        val subCorrAppliances = subCorr.appliances.map(x => {
          s"${x.applianceType.toString} : ${stateOfAppliance(x.getCurrentState())}"
        }).mkString(" ")

        println(s"Sub Corridor $j $subCorrAppliances")
        j += 1
      }
    }

    println("------------------")
    println("")
  }
}