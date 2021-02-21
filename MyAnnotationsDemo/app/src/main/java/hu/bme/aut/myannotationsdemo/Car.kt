package hu.bme.aut.myannotationsdemo

import hu.bme.aut.autotoast_annotations.AutoToast
import hu.bme.aut.autotoast_annotations.ShowToast

@AutoToast
class Car(
    @ShowToast
    val manufacturername: String,

    @ShowToast
    val type: String,

    val name: String
)
