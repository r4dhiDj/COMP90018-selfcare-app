package com.example.selfcare

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.ar.core.*
import com.google.ar.core.exceptions.*
import java.io.IOException
import java.util.concurrent.ArrayBlockingQueue
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import com.example.selfcare.presentation.components.helpers.*
import com.example.selfcare.presentation.components.rendering.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.abs


class AR_Activity : AppCompatActivity() , GLSurfaceView.Renderer{
    private val TAG: String = AR_Activity::class.java.simpleName

    private var installRequested = false

    private var mode: Mode = Mode.STEVE

    private var session: Session? = null

    // Tap handling and UI.
    private lateinit var gestureDetector: GestureDetector
    private lateinit var trackingStateHelper: TrackingStateHelper
    private lateinit var displayRotationHelper: DisplayRotationHelper
    private val messageSnackbarHelper: SnackbarHelper = SnackbarHelper()

    private val backgroundRenderer: BackgroundRenderer = BackgroundRenderer()
    private val planeRenderer: PlaneRenderer = PlaneRenderer()
    private val pointCloudRenderer: PointCloudRenderer = PointCloudRenderer()

    private val steveObject = ObjectRenderer()
    private val spidermanObject = ObjectRenderer()
    private val coinObject = ObjectRenderer()
    private val amogusObject = ObjectRenderer()

    private var steveAttachment: PlaneAttachment? = null
    private var spidermanAttachment: PlaneAttachment? = null
    private var coinAttachment: PlaneAttachment? = null
    private var amogusAttachment: PlaneAttachment? = null

    // Temporary matrix allocated here to reduce number of allocations and taps for each frame.
    private val maxAllocationSize = 20
    private val anchorMatrix = FloatArray(maxAllocationSize)
    private val queuedSingleTaps = ArrayBlockingQueue<MotionEvent>(maxAllocationSize)


    // Virtual Objects
    private var coinAnchors = mutableListOf<Anchor>()
    private var coinPlanes = hashMapOf<Plane, Int>()

    private lateinit var surfaceView: GLSurfaceView

    private var addClicked = false

    //FAB animations
    private val rotateOpen: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)}


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.ar_activity)


        surfaceView = findViewById(R.id.surfaceView)

        trackingStateHelper = TrackingStateHelper(this)
        displayRotationHelper = DisplayRotationHelper(this)

        installRequested = false

        setupTapDetector()
        setupSurfaceView()

    }

    fun onAddButtonClicked(view: View) {
        if (view.id == R.id.addButton) {
            setVisibility(addClicked);
            setAnimation(addClicked)
            addClicked = !addClicked
        }
    }

    fun setAnimation(clicked: Boolean) {
        val addButton = findViewById<FloatingActionButton>(R.id.addButton)
        val mascotButton = findViewById<FloatingActionButton>(R.id.mascotButton)
        val coinButton = findViewById<FloatingActionButton>(R.id.coinrunButton)
        if(!clicked) {
            mascotButton.startAnimation(fromBottom)
            coinButton.startAnimation(fromBottom)
            addButton.startAnimation(rotateOpen)
        }
        else {
            mascotButton.startAnimation(toBottom)
            coinButton.startAnimation(toBottom)
            addButton.startAnimation(rotateClose)
        }
    }

    fun settingsPressed(view: View) {

        val bottomSheetDialog = BottomSheetDialog(
            this, R.style.BottomSheetDialogTheme
        )
        val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
            R.layout.settings_ar_dialog,
            findViewById(R.id.bottomSheet) as LinearLayout?
        )


        bottomSheetView.findViewById<View>(R.id.steveButton).setOnClickListener {
            mode = Mode.STEVE

        }
        bottomSheetView.findViewById<View>(R.id.spidermanButton).setOnClickListener {
            mode = Mode.SPIDERMAN
        }
        bottomSheetView.findViewById<View>(R.id.amogusButton).setOnClickListener {
            mode = Mode.AMOGUS
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()



    }

    fun setVisibility(clicked: Boolean) {
        var mascotButton = findViewById<FloatingActionButton>(R.id.mascotButton)
        var coinButton = findViewById<FloatingActionButton>(R.id.coinrunButton)
        if(!clicked) {
            mascotButton.visibility = View.VISIBLE
            coinButton.visibility = View.VISIBLE
        }
        else {
            mascotButton.visibility = View.INVISIBLE
            coinButton.visibility = View.INVISIBLE
        }
    }

    fun setMode(view: View) {
        when (view.id) {
            R.id.mascotButton -> mode = Mode.SPIDERMAN
            R.id.coinrunButton -> mode = Mode.COIN

        }
    }



//    fun onRadioButtonClicked(view: View) {
//        when (view.id) {
//            R.id.radioSpiderman -> mode = Mode.SPIDERMAN
//            R.id.radioCoin -> mode = Mode.COIN
//            R.id.radioAmogus -> mode = Mode.AMOGUS
//            else -> mode = Mode.STEVE
//        }
//    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setupSurfaceView() {
        // Set up renderer.
        surfaceView.preserveEGLContextOnPause = true
        surfaceView.setEGLContextClientVersion(2)
        surfaceView.setEGLConfigChooser(8, 8, 8, 8, maxAllocationSize, 0)
        surfaceView.setRenderer(this)
        surfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        surfaceView.setWillNotDraw(false)
        surfaceView.setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }

    private fun setupTapDetector() {
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                onSingleTap(e)
                return true
            }

            override fun onDown(e: MotionEvent): Boolean {
                return true
            }
        })
    }

    private fun onSingleTap(e: MotionEvent) {
        // Queue tap if there is space. Tap is lost if queue is full.
        queuedSingleTaps.offer(e)
    }

    override fun onResume() {
        super.onResume()

        if (session == null) {
            if (!setupSession()) {
                return
            }
        }

        try {
            session?.resume()
        } catch (e: CameraNotAvailableException) {
            messageSnackbarHelper.showError(this, getString(R.string.camera_not_available))
            session = null
            return
        }

        surfaceView.onResume()
        displayRotationHelper.onResume()
    }

    private fun setupSession(): Boolean {
        var exception: Exception? = null
        var message: String? = null

        try {
            when (ArCoreApk.getInstance().requestInstall(this, !installRequested)) {
                ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                    installRequested = true
                    return false
                }
                ArCoreApk.InstallStatus.INSTALLED -> {
                }
                else -> {
                    message = getString(R.string.arcore_install_failed)
                }
            }

            // Requesting Camera Permission
            if (!CameraPermissionHelper.hasCameraPermission(this)) {
                CameraPermissionHelper.requestCameraPermission(this)
                return false
            }

            // Create the session.
            session = Session(this)

        } catch (e: UnavailableArcoreNotInstalledException) {
            message = getString(R.string.please_install_arcore)
            exception = e
        } catch (e: UnavailableUserDeclinedInstallationException) {
            message = getString(R.string.please_install_arcore)
            exception = e
        } catch (e: UnavailableApkTooOldException) {
            message = getString(R.string.please_update_arcore)
            exception = e
        } catch (e: UnavailableSdkTooOldException) {
            message = getString(R.string.please_update_app)
            exception = e
        } catch (e: UnavailableDeviceNotCompatibleException) {
            message = getString(R.string.arcore_not_supported)
            exception = e
        } catch (e: Exception) {
            message = getString(R.string.failed_to_create_session)
            exception = e
        }

        if (message != null) {
            messageSnackbarHelper.showError(this, message)
            Log.e(TAG, getString(R.string.failed_to_create_session), exception)
            return false
        }

        return true
    }

    override fun onPause() {
        super.onPause()

        if (session != null) {
            displayRotationHelper.onPause()
            surfaceView.onPause()
            session!!.pause()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        results: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            Toast.makeText(
                this,
                getString(R.string.camera_permission_needed),
                Toast.LENGTH_LONG
            ).show()

            // Permission denied with checking "Do not ask again".
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                CameraPermissionHelper.launchPermissionSettings(this)
            }
            finish()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        FullScreenHelper.setFullScreenOnWindowFocusChanged(this, hasFocus)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f)

        // Prepare the rendering objects. This involves reading shaders, so may throw an IOException.
        try {
            // Create the texture and pass it to ARCore session to be filled during update().
            backgroundRenderer.createOnGlThread(this)
            planeRenderer.createOnGlThread(this, getString(R.string.model_grid_png))
            pointCloudRenderer.createOnGlThread(this)

            // 1
            steveObject.createOnGlThread(this, getString(R.string.model_steve_obj), getString(
                R.string.model_steve_png))
            spidermanObject.createOnGlThread(this, getString(R.string.model_spiderman_obj), getString(
                R.string.model_spiderman_png))
            coinObject.createOnGlThread(this, getString(R.string.model_coin_obj), getString(
                R.string.model_coin_png))
            amogusObject.createOnGlThread(this, getString(R.string.model_amogus_obj), getString(R.string.model_amogus_png))

            // 2
            coinObject.setMaterialProperties(0.0f, 3.5f, 1.0f, 6.0f)
            steveObject.setMaterialProperties(0.0f, 3.5f, 1.0f, 6.0f)
            spidermanObject.setMaterialProperties(0.0f, 3.5f, 1.0f, 6.0f)
            amogusObject.setMaterialProperties(0.0f, 3.5f, 1.0f, 6.0f)

        } catch (e: IOException) {
            Log.e(TAG, getString(R.string.failed_to_read_asset), e)
        }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        displayRotationHelper.onSurfaceChanged(width, height)
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        // Clear screen to notify driver it should not load any pixels from previous frame.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        session?.let {
            // Notify ARCore session that the view size changed
            displayRotationHelper.updateSessionIfNeeded(it)

            try {
                it.setCameraTextureName(backgroundRenderer.textureId)

                val frame = it.update()
                val camera = frame.camera

                // Handle one tap per frame.
                handleTap(frame, camera)
                drawBackground(frame)

                // Keeps the screen unlocked while tracking, but allow it to lock when tracking stops.
                trackingStateHelper.updateKeepScreenOnFlag(camera.trackingState)

                // If not tracking, don't draw 3D objects, show tracking failure reason instead.
                if (!isInTrackingState(camera)) return

                val projectionMatrix = computeProjectionMatrix(camera)
                val viewMatrix = computeViewMatrix(camera)
                val lightIntensity = computeLightIntensity(frame)

                visualizeTrackedPoints(frame, projectionMatrix, viewMatrix)
                checkPlaneDetected()
                visualizePlanes(camera, projectionMatrix)

                drawObject(
                    steveObject,
                    steveAttachment,
                    Mode.STEVE.scaleFactor,
                    projectionMatrix,
                    viewMatrix,
                    lightIntensity
                )

                drawObject(
                    spidermanObject,
                    spidermanAttachment,
                    Mode.SPIDERMAN.scaleFactor,
                    projectionMatrix,
                    viewMatrix,
                    lightIntensity
                )

                // Spawns initial Coins
                if (hasTrackingPlane()) {
                    val allPlanes = session!!.getAllTrackables(Plane::class.java)

                    if (coinAnchors.size < 9) {
                        for (plane in coinPlanes.keys) {
                            if (coinPlanes.get(plane)!! < 3) {

                                val randomOffsetX = Math.random()
                                val randomOffsetY = Math.random()
                                val planeCenterPose = plane.centerPose
                                val anchorPose = Pose(floatArrayOf(
                                    (planeCenterPose.tx() + randomOffsetX).toFloat(),
                                    (planeCenterPose.ty() + randomOffsetY).toFloat(),
                                    planeCenterPose.tz()
                                ), planeCenterPose.rotationQuaternion)

                                val anchor = session!!.createAnchor(anchorPose)
//                                val anchor = session!!.createAnchor(plane.centerPose)
                                coinAnchors.add(anchor)
                                coinPlanes[plane] = coinPlanes[plane]!! + 1
                            }
                        }
                        Log.d(TAG, "onDrawFrame: ${coinAnchors.size} + ${coinAnchors}")
                    }
                }


                for (anchor in coinAnchors) {

                    val cameraX = camera.pose.tx()
                    val cameraY = camera.pose.ty()
                    val anchorX = anchor.pose.tx()
                    val anchorY = anchor.pose.ty()
                    val distX = abs(cameraX - anchorX)
                    val distY = abs(cameraY - anchorY)


                    if (distX < 0.5 && distY < 0.5) {

                        Log.d(TAG, "REACHING COIN!!!")
                        Log.d(TAG, "Camera pos - x: $cameraX, y: $cameraY")
                        Log.d(TAG, "Anchor pos - x: $anchorX, y: $anchorY")

                        anchor.detach()
                        coinAnchors.remove(anchor)
                    } else {
                        anchor.pose.toMatrix(anchorMatrix, 0)
                        // Update shader properties and draw
                        coinObject.updateModelMatrix(anchorMatrix, Mode.COIN.scaleFactor)
                        coinObject.draw(viewMatrix, projectionMatrix, lightIntensity)
                    }
                }

                drawObject(
                    amogusObject,
                    amogusAttachment,
                    Mode.AMOGUS.scaleFactor,
                    projectionMatrix,
                    viewMatrix,
                    lightIntensity
                )
            } catch (t: Throwable) {
                Log.e(TAG, getString(R.string.exception_on_opengl), t)
            }
        }
    }

    private fun isInTrackingState(camera: Camera): Boolean {
        if (camera.trackingState == TrackingState.PAUSED) {
            messageSnackbarHelper.showMessage(
                this, TrackingStateHelper.getTrackingFailureReasonString(camera)
            )
            return false
        }

        return true
    }

    private fun drawObject(
        objectRenderer: ObjectRenderer,
        planeAttachment: PlaneAttachment?,
        scaleFactor: Float,
        projectionMatrix: FloatArray,
        viewMatrix: FloatArray,
        lightIntensity: FloatArray
    ) {
        if (planeAttachment?.isTracking == true) {
            planeAttachment.pose.toMatrix(anchorMatrix, 0)

            // Update and draw the model
            objectRenderer.updateModelMatrix(anchorMatrix, scaleFactor)
            objectRenderer.draw(viewMatrix, projectionMatrix, lightIntensity)
        }
    }

    private fun drawBackground(frame: Frame) {
        backgroundRenderer.draw(frame)
    }

    private fun computeProjectionMatrix(camera: Camera): FloatArray {
        val projectionMatrix = FloatArray(maxAllocationSize)
        camera.getProjectionMatrix(projectionMatrix, 0, 0.1f, 100.0f)

        return projectionMatrix
    }

    private fun computeViewMatrix(camera: Camera): FloatArray {
        val viewMatrix = FloatArray(maxAllocationSize)
        camera.getViewMatrix(viewMatrix, 0)

        return viewMatrix
    }

    /**
     * Compute lighting from average intensity of the image.
     */
    private fun computeLightIntensity(frame: Frame): FloatArray {
        val lightIntensity = FloatArray(4)
        frame.lightEstimate.getColorCorrection(lightIntensity, 0)

        return lightIntensity
    }

    /**
     * Visualizes tracked points.
     */
    private fun visualizeTrackedPoints(
        frame: Frame,
        projectionMatrix: FloatArray,
        viewMatrix: FloatArray
    ) {
        // Use try-with-resources to automatically release the point cloud.
        frame.acquirePointCloud().use { pointCloud ->
            pointCloudRenderer.update(pointCloud)
            pointCloudRenderer.draw(viewMatrix, projectionMatrix)
        }
    }

    /**
     *  Visualizes planes.
     */
    private fun visualizePlanes(camera: Camera, projectionMatrix: FloatArray) {
        planeRenderer.drawPlanes(
            session!!.getAllTrackables(Plane::class.java),
            camera.displayOrientedPose,
            projectionMatrix
        )
    }

    /**
     * Checks if any tracking plane exists then, hide the message UI, otherwise show searchingPlane message.
     */
    private fun checkPlaneDetected() {
        if (hasTrackingPlane()) {
            messageSnackbarHelper.hide(this)
        } else {
            messageSnackbarHelper.showMessage(
                this,
                getString(R.string.searching_for_surfaces)
            )
        }
    }

    /**
     * Checks if we detected at least one plane.
     */
    private fun hasTrackingPlane(): Boolean {
        val allPlanes = session!!.getAllTrackables(Plane::class.java)

        for (plane in allPlanes) {
            if (plane.trackingState == TrackingState.TRACKING && plane.type == Plane.Type.HORIZONTAL_UPWARD_FACING) {

                if (coinPlanes.size < 3 && !coinPlanes.containsKey(plane)) {
                    coinPlanes[plane] = 0
                }

                return true
            }
        }

        return false
    }

    /**
     * Handle a single tap per frame
     */
    private fun handleTap(frame: Frame, camera: Camera) {
        val tap = queuedSingleTaps.poll()


        if (tap != null && camera.trackingState == TrackingState.TRACKING) {
            Log.d(TAG, "handleTap: $tap")
            // Check if any plane was hit, and if it was hit inside the plane polygon
            for (hit in frame.hitTest(tap)) {
                val trackable = hit.trackable

                if ((trackable is Plane
                            && trackable.isPoseInPolygon(hit.hitPose)
                            && PlaneRenderer.calculateDistanceToPlane(hit.hitPose, camera.pose) > 0)
                    || (trackable is Point
                            && trackable.orientationMode
                            == Point.OrientationMode.ESTIMATED_SURFACE_NORMAL)
                ) {
                    when (mode) {
                        Mode.STEVE -> steveAttachment = addSessionAnchorFromAttachment(steveAttachment, hit)
                        Mode.SPIDERMAN -> spidermanAttachment = addSessionAnchorFromAttachment(spidermanAttachment, hit)
//                        Mode.COIN -> coinAttachment = addSessionAnchorCoin(hit)
                        Mode.AMOGUS -> amogusAttachment = addSessionAnchorFromAttachment(amogusAttachment, hit)
                    }
                    break
                }
            }
        }
    }

    // Create an anchor if a plane or an oriented point was hit
    private fun addSessionAnchorFromAttachment(
        previousAttachment: PlaneAttachment?,
        hit: HitResult
    ): PlaneAttachment? {
        // 1
        previousAttachment?.anchor?.detach()

        // 2
        val plane = hit.trackable as Plane
        val anchor = session!!.createAnchor(hit.hitPose)

        // 3
        return PlaneAttachment(plane, anchor)
    }


    private fun addSessionAnchorCoin(
        hit: HitResult
    ): PlaneAttachment? {
        // 1
        if (coinAnchors.size > 15) {
            coinAnchors[0].detach()
            coinAnchors.removeAt(0)
        }

        // 2
        val plane = hit.trackable as Plane
        val anchor = session!!.createAnchor(hit.hitPose)

        coinAnchors.add(anchor)
        Log.d(TAG, "addSessionAnchorCoin: $coinAnchors")

        // 3
        return PlaneAttachment(plane, anchor)
    }

}