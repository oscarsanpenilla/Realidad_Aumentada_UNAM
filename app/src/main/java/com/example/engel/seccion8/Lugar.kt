package com.example.engel.seccion8


class SitioInteres(var nombre:String, var modelo:String,var thumb:Int)

class Lugar(var latitude:Double = 0.0, var longitude:Double =0.0, var radio:Double =0.0, var name:String="", var info:String="") {

    lateinit var sitiosInteres : ArrayList<SitioInteres>
    var isOnPlace = false


    fun isOnPlace(distUserPlace:Double){
        if (distUserPlace <= radio) {
            this.isOnPlace = true
        } else {
            this.isOnPlace = false
        }
    }

    fun getLugares():ArrayList<Lugar>{
        var lugares :ArrayList<Lugar>
        lugares = ArrayList()
        lugares.add(Lugar(19.32649 , -99.182388, 122.0,"Anexo Ingenieria", "info"))
        lugares[0].sitiosInteres = ArrayList()
        lugares[0].sitiosInteres.add(SitioInteres("Parque de Hielo","WinterScene.sfb",R.drawable.mountain_thumb))
        lugares[0].sitiosInteres.add(SitioInteres("Andy Android","andy.sfb",R.drawable.droid_thumb))
        lugares.add(Lugar(19.33139 , -99.184455, 115.0,"Fac. Ingeniería", "info"))
        lugares[1].sitiosInteres = ArrayList()
        lugares[1].sitiosInteres.add(SitioInteres("Cabaña","Cabin.sfb",R.drawable.cabin_thumb))
        lugares[1].sitiosInteres.add(SitioInteres("Faro","Lighthouse.sfb",R.drawable.lighthouse_thumb))
        lugares[1].sitiosInteres.add(SitioInteres("Estadio Baseball","Ballpark.sfb",R.drawable.stadium))
        lugares.add(Lugar(19.331307, -99.186625,100.0,"Fac. Arquitectura", "info"))
        lugares[2].sitiosInteres = ArrayList()
        lugares[2].sitiosInteres.add(SitioInteres("Hotel","CUPID_HOTEL.sfb",R.drawable.cupid_hotel_thumb))
        lugares.add(Lugar(19.331251, -99.181449, 100.0,"Fac. Química", "info"))
        lugares[3].sitiosInteres = ArrayList()
        lugares[3].sitiosInteres.add(SitioInteres("Igloo","igloo.sfb",R.drawable.igloo_thumb))
        lugares.add(Lugar(19.333216, -99.181093,195.0,"Fac. Medicina", "info"))
        lugares[4].sitiosInteres = ArrayList()
        lugares[4].sitiosInteres.add(SitioInteres("Puerto","storage.sfb",R.drawable.storage_thumb))
        return lugares
    }

}