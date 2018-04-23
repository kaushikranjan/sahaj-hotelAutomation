package com.sahaj.hotelautomation.models.interfaces

import com.sahaj.hotelautomation.constants.CorridorType

abstract class Corridor(val id: String,
     val appliances : Array[Appliance], val corridorType: CorridorType.Value)