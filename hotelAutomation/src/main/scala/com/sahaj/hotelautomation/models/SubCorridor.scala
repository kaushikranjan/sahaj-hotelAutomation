package com.sahaj.hotelautomation.models

import com.sahaj.hotelautomation.constants.CorridorType
import com.sahaj.hotelautomation.models.interfaces.{Appliance, Corridor}

case class SubCorridor(override val id: String,override val appliances: Array[Appliance])
  extends Corridor(id, appliances, CorridorType.SUB_CORRIDOR)