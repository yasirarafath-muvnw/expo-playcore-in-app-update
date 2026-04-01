package expo.modules.inappupdate

import android.util.Log
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition

class ExpoInAppUpdateModule : Module() {
  private val TAG = "ExpoInAppUpdate"
  // A unique code to identify the update request
  private val UPDATE_REQUEST_CODE = 9001

  override fun definition() = ModuleDefinition {
    Name("ExpoInAppUpdate")

    Function("triggerImmediateUpdate") {
      val activity = appContext.currentActivity ?: return@Function null
      val appUpdateManager = AppUpdateManagerFactory.create(activity)
      
      Log.i(TAG, "Requesting app update info...")
      val appUpdateInfoTask = appUpdateManager.appUpdateInfo

      appUpdateInfoTask.addOnSuccessListener { info ->
        Log.i(TAG, "App update info received. UpdateAvailability: ${info.updateAvailability()}, AvailableVersionCode: ${info.availableVersionCode()}")

        // Check if an update is available and if "Immediate" is allowed
        if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
            info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
        ) {
          Log.i(TAG, "Immediate update is available and allowed. Starting update flow...")
          // For Play Core 2.1.0, we MUST use the AppUpdateOptions builder
          val options = AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()

          // Correct Signature: (info, activity, options, requestCode)
          appUpdateManager.startUpdateFlowForResult(
            info,
            activity,
            options,
            UPDATE_REQUEST_CODE
          )
        } else {
          Log.i(TAG, "No immediate update available, or not allowed for this update.")
        }
      }.addOnFailureListener { exception ->
        Log.e(TAG, "Failed to fetch app update info from Play Store", exception)
      }
    }
  }
}