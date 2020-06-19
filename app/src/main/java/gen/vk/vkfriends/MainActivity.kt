package gen.vk.vkfriends

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import gen.vk.vkfriends.adapter.FriendsAdapter
import gen.vk.vkfriends.model.VKUser
import gen.vk.vkfriends.request.VKFriendsRequest

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        logoutBtn.setOnClickListener {
            VK.logout()
            SplashActivity.startFrom(this)
            finish()
        }

        requestFriends()

    }

    private fun showFriends(friends: List<VKUser>) {
        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = FriendsAdapter(friends as MutableList<VKUser>)

        recyclerView.adapter = adapter
    }

    private fun requestFriends() {
        VK.execute(VKFriendsRequest(), object: VKApiCallback<List<VKUser>> {
            override fun success(result: List<VKUser>) {
                if (!isFinishing && !result.isEmpty()) {
                    showFriends(result)
                }
            }
            override fun fail(error: Exception) {
                Log.e(TAG, error.toString())
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"

        private const val IMAGE_REQ_CODE = 101

        fun startFrom(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

}

