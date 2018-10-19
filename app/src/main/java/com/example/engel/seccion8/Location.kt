package com.example.engel.seccion8

import android.annotation.SuppressLint
import android.app.Activity

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



class Location(var activity: Activity){

    private var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest:LocationRequest
    var callback:LocationCallback? = null
    var position:LatLng? = null

    init {
        this.fusedLocationProviderClient = FusedLocationProviderClient(activity)
        configLocationRequest()
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
                        Toast.makeText(activity,"Sin Permisos de Ubicacion",Toast.LENGTH_SHORT).show()
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
    fun obtenerUbicacion(funtion:()->Unit) {
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

}
