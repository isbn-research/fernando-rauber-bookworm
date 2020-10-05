package com.fernando.bookworm.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.SparseArray
import android.view.*
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import com.fernando.bookworm.R
import com.fernando.bookworm.extension.toastMessage
import com.fernando.bookworm.util.RxBus
import com.fernando.bookworm.util.RxEvent
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import dagger.android.support.DaggerFragment


class CodeReaderFragment : DaggerFragment() {

    private companion object {
        const val REQUEST_CAMERA_PERMISSION = 20
    }

    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector
    private lateinit var surfaceView: SurfaceView
    private lateinit var textView: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_code_reader, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        surfaceView = view.findViewById(R.id.camera_surface)
        textView = view.findViewById(R.id.tv_result)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            askForPermission()
        else
            setUpControls()

    }


    private fun setUpControls() {
        detector = BarcodeDetector.Builder(requireContext()).build()
        cameraSource = CameraSource.Builder(requireContext(), detector)
            .setAutoFocusEnabled(true)
            .build()

        surfaceView.holder.addCallback(surfaceCallBack)
        detector.setProcessor(processor)
    }

    private fun askForPermission() {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.isNotEmpty())
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                setUpControls()
            else
                requireActivity().toastMessage(text = "Permission Denied!", isWarning = true)
    }

    private val surfaceCallBack = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder?) {
            try {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    return

                cameraSource.start(holder)
            } catch (e: Exception) {
                requireActivity().toastMessage(text = "Something went wrong!", isWarning = true)
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            if (cameraSource != null) {
                cameraSource.release();
            }
        }

    }

    private val processor = object : Detector.Processor<Barcode> {

        private var barcodeFound: Boolean = false

        override fun release() {

        }

        override fun receiveDetections(detector: Detector.Detections<Barcode>?) {

            if (detector != null && detector.detectedItems.isNotEmpty()) {
                val qrCode: SparseArray<Barcode> = detector.detectedItems
                val code = qrCode.valueAt(0)

                textView.text = code.displayValue


                if (code.displayValue.length >= 10 && !barcodeFound) {
                    barcodeFound = true

                    val handler = Handler(Looper.getMainLooper())
                    handler.post {
                        RxBus.publish(RxEvent.EventSearchByBarcode(textView.text.toString()))

                        cameraSource.release()
                    }
                }

            } else
                textView.text = ""
        }

    }

}