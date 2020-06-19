package gen.vk.vkfriends

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.toast.*

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splach_activity)

        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        if (isConnected) {

            if (VK.isLoggedIn()) {
                MainActivity.startFrom(this)
                finish()
                return
            }

            VK.login(this, arrayListOf(VKScope.WALL, VKScope.PHOTOS))

        } else {

            val customToastLayout = layoutInflater.inflate(R.layout.toast,rootlayout)
            val customToast = Toast(this)
            customToast.view = customToastLayout
            customToast.setGravity(Gravity.CENTER_HORIZONTAL and Gravity.CENTER_VERTICAL,0,0)
            customToast.duration = Toast.LENGTH_LONG

            customToast.show()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                MainActivity.startFrom(this@SplashActivity)
                finish()
            }

            override fun onLoginFailed(errorCode: Int) {
            }
        }
        if (!VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        fun startFrom(context: Context) {
            val intent = Intent(context, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }


}
