package gen.vk.vkfriends.request

import com.vk.api.sdk.requests.VKRequest
import gen.vk.vkfriends.model.VKUser
import org.json.JSONObject
import java.util.ArrayList

class VKFriendsRequest(uid: Int = 0): VKRequest<List<VKUser>>("friends.get") {
    init {
        if (uid != 0) {
            addParam("user_id", uid)
        }
        addParam("fields", "photo_200")
    }

    override fun parse(r: JSONObject): List<VKUser> {
        val users = r.getJSONObject("response").getJSONArray("items")
        val result = ArrayList<VKUser>()
        for (i in 0 until users.length()) {
            result.add(VKUser.parse(users.getJSONObject(i)))
        }
        return result
    }
}