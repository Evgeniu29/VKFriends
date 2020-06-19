package gen.vk.vkfriends.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gen.vk.vkfriends.R
import gen.vk.vkfriends.model.VKUser

class FriendsAdapter(var contactList: MutableList<VKUser>) :
    RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.friend,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactList[position]
        holder.bindItems(contact)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(user: VKUser) {

            val id = itemView.findViewById<TextView>(R.id.userId)

            val firstName = itemView.findViewById<TextView>(R.id.firstname)

            val lastName = itemView.findViewById<TextView>(R.id.lastname)

            val photo = itemView.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.photo)

            id.text = user.id.toString()

            firstName.text = user.firstName

            lastName.text = user.lastName

            Glide.with(itemView)
                .load(user.photo).centerCrop()
                .placeholder(R.drawable.noimage).error(R.drawable.noimage)
                .fallback(R.drawable.noimage)
                .into(photo)
        }
    }

}

