package com.example.engel.seccion8

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.ar.sceneform.ux.ArFragment
import android.view.View
import android.widget.ImageView
import android.net.Uri
import android.support.v7.app.AlertDialog


import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ux.TransformableNode
import com.google.ar.sceneform.rendering.Renderable
import kotlinx.android.synthetic.main.layout_lugares.*
import android.graphics.Bitmap
import com.google.ar.core.*
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
    private lateinit var lugares :ArrayList<Lugar>

    private lateinit var lugarMasCercano:Lugar
    private var anchor: Anchor? = null
    private var index:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        button_sig.setOnClickListener {
            toast("Enfoca la tarjeta nuevamente.")
            shouldAddModel = true
            anchor?.detach()
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

        lugares = Lugar().getLugares()

    }
    private fun main(){
        for (lugar in lugares)lugar.isOnPlace = false
        getLugarMasCercano()
        if (!lugarMasCercano.isOnPlace) {
            shouldAddModel = true
            anchor?.detach()
            index = 0
        }
        updateLugaresRelativeLayout()
        updateGalleryLayout()

    }

    private fun updateGalleryLayout(){
        gallery_layout.removeAllViews()
        if (lugarMasCercano.isOnPlace) {
            for (sitioDeInteres in lugarMasCercano.sitiosInteres) {
                var modelo1 = ImageView(this)
                modelo1.setImageResource(sitioDeInteres.thumb)
                modelo1.setContentDescription(sitioDeInteres.nombre)
                gallery_layout.addView(modelo1)
            }
        }else gallery_layout.removeAllViews()

    }



    private fun getLugarMasCercano() {lugarMasCercano = location.closestPlace(lugares)}
    private fun updateLugaresRelativeLayout(){
        if (lugarMasCercano.isOnPlace){
            image_view_lugar.setImageResource(R.drawable.ic_location)
            text_view_nombre.text = lugarMasCercano.name
            image_view_lugar.visibility = View.VISIBLE
            text_view_info.visibility = View.VISIBLE
            if (!shouldAddModel) text_view_info.text = lugarMasCercano.sitiosInteres[index].nombre
            else text_view_info.text = "Enfoca la tarjeta de Metrobus"

        }else {
            text_view_nombre.text = "Lugar mas cercano ${lugarMasCercano.name}"
            image_view_lugar.setImageResource(R.drawable.ic_location_off)
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
        augmentedImageDatabase = AugmentedImageDatabase(session)
        augmentedImageDatabase.addImage("metrobus-1", loadAugmentedImage("metrobus-1.png") ?: return false)
        augmentedImageDatabase.addImage("metrobus-2", loadAugmentedImage("metrobus-2.png") ?: return false)
        augmentedImageDatabase.addImage("metrobus-3", loadAugmentedImage("metrobus-3.PNG") ?: return false)
        config.setAugmentedImageDatabase(augmentedImageDatabase)
        return true
    }

    private fun loadAugmentedImage(picture:String): Bitmap? {
        try {
            assets.open(picture).use { `is` -> return BitmapFactory.decodeStream(`is`) }
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

                // ( augmentedImage.getName() == "metrobus" || augmentedImage.getName() == "metrobus-3" ) &&
                if ( shouldAddModel) {
                    if (index >= lugarMasCercano.sitiosInteres.size) index=0
                    if (lugarMasCercano.isOnPlace){
                        toast("Cargando...")
                        anchor = augmentedImage.createAnchor(augmentedImage.getCenterPose())
                        placeObject(fragment,
                                anchor!!,
                                Uri.parse(lugarMasCercano.sitiosInteres[index].modelo))
                        shouldAddModel = false
                        updateLugaresRelativeLayout()

                    }else toast("Tarjeta Dectectada... Pero no estas en un sitio de interes")
                }
            }
        }

    }


    override fun onRestart() {
        super.onRestart()
        location.permisoLocation {
            location.onLocationUpdate { main() }
        }
        fragment.arSceneView.resume()
        fragment.getPlaneDiscoveryController().hide()


    }

    override fun onStop() {
        super.onStop()

        location.detenerActualizacionUbicacion()
        fragment.arSceneView.pause()

    }

    override fun onPause() {
        super.onPause()
        //fragment.arSceneView.pause()
    }



}
