package com.sahaj.hotelautomation.models

import com.sahaj.hotelautomation.constants.{ApplianceType, CorridorType}
import com.sahaj.hotelautomation.models.interfaces.Appliance

case class Light(override val id: String, override val operationStartTime: Time,
                 override val operationEndTime: Time, override val powerConsumption: Double,
                 override val floorNumber: Int, override val corridorType: CorridorType.Value,
                 override val corridorIndex: Int)
  extends Appliance(id, operationStartTime, operationEndTime, powerConsumption,
    ApplianceType.LIGHT, floorNumber, corridorType,corridorIndex)