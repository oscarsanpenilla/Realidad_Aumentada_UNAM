package com.example.engel.seccion8



class Lugar(var latitude:Double = 0.0, var longitude:Double =0.0, var radio:Double =0.0, var name:String="", var info:String="") {

    lateinit var lugares :ArrayList<Lugar>
    var isOnPlace = false


    fun isOnPlace(distUserPlace:Double){
        if (distUserPlace <= radio) {
            this.isOnPlace = true
        } else {
            this.isOnPlace = false
        }
    }

    fun getLugares(){
        lugares = ArrayList()
        lugares.add(Lugar(19.32649 , -99.182388, 122.0,"Anexo Ingenieria", "info"))
        lugares.add(Lugar(19.33139 , -99.184455, 115.0,"Fac. Ingeniería", "info"))
        lugares.add(Lugar(19.331307, -99.186625,100.0,"Fac. Contaduría", "info"))
        lugares.add(Lugar(19.332878, -99.185150, 100.0,"Fac. Química", "info"))
        lugares.add(Lugar(19.334285, -99.181514,195.0,"Fac. Medicina", "info"))

    }

}