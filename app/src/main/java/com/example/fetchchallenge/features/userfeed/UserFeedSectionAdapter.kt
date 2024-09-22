package layout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchchallenge.R
import com.example.fetchchallenge.data.User
import com.example.fetchchallenge.databinding.UserCellBinding

class UserFeedSectionAdapter(private val users: List<User>)
        : RecyclerView.Adapter<UserFeedSectionAdapter.UserFeedSectionViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFeedSectionViewHolder {
            val binding = UserCellBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return UserFeedSectionViewHolder(binding)
        }

        override fun getItemCount() = users.size

        override fun onBindViewHolder(holder: UserFeedSectionViewHolder, position: Int) {
            with(holder) {
                with(users[position]) {
                    binding.id.text = itemView.context.getString(R.string.user_id_format, id.toString())
                    binding.name.text = itemView.context.getString(R.string.name_format, name)
                }
            }
        }

        inner class UserFeedSectionViewHolder(val binding: UserCellBinding)
            : RecyclerView.ViewHolder(binding.root)
}