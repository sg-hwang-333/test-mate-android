package kr.hs.emirim.evie.testmateloginpage.alarm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.alarm.data.NewAlarm

class NewAlarmAdapter(private val context: Context) :
    ListAdapter<NewAlarm, NewAlarmAdapter.AlarmViewHolder>(NewAlarmDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = getItem(position)
        holder.bind(alarm)
    }

    inner class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val subjectTextView: TextView = itemView.findViewById(R.id.subject)
        private val dateTextView: TextView = itemView.findViewById(R.id.date)

        fun bind(alarm: NewAlarm) {
            subjectTextView.text = alarm.subject
            dateTextView.text = alarm.date.toString()
        }
    }
}

class NewAlarmDiffCallback : DiffUtil.ItemCallback<NewAlarm>() {
    override fun areItemsTheSame(oldItem: NewAlarm, newItem: NewAlarm): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NewAlarm, newItem: NewAlarm): Boolean {
        return oldItem == newItem
    }
}
