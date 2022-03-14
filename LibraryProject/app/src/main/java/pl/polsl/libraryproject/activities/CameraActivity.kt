package pl.polsl.libraryproject.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.*
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import pl.polsl.libraryproject.enums.PreviousActivity
import pl.polsl.libraryproject.R

class CameraActivity : AppCompatActivity() {

    private val cameraExecutor: ExecutorService by lazy { Executors.newSingleThreadExecutor() }
    private lateinit var cameraView: PreviewView
    private lateinit var addButton: Button
    private var recognizedText: String = ""
    private lateinit var previousActivity: PreviousActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        val args = intent.getBundleExtra("BUNDLE")
        previousActivity = args!!.getSerializable("previous") as PreviousActivity

        cameraView = findViewById(R.id.cameraView)
        addButton = findViewById(R.id.addButton)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions()
        }

        addButton.setOnClickListener {
            val myIntent = previousActivity.findPreviousActivity(this)
            val args = Bundle()
            args.putSerializable("recognizedText", recognizedText)
            myIntent.putExtra("text", args)
            startActivity(myIntent)
        }
    }

    private fun startCamera() {
        try {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
            cameraProviderFuture.addListener(
                {
                    val preview = Preview.Builder()
                        .build()
                        .also {
                            it.setSurfaceProvider(cameraView.surfaceProvider)
                        }

                    cameraProviderFuture.get().bind(preview, imageAnalyzer)
                },
                ContextCompat.getMainExecutor(this)
            )
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun ProcessCameraProvider.bind(
        preview: Preview,
        imageAnalyzer: ImageAnalysis
    ) = try {
        unbindAll()
        bindToLifecycle(
            this@CameraActivity,
            CameraSelector.DEFAULT_BACK_CAMERA,
            preview,
            imageAnalyzer
        )
    } catch (ise: IllegalStateException) {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) { //check permissions
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private companion object {
        const val REQUEST_CODE_PERMISSIONS = 10
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )
    }

    class TextReaderAnalyzer(
        private val textFoundListener: (String) -> Unit
    ) : ImageAnalysis.Analyzer {

        override fun analyze(imageProxy: ImageProxy) {
            val recognizer = TextRecognition.getClient(
                TextRecognizerOptions.DEFAULT_OPTIONS
            )
            //reading the image from the camera
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                //preparing an image to be passed to text recognition
                val image =
                    InputImage.fromMediaImage(
                        mediaImage,
                        imageProxy.imageInfo.rotationDegrees
                    )
                //start text recognition
                recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        processTextFromImage(visionText)
                        imageProxy.close()
                    }
                    .addOnFailureListener {
                    }
            }
        }


        private fun processTextFromImage(visionText: Text) {
            for (block in visionText.textBlocks) {//read as block of text
                textFoundListener(block.text)
            }
        }
    }


    private val imageAnalyzer by lazy {
        ImageAnalysis.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
            .also {
                it.setAnalyzer(
                    cameraExecutor,
                    TextReaderAnalyzer(::onTextFound)
                )
            }
    }

    private fun onTextFound(foundText: String) {
        //display recognized text
        Toast.makeText(this, foundText, Toast.LENGTH_SHORT).show()
        recognizedText = foundText
    }

    override fun onBackPressed() {
        val myIntent = previousActivity.findPreviousActivity(this)
        val args = Bundle()
        args.putSerializable("recognizedText", "")
        myIntent.putExtra("text", args)
        startActivity(myIntent)
    }
}