package com.example.engel.seccion8

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.app.AppCompatActivity

import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.karumi.dexter.Dexter
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener



//var activity: Activity
open class Location(){



    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest:LocationRequest
    private var callback:LocationCallback? = null
    var position:LatLng? = null
    lateinit var activity:Activity

    init {

        configLocationRequest()
    }

    fun configFusedLocationProviderClient(){
        this.fusedLocationProviderClient = FusedLocationProviderClient(activity)
    }

    fun configLocationRequest(){
        locationRequest = LocationRequest()
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    fun permisoLocation(funtion:()-> Unit){
        Dexter.withActivity(activity)
                .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION )
                .withListener(object : PermissionListener{
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        funtion()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        activity.toast("Se necesitan permisos de Localizacion")
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }
                })
                .check()


    }

    fun detenerActualizacionUbicacion() {
        fusedLocationProviderClient.removeLocationUpdates(callback)
    }

    @SuppressLint("MissingPermission")
    fun onLocationUpdate(funtion:()->Unit) {
        // obtenemos ultima ubicacion
        callback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                for (ubicacion in locationResult?.locations!!){
                    position = LatLng(ubicacion.latitude,ubicacion.longitude)

                }
                funtion()
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,callback,null)

    }


    // Calculos
    fun calcDistance(lugar:Lugar,location: LatLng = this.position!!): Double {

        val earthRadius = 6378.0
        val dLat = Math.toRadians(lugar.latitude - location.latitude)
        val dLon = Math.toRadians(lugar.longitude - location.longitude)
        val a = Math.pow(Math.sin(dLat / 2), 2.0) + Math.cos(lugar.latitude) * Math.cos(location.latitude) * Math.pow(Math.sin(dLon / 2), 2.0)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val distUserPlace = earthRadius * c * 1000.0

        return distUserPlace
    }

    fun closestPlace(places: ArrayList<Lugar>, location: LatLng = this.position!!): Lugar {
        var k = 0
        var actualDist: Double
        var prevDist: Double

        for (i in 1 until places.size) {
            places[i].isOnPlace = false
            actualDist = calcDistance(places[i])
            prevDist = calcDistance(places[k])
            if (prevDist >= actualDist) k = i
        }

        if (calcDistance(places[k]) <= places[k].radio) places[k].isOnPlace = true

        return places[k]
    }





}
