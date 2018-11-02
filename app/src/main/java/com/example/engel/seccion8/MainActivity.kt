package com.example.engel.seccion8

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.ar.sceneform.ux.ArFragment
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.net.Uri
import android.support.v7.app.AlertDialog


import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ux.TransformableNode
import com.google.ar.sceneform.rendering.Renderable
import kotlinx.android.synthetic.main.layout_lugares.*
import android.graphics.Bitmap
import com.google.ar.core.*
import com.google.a.b.a.a.a.e
import android.graphics.BitmapFactory
import android.util.Log
import java.io.IOException
import com.google.ar.core.TrackingState
import com.google.ar.core.AugmentedImage
import com.google.ar.sceneform.FrameTime
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var fragment: CustomArFragment
    private var shouldAddModel = true

    private lateinit var location:Location
    private lateinit var lugar:Lugar
    private lateinit var lugares :ArrayList<Lugar>

    private lateinit var lugarMasCercano:Lugar
    private lateinit var anchor: Anchor
    private var index:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        button_sig.setOnClickListener {
            toast("btn funciona ${index}")
            shouldAddModel = true
            anchor.detach()
            if (index < lugarMasCercano.sitiosInteres.size - 1) index++
            else index = 0

        }

        fragment = getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment) as CustomArFragment
        fragment.getPlaneDiscoveryController().hide()
        fragment.getArSceneView().getScene().setOnUpdateListener(this::onUpdateFrame)


        location = Location()
        location.activity = this
        location.configFusedLocationProviderClient()
        location.permisoLocation {
            location.onLocationUpdate {

            main()

            }
        }
        lugar = Lugar()
        lugar.getLugares()
        lugares = lugar.lugares

    }
    private fun main(){
        updateLugaresRelativeLayout()
        updateGalleryLayout()
        //toast("${lugarMasCercano.sitiosInteres[0].nombre  }")
    }

    private fun updateGalleryLayout(){

    }

    private fun delateAnchor(){

    }

    private fun getLugarMasCercano():Lugar = location.closestPlace(lugares)
    private fun updateLugaresRelativeLayout(){
        lugarMasCercano = location.closestPlace(lugares)
        if (lugarMasCercano.isOnPlace){
            text_view_nombre.text = getLugarMasCercano().name
            image_view_lugar.visibility = View.VISIBLE
            text_view_info.visibility = View.VISIBLE
        }else {
            text_view_nombre.text = "Lugar mas cercano ${getLugarMasCercano().name}"
            image_view_lugar.visibility = View.INVISIBLE
            text_view_info.visibility = View.INVISIBLE
        }
    }

    private fun placeObject(fragment: ArFragment, anchor: Anchor, model: Uri) {
        ModelRenderable.builder()
                .setSource(fragment.context, model)
                .build()
                .thenAccept { renderable -> addNodeToScene(fragment, anchor, renderable) }
                .exceptionally { throwable ->
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage(throwable.message)
                            .setTitle("Error!")
                    val dialog = builder.create()
                    dialog.show()
                    null
                }

    }

    private fun addNodeToScene(fragment: ArFragment, anchor: Anchor, renderable: Renderable) {
        val anchorNode = AnchorNode(anchor)
        val node = TransformableNode(fragment.transformationSystem)
        node.renderable = renderable
        node.setParent(anchorNode)
        fragment.arSceneView.scene.addChild(anchorNode)
        node.select()
    }

    fun setupAugmentedImageDb(config: Config, session: Session): Boolean {
        val augmentedImageDatabase: AugmentedImageDatabase

        val bitmap = loadAugmentedImage() ?: return false

        augmentedImageDatabase = AugmentedImageDatabase(session)
        augmentedImageDatabase.addImage("metrobus", bitmap!!)

        config.setAugmentedImageDatabase(augmentedImageDatabase)
        return true
    }

    private fun loadAugmentedImage(): Bitmap? {
        try {
            assets.open("metrobus.png").use { `is` -> return BitmapFactory.decodeStream(`is`) }
        } catch (e: IOException) {
            Log.e("ImageLoad", "IO Exception while loading", e)
        }

        return null
    }

    private fun onUpdateFrame(frameTime: FrameTime) {
        val frame = fragment.getArSceneView().getArFrame()

        val augmentedImages = frame.getUpdatedTrackables(AugmentedImage::class.java)

        for (augmentedImage in augmentedImages) {
            if (augmentedImage.getTrackingState() == TrackingState.TRACKING) {

                if (augmentedImage.getName() == "metrobus" && shouldAddModel) {
                    if (index >= lugarMasCercano.sitiosInteres.size) index=0
                    anchor = augmentedImage.createAnchor(augmentedImage.getCenterPose())

                    placeObject(fragment,
                            anchor,
                            Uri.parse(lugarMasCercano.sitiosInteres[index].modelo))
                    shouldAddModel = false


                }
            }
        }

    }


    override fun onRestart() {
        super.onRestart()
        location.permisoLocation {
            location.onLocationUpdate { main() }
        }

    }









    override fun onStop() {
        super.onStop()

        location.detenerActualizacionUbicacion()

    }



}
