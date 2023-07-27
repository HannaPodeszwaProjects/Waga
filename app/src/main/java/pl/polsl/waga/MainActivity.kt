package pl.polsl.waga

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import android.util.SparseIntArray
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.label.TensorLabel
import pl.polsl.waga.ml.Model
import java.io.*
import java.util.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

//    private var labelsList = arrayListOf(
//        )//
        private var labelsList = arrayListOf("Jabłko", "Banan", "Burak", "Kapusta", "Karambola", "Marchewka", "Ogórek", "Guawa",
    "Kiwi", "Mango", "Melon", "Cebula czerwona","Cebula biała", "Pomarancza", "Pietruszka", "Brzoskwinia", "Gruszka", "Papryka czerwona",
    "Persymona", "Papaja", "Sliwka", "Granat", "Ziemniak", "Pomidor")

    lateinit var imageBitmap: Bitmap
    private lateinit var IsProcessing :referenceBool
    private var recognizedFruit: String= ""
    private var toPrint:String = ""
    private var isRecognized = false

    enum class UserPermission{
        CAMERA,
        WRITE_DATA
    }

    private var count = 0
    private var textureView: TextureView? = null

    companion object {
        private const val TAG = "AndroidCameraApi"
        private val ORIENTATIONS = SparseIntArray()
        private const val REQUEST_CAMERA_PERMISSION = 200

        init {
            ORIENTATIONS.append(Surface.ROTATION_0, 90)
            ORIENTATIONS.append(Surface.ROTATION_90, 0)
            ORIENTATIONS.append(Surface.ROTATION_180, 270)
            ORIENTATIONS.append(Surface.ROTATION_270, 180)
        }
    }

    private lateinit var cameraId: String
    protected var cameraDevice: CameraDevice? = null
    protected var cameraCaptureSessions: CameraCaptureSession? = null
    protected var captureRequestBuilder: CaptureRequest.Builder? = null
    private var imageDimension: Size? = null
    private var imageReader: ImageReader? = null
    private var mBackgroundHandler: Handler? = null
    private var mBackgroundThread: HandlerThread? = null
    private data class referenceBool (var value: Boolean)
    private var isProcessing : referenceBool = referenceBool(false)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textureView = findViewById<View>(R.id.texture) as TextureView


        val yesButton: Button = findViewById(R.id.yesButton)
        val noButton: Button = findViewById(R.id.noButton)
        val startButton: Button = findViewById(R.id.startButton)
        yesButton.setVisibility(View.GONE)
        noButton.setVisibility(View.GONE)

        //BUTTONS
        yesButton.setOnClickListener {
            val toast = Toast.makeText(applicationContext, "Drukowanie etykiety dla " +toPrint, Toast.LENGTH_SHORT)
            toast.show()
            startButton.setText("Zważ")
            clearLabel()
        }
        noButton.setOnClickListener {
            val myIntent = Intent(this, AllProducts::class.java)
            val args = Bundle()
            args.putSerializable("labellist", labelsList as Serializable)
            myIntent.putExtra("BUNDLE",args)
            startActivity(myIntent)
        }

        startButton.setOnClickListener {
            decodeImage(imageBitmap,IsProcessing)


            startButton.setText("Rozpoznaj ponownie")

            if(isRecognized) {
                yesButton.setVisibility(View.VISIBLE)
                noButton.setVisibility(View.VISIBLE)
                toPrint = recognizedFruit
            }
            recognizedFruit=""
        }

    }



    var textureListener: TextureView.SurfaceTextureListener = object :
        TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            //open your camera here
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
            // Transform you image captured size according to the surface width and height
        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            return false
        }

        override fun onSurfaceTextureUpdated(texture: SurfaceTexture) {
            count++

            if(isProcessing.value == false) {
                isProcessing.value = true
                thread {
                    val frame = Bitmap.createBitmap(textureView!!.width, textureView!!.height, Bitmap.Config.ARGB_8888)
                    textureView?.getBitmap(frame)
                    imageBitmap=frame
                    IsProcessing=isProcessing
                }
            }
        }
    }

    suspend private fun asyncDecode(frame: Bitmap, isProcessing: referenceBool) {
        isProcessing.value = false
    }


    private val stateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            //This is called when the camera is open
            Log.e(TAG, "onOpened")
            cameraDevice = camera
            createCameraPreview()
        }

        override fun onDisconnected(camera: CameraDevice) {
            cameraDevice!!.close()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            cameraDevice!!.close()
            cameraDevice = null
        }
    }

    protected fun startBackgroundThread() {
        mBackgroundThread = HandlerThread("Camera Background")
        mBackgroundThread!!.start()
        mBackgroundHandler = Handler(mBackgroundThread!!.looper)
    }

    protected fun stopBackgroundThread() {
        mBackgroundThread!!.quitSafely()
        try {
            mBackgroundThread!!.join()
            mBackgroundThread = null
            mBackgroundHandler = null
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    protected fun createCameraPreview() {
        try {
            val texture = textureView!!.surfaceTexture!!
            texture.setDefaultBufferSize(imageDimension!!.width, imageDimension!!.height)
            val surface = Surface(texture)
            captureRequestBuilder =
                cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            captureRequestBuilder!!.addTarget(surface)
            cameraDevice!!.createCaptureSession(
                Arrays.asList(surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                        //The camera is already closed
                        if (null == cameraDevice) {
                            return
                        }
                        // When the session is ready, we start displaying the preview.
                        cameraCaptureSessions = cameraCaptureSession
                        updatePreview()
                    }

                    override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
                        Toast.makeText(
                            this@MainActivity,
                            "Configuration change",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                null
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun openCamera() {
        val manager = getSystemService(CAMERA_SERVICE) as CameraManager
        Log.e(TAG, "is camera open")
        try {
            cameraId = manager.cameraIdList[0]
            val characteristics = manager.getCameraCharacteristics(cameraId)
            val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!
            imageDimension = map.getOutputSizes(SurfaceTexture::class.java)[0]
            // Add permission for camera and let user grant the permission
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CAMERA_PERMISSION
                )
                return
            }
            manager.openCamera(cameraId, stateCallback, null)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
        Log.e(TAG, "openCamera X")
    }

    protected fun updatePreview() {
        if (null == cameraDevice) {
            Log.e(TAG, "updatePreview error, return")
        }
        captureRequestBuilder!!.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
        try {
            cameraCaptureSessions!!.setRepeatingRequest(
                captureRequestBuilder!!.build(),
                null,
                mBackgroundHandler
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun closeCamera() {
        if (null != cameraDevice) {
            cameraDevice!!.close()
            cameraDevice = null
        }
        if (null != imageReader) {
            imageReader!!.close()
            imageReader = null
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(
                    this@MainActivity,
                    "Sorry!!!, you can't use this app without granting permission",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startBackgroundThread()
        if (textureView!!.isAvailable) {
            openCamera()
        } else {
            textureView!!.surfaceTextureListener = textureListener
        }
    }

    override fun onPause() {
        closeCamera()
        stopBackgroundThread()
        super.onPause()
    }




/* Decode image from bitmap with Tensorflow model*/
    private fun decodeImage(img: Bitmap, isProcessing: referenceBool){

    val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(100, 100, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        var tImage = TensorImage(DataType.FLOAT32)
        tImage.load(img) //change bitmap to TensorImage
        tImage = imageProcessor.process(tImage)

        val probabilityProcessor =
            TensorProcessor.Builder().add(NormalizeOp(0f, 255f)).build()
        val myModel = Model.newInstance(this)

        val outputs =
            myModel.process(probabilityProcessor.process(tImage.tensorBuffer))
        val outputBuffer = outputs.outputFeature0AsTensorBuffer


        val tensorLabel = TensorLabel(labelsList, outputBuffer) //add labels to output
        var tmpScore=0f
        var fruit =""
        var probability = ""
        for(a in tensorLabel.categoryList)
        {
            if(a.score > 0.5 && tmpScore<a.score)
            {
                fruit=a.label
                tmpScore = a.score
                probability = a.score.toString()
            }
        }
        recognizedFruit = fruit
    if(fruit.equals("")) {
        imageLabel.text = "Nie rozpoznano obiektu"
        isRecognized = false
    }
    else {
        imageLabel.text =
            "Czy twój produkt to:\n" + recognizedFruit + "\nPrawdopodobieństwo: " + probability
        isRecognized = true
    }
        isProcessing.value = false
    }

    private fun clearLabel(){
        this.imageLabel.text = ""
    }


//Permisions
    private fun checkAndRequestPermissionsFor(items: ArrayList<UserPermission>){

        var itemsRequirePermission = ArrayList<UserPermission>()
        for (item in items){

            if (!hasPermissionFor(item)){
                itemsRequirePermission.add(item)
            }
        }
        if (!itemsRequirePermission.isEmpty()){
            requestPermissionFor(itemsRequirePermission)
        }

    }

    private fun hasPermissionFor(item: UserPermission): Boolean{

        var isPermitted = false
        when (item){

            UserPermission.CAMERA ->{

                isPermitted = this.checkSelfPermission(Manifest.permission.CAMERA) === PackageManager.PERMISSION_GRANTED

            }
            UserPermission.WRITE_DATA ->{
                isPermitted = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            }
        }
        return isPermitted
    }
    private fun requestPermissionFor(items: ArrayList<UserPermission>){

        var manisfestInfo = ArrayList<String>()
        for (item in items){

            manisfestInfo.add(getManisfestInfoFor(item))

        }
        val arrayOfPermissionItems = arrayOfNulls<String>(manisfestInfo.size)
        manisfestInfo.toArray(arrayOfPermissionItems)
        this.requestPermissions(arrayOfPermissionItems, 2)

    }

    private fun getManisfestInfoFor(item: UserPermission): String{

        var manifestString = ""
        when (item){

            UserPermission.CAMERA ->{

                manifestString = Manifest.permission.CAMERA
                //this.requestPermissions(arrayOf<String>(Manifest.permission.CAMERA), 1)

            }
            UserPermission.WRITE_DATA ->{
                manifestString = Manifest.permission.WRITE_EXTERNAL_STORAGE
                //this.requestPermissions(arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE), 2)
            }
        }
        return manifestString
    }


    private fun showAlert(message: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Recognized Text")
        dialog.setMessage(message)
        dialog.setPositiveButton(" OK ",
            { dialog, id -> dialog.dismiss() })
        dialog.show()

    }
}