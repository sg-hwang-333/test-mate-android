package kr.hs.emirim.evie.testmateloginpage.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.HorizontalScrollView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.home.data.TestData

class EditTestRecordActivity : AppCompatActivity() {
    // 시험기록 데이터 생성
    val testRecordDataList: List<TestData> = listOf(
        TestData("1학년 2학기 기말",75),
        TestData("1학년 2학기 중간",60),
        TestData("1학년 1학기 기말",80),
        TestData("1학년 1학기 중간",100),
        TestData("@학년 @학기 중간",89),
        TestData("@학년 @학기 중간",91),
        TestData("@학년 @학기 중간",78),
        TestData("@학년 @학기 중간",96)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_test_record)

        // 성적 그래프
        val linechart = findViewById<LineChart>(R.id.test_record_chart)
        val xAxis = linechart.xAxis
        val yAxisLeft = linechart.axisLeft

        val entries: MutableList<Entry> = mutableListOf() // Entry : 데이터 포인트를 나타내는 클래스
        for (i in testRecordDataList.indices){
            entries.add(Entry(i.toFloat(), testRecordDataList[i].score.toFloat()))
        }
        val lineDataSet = LineDataSet(entries,"entries")

        // X축 구분선 색상 설정
            xAxis.apply {
                gridColor = resources.getColor(R.color.black_100, null) // 원하는 색상으로 변경
            }

        // Y축 구분선 색상 설정
        yAxisLeft.apply {
            gridColor = resources.getColor(R.color.black_100, null) // 원하는 색상으로 변경
        }

        lineDataSet.apply {
            color = resources.getColor(R.color.green_500, null) // 그래프 선 색깔
            circleRadius = 5f
            lineWidth = 3f
            setCircleColor(resources.getColor(R.color.green_500, null))
            circleHoleColor = resources.getColor(R.color.green_500, null)
            setDrawHighlightIndicators(false)
            setDrawValues(true) // 숫자표시
            valueTextColor = resources.getColor(R.color.black, null)
            valueFormatter = DefaultValueFormatter(0)  // 소숫점 자릿수 설정
            valueTextSize = 10f
        }

        //차트 전체 설정
        linechart.apply {
            axisRight.isEnabled = true   //y축 사용여부
            axisLeft.isEnabled = true
            legend.isEnabled = false    //legend 사용여부
            description.isEnabled = false //주석
            isDragXEnabled = true   // x 축 드래그 여부
            isScaleYEnabled = false //y축 줌 사용여부
            isScaleXEnabled = false //x축 줌 사용여부
        }

        //X축 설정
        xAxis.apply {
            setDrawGridLines(false)
            setDrawAxisLine(true)
            setDrawLabels(true)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = XAxisCustomFormatter(changeTestDateText(testRecordDataList))
            textColor = resources.getColor(R.color.black, null)
            textSize = 10f
            labelRotationAngle = 0f
            setLabelCount(10, true)
        }

        val horizontalScrollView = findViewById<HorizontalScrollView>(R.id.scroll_view_graph)
        horizontalScrollView.post{
            horizontalScrollView.scrollTo(
                linechart.width,
                0
            )
        }

        linechart.apply {
            data = LineData(lineDataSet)
            notifyDataSetChanged() //데이터 갱신
            invalidate() // view갱신
        }
        // 성적 그래프
    }

    fun changeTestDateText(dataList: List<TestData>): List<String> {
        val dataTextList = ArrayList<String>()
        for (i in dataList.indices) {
            dataTextList.add(dataList[i].testDate)
        }
        return dataTextList
    }

    class XAxisCustomFormatter(val xAxisData: List<String>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return xAxisData[(value).toInt()]
        }

    }
}