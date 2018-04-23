package com.sahaj.hotelautomation.models

import com.sahaj.hotelautomation.constants.CorridorType
import com.sahaj.hotelautomation.models.interfaces.{Appliance, Corridor}

case class Floor(mainCorridors: Array[Corridor], subCorridors: Array[Corridor])
