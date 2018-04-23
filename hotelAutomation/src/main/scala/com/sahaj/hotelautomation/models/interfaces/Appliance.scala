package com.sahaj.hotelautomation.models.interfaces

import com.sahaj.hotelautomation.models.Time
import com.sahaj.hotelautomation.constants.{ApplianceType, CorridorType}

abstract class Appliance(val id: String, val operationStartTime: Time, val operationEndTime: Time,
                         val powerConsumption: Double, val applianceType: ApplianceType.Value,
                         val floorNumber: Int, val corridorType: CorridorType.Value, val corridorIndex: Int) {

  private var stateChangeEnabled: Boolean = false
  private var currentState: Boolean = false

  def switchOff() = {
    this.currentState = false
  }

  def switchOn() = {
    this.currentState = true
  }

  def getCurrentState() = {
    this.currentState
  }

  def disableChange() = {
    this.stateChangeEnabled = false
  }

  def enableChange() = {
    this.stateChangeEnabled = true
  }

  def getStateChangeEnabled() = {
    this.stateChangeEnabled
  }

}
