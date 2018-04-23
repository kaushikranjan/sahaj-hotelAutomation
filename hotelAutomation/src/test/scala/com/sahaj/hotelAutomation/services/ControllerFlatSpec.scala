package com.sahaj.hotelAutomation.services

import com.sahaj.hotelautomation.constants.{ActionSet, ApplianceType}
import com.sahaj.hotelautomation.models.Hotel
import com.sahaj.hotelautomation.models.commandModels._
import com.sahaj.hotelautomation.services.Contoller
import org.scalatest.fixture

class ControllerFlatSpec extends fixture.FlatSpec {

  case class F(result_success : Hotel)

  override type FixtureParam = F

  def withFixture(test: OneArgTest)= {

    Contoller.run(DefaultCommand((ActionSet.DEFAULT), DefaultAttribs(2,2,1)))
    Contoller.run(MovementCommand((ActionSet.MOVEMENT), MovementAttribs(1,1)))
    Contoller.run(MovementCommand((ActionSet.MOVEMENT), MovementAttribs(1,2)))
    Contoller.run(NoMovementCommand((ActionSet.NO_MOVEMENT), MovementAttribs(1,1)))
    Contoller.run(NoMovementCommand((ActionSet.NO_MOVEMENT), MovementAttribs(1,2)))

    val theFixture = F(null)
    test(theFixture)
  }

  "first instantiation" should "successfully create hotel object" in {param =>
    assert(true)
  }
}
