package kr.hs.emirim.evie.testmateloginpage

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.GridView
import android.widget.LinearLayout
import java.util.Calendar
import java.util.Date

class CalendarView : LinearLayout {
    lateinit var header: LinearLayout
    lateinit var gridView: GridView

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs, 0
    ) {
        initControl(context, attrs!!)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private fun assignUiElements() {
        // layout is inflated, assign local variables to components
        header = findViewById(R.id.calendar_header)
        gridView = findViewById(R.id.calendar_grid)
    }

    fun updateCalendar(events: HashSet<Date>, inputCalendar: Calendar) {
        val cells = ArrayList<Date>()

        inputCalendar.set(Calendar.DAY_OF_MONTH, 1)

        // 여기서 빼주는 값 1의 경우 한 주의 시작요일에 따라 다르게 설정해주면 됨.
        // 필자가 쓴 캘린더의 경우 일요일부터 시작하는 관계로 1을 감산해주었음.
        val monthBeginningCell = inputCalendar.get(Calendar.DAY_OF_WEEK) - 1

        inputCalendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)

        // 그리드에 집어넣을 cell들의 setup.
        while (cells.size < (Calendar.DAY_OF_MONTH) +
            inputCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        ) {
            cells.add(inputCalendar.time)
            inputCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        // 그리드 업데이트
        gridView.adapter =
            CalendarAdapter(context, cells, events, inputCalendar.get(Calendar.MONTH))
    }

    private fun initControl(context: Context, attrs: AttributeSet) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.activity_calendar, this)
        assignUiElements()
    }
}