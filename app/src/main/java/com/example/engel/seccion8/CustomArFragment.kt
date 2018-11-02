package com.example.engel.seccion8

import android.util.Log
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment

class CustomArFragment: ArFragment() {

    override fun getSessionConfiguration(session: Session): Config {
        //getPlaneDiscoveryController().hide()
        getPlaneDiscoveryController().setInstructionView(null)

        var config:Config = Config(session)
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE)
        session.configure(config)
        this.getArSceneView().setupSession(session)

        if ((getActivity() as MainActivity).setupAugmentedImageDb(config, session)){
            Log.d("SetupAumImgDb","Success")
        }else{
            Log.e("SetupAumImgDb","Fail to setup Db")
        }

        return config
    }
}