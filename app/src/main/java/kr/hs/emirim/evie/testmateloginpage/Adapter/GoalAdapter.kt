package kr.hs.emirim.evie.testmateloginpage.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.evie.testmateloginpage.GoalActivity
import kr.hs.emirim.evie.testmateloginpage.Model.GoalModel
//import net.penguincoders.doit.AddNewTask
//import net.penguincoders.doit.MainActivity
//import net.penguincoders.doit.Model.ToDoModel
//import net.penguincoders.doit.R
//import net.penguincoders.doit.Utils.DatabaseHandler

class GoalAdapter :
    RecyclerView.Adapter<GoalAdapter.ViewHolder>() {

    private var todoList: List<GoalModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db.openDatabase()

        val item = todoList[position]
        holder.task.text = item.task
        holder.task.isChecked = toBoolean(item.status)
//        holder.task.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                db.updateStatus(item.id, 1)
//            } else {
//                db.updateStatus(item.id, 0)
//            }
//        }
    }

    private fun toBoolean(n: Int): Boolean {
        return n != 0
    }

//    override fun getItemCount(): Int {
//        return todoList.size
//    }

//    fun getContext(): Context {
//        return activity
//    }

//    fun setTasks(todoList: List<GoalModel>) {
//        this.todoList = todoList
//        notifyDataSetChanged()
//    }

//    fun deleteItem(position: Int) {
//        val item = todoList[position]
//        db.deleteTask(item.id)
//        val tempList = ArrayList(todoList)
//        tempList.removeAt(position)
//        this.todoList = tempList
//        notifyItemRemoved(position)
//    }

//    fun editItem(position: Int) {
//        val item = todoList[position]
//        val bundle = Bundle()
//        bundle.putInt("id", item.id)
//        bundle.putString("task", item.task)
//        val fragment = AddNewTask()
//        fragment.arguments = bundle
//        fragment.show(activity.supportFragmentManager, AddNewTask.TAG)
//    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val task: CheckBox = view.findViewById(R.id.todoCheckBox)
    }
}
