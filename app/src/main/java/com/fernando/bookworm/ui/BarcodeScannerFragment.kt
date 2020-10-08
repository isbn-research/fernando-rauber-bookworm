package com.fernando.bookworm.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import com.fernando.bookworm.R
import com.fernando.bookworm.databinding.FragmentBarcodeScannerBinding
import com.fernando.bookworm.extension.toastMessage
import com.fernando.bookworm.util.Constants
import com.fernando.bookworm.util.RxBus
import com.fernando.bookworm.util.RxEvent
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BarcodeScannerFragment @Inject constructor() : DaggerFragment() {

    private var _binding: FragmentBarcodeScannerBinding? = null
    private val binding get() = _binding!!


    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector
    private var barcodeFound: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // View Binding
        _binding = FragmentBarcodeScannerBinding.inflate(inflater, container, false)

        detector = BarcodeDetector.Builder(requireContext()).build()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onResume() {
        super.onResume()

        // Check for camera permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            askForPermission()
        else {
            barcodeFound = false

            // Start Camera
            cameraSource = CameraSource.Builder(requireContext(), detector)
                .setAutoFocusEnabled(true)
                .build()
            detector.setProcessor(processor)
            binding.cameraSurfaceView.holder.addCallback(surfaceCallBack)
            binding.cameraSurfaceView.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()

        binding.cameraSurfaceView.visibility = View.GONE
        binding.cameraSurfaceView.holder.removeCallback(surfaceCallBack)
    }

    private fun askForPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), Constants.REQUEST_CAMERA_PERMISSION)
    }

    private val surfaceCallBack = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder?) {
            try {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    return

                cameraSource.start(holder)
            } catch (e: Exception) {
                requireActivity().toastMessage(R.string.error, isWarning = true)
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            cameraSource.release()
        }
    }

    private val processor = object : Detector.Processor<Barcode> {

        override fun release() {

        }

        override fun receiveDetections(detector: Detector.Detections<Barcode>?) {

            if (detector != null && detector.detectedItems.isNotEmpty()) {
                val qrCode: SparseArray<Barcode> = detector.detectedItems
                val code = qrCode.valueAt(0)

                if (code.displayValue.length >= 10 && !barcodeFound) {
                    barcodeFound = true

                    // Send data to MainActivity to switch to Search tab and the data to Search tab search by barcode
                    val handler = Handler(Looper.getMainLooper())
                    handler.post {
                        RxBus.publish(RxEvent.EventSearchByBarcode(code.displayValue))
                    }
                }

            }
        }
    }

}