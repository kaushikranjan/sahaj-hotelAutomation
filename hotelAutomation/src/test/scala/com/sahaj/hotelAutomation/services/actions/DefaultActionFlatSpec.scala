package com.sahaj.hotelAutomation.services.actions

import com.sahaj.hotelautomation.constants.ApplianceType
import com.sahaj.hotelautomation.models.Hotel
import com.sahaj.hotelautomation.services.actions.DefaultAction
import org.scalatest.fixture

class DefaultActionFlatSpec extends fixture.FlatSpec {

  case class F(result_success : Option[Hotel], result_reset : Option[Hotel])

  override type FixtureParam = F

  def withFixture(test: OneArgTest)= {

    val hotel = DefaultAction.instantiate(1, 2, 2)
    val hotel_again = DefaultAction.instantiate(1, 2, 2)

    val theFixture = F(hotel, hotel_again)
    test(theFixture)
  }

  "first instantiation" should "successfully create hotel object" in {param =>
    assert(param.result_success.isDefined)

    val totalFloors = 2
    val mainCorridorsPerFloor = 1
    val subCorridorsPerFloor = 2

    val hotel = param.result_success.get
    assert(hotel.floors.length == totalFloors)

    val subCorridorsInFloor = hotel.floors.map(x => x.subCorridors.length).count(x => x == subCorridorsPerFloor)
    val mainCorridorsInFloor = hotel.floors.map(x => x.mainCorridors.length).count(x => x == mainCorridorsPerFloor)
    assert(subCorridorsInFloor == totalFloors)
    assert(mainCorridorsInFloor == totalFloors)

    hotel.floors.foreach(x => {
      val subCorridorAppliances = x.subCorridors.map(y => y.appliances)
      subCorridorAppliances.foreach(y => {
        assert(y.count(app => app.applianceType == ApplianceType.AC) == 1)
        assert(y.count(app => app.applianceType == ApplianceType.LIGHT) == 1)
      })

      val mainCorridorAppliances = x.mainCorridors.map(y => y.appliances)
      mainCorridorAppliances.foreach(y => {
        assert(y.count(app => app.applianceType == ApplianceType.AC) == 1)
        assert(y.count(app => app.applianceType == ApplianceType.LIGHT) == 1)
      })
    })

  }

  "second instantiation" should "reset hotel object" in {param =>
    assert(param.result_reset.isDefined)

    val totalFloors = 2
    val mainCorridorsPerFloor = 1
    val subCorridorsPerFloor = 2

    val hotel = param.result_reset.get
    assert(hotel.floors.length == totalFloors)

    val subCorridorsInFloor = hotel.floors.map(x => x.subCorridors.length).count(x => x == subCorridorsPerFloor)
    val mainCorridorsInFloor = hotel.floors.map(x => x.mainCorridors.length).count(x => x == mainCorridorsPerFloor)
    assert(subCorridorsInFloor == totalFloors)
    assert(mainCorridorsInFloor == totalFloors)

    hotel.floors.foreach(x => {
      val subCorridorAppliances = x.subCorridors.map(y => y.appliances)
      subCorridorAppliances.foreach(y => {
        assert(y.count(app => app.applianceType == ApplianceType.AC) == 1)
        assert(y.count(app => app.applianceType == ApplianceType.LIGHT) == 1)
      })

      val mainCorridorAppliances = x.mainCorridors.map(y => y.appliances)
      mainCorridorAppliances.foreach(y => {
        assert(y.count(app => app.applianceType == ApplianceType.AC) == 1)
        assert(y.count(app => app.applianceType == ApplianceType.LIGHT) == 1)
      })
    })

  }
}
