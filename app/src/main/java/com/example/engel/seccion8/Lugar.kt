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
        lugares[0].sitiosInteres.add(SitioInteres("Centro de Ingenieria Avanzada","andy.sfb",R.drawable.droid_thumb))
        lugares[0].sitiosInteres.add(SitioInteres("Pokebola","igloo.sfb",R.drawable.igloo_thumb))
        lugares.add(Lugar(19.33139 , -99.184455, 115.0,"Fac. Ingeniería", "info"))
        lugares[1].sitiosInteres = ArrayList()
        lugares[1].sitiosInteres.add(SitioInteres("Biblioteca","Cabin.sfb",R.drawable.cabin_thumb))
        lugares.add(Lugar(19.331307, -99.186625,100.0,"Fac. Contaduría", "info"))
        lugares[2].sitiosInteres = ArrayList()
        lugares[2].sitiosInteres.add(SitioInteres("Tacos de Canasta","igloo.sfb",R.drawable.droid_thumb))
        lugares.add(Lugar(19.332878, -99.185150, 100.0,"Fac. Química", "info"))
        lugares.add(Lugar(19.334285, -99.181514,195.0,"Fac. Medicina", "info"))
        return lugares
    }

}