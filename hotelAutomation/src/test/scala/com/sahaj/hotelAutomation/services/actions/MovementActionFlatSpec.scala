package com.sahaj.hotelAutomation.services.actions

import com.sahaj.hotelautomation.constants.{ApplianceType, PowerConsumptionUtility}
import com.sahaj.hotelautomation.models.Hotel
import com.sahaj.hotelautomation.services.actions.{DefaultAction, MovementAction}
import org.scalatest.fixture

class MovementActionFlatSpec  extends fixture.FlatSpec {

  case class F(result_success : Hotel)

  override type FixtureParam = F

  def withFixture(test: OneArgTest)= {
    //mainCorridor, subCorridor, Floors
    val hotel = DefaultAction.instantiate(1, 2, 2)
    MovementAction.execute(hotel.get, 1, 1)

    val theFixture = F(hotel.get)
    test(theFixture)
  }

  "movement action" should "switch on light in subCorridor 1 of floor 1" in {param =>
    val hotel = param.result_success

    val appliances = hotel.floors(0).subCorridors(0).appliances

    // all light appliances should be switched on
    assert(appliances.count(x => x.applianceType == ApplianceType.LIGHT && x.getCurrentState()) == appliances.count(x => x.applianceType == ApplianceType.LIGHT))

    val powerConsumed = PowerConsumptionUtility.powerBeingConsumedInAFloor(hotel.floors(0))
    val maxPower = PowerConsumptionUtility.maxPowerConsumption(hotel.floors(0).mainCorridors.length, hotel.floors(0).subCorridors.length)

    assert(powerConsumed <= maxPower)
  }

}
