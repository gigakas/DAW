import android.Manifest
import android.R
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class DJIsdk : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
/*    protected var loginBtn: Button? = null
    protected var logoutBtn: Button? = null
    protected var bindingStateTV: TextView? = null
    protected var appActivationStateTV: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // When the compile and target version is higher than 22, please request the
        // following permissions at runtime to ensure the
        // SDK work well.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.VIBRATE,
                    Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.READ_PHONE_STATE), 1)
        }
        setContentView(R.layout.activity_main)
        initUI()
    }

    public override fun onResume() {
        Log.e(TAG, "onResume")
        super.onResume()
    }

    public override fun onPause() {
        Log.e(TAG, "onPause")
        super.onPause()
    }

    public override fun onStop() {
        Log.e(TAG, "onStop")
        super.onStop()
    }

    fun onReturn(view: View?) {
        Log.e(TAG, "onReturn")
        finish()
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        super.onDestroy()
    }

    private fun initUI() {
        bindingStateTV = findViewById<View>(R.id.tv_binding_state_info) as TextView
        appActivationStateTV = findViewById<View>(R.id.tv_activation_state_info) as TextView
        loginBtn = findViewById<View>(R.id.btn_login) as Button
        logoutBtn = findViewById<View>(R.id.btn_logout) as Button
        loginBtn.setOnClickListener(this)
        logoutBtn.setOnClickListener(this)
    }

    fun onClick(v: View) {
        when (v.getId()) {
            R.id.btn_login -> {
            }
            R.id.btn_logout -> {
            }
            else -> {
            }
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }*/
}
