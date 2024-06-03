package kr.hs.emirim.evie.testmateloginpage.home

import android.content.Context
import android.widget.HorizontalScrollView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.home.data.TestData

class ScoreChart(private val lineChart: LineChart, private val scrollView: HorizontalScrollView, private val context: Context) {

    fun setupChart(testRecordDataList: List<TestData>) {
        val xAxis: XAxis = lineChart.xAxis // x축
        val yAxisLeft = lineChart.axisLeft // y축 왼쪽 라인

        val entries: MutableList<Entry> = mutableListOf()
        for (i in testRecordDataList.indices) {
            entries.add(Entry(i.toFloat(), testRecordDataList[i].score.toFloat()))
        }
        val lineDataSet = LineDataSet(entries, "entries")

        // X축 구분선 색상 설정
        xAxis.gridColor = context.resources.getColor(R.color.black_100, null)

        // Y축 구분선 색상 설정
        yAxisLeft.gridColor = context.resources.getColor(R.color.black_100, null)

        lineDataSet.apply {
            color = context.resources.getColor(R.color.green_500, null) // 그래프 선 색깔
            circleRadius = 5f
            lineWidth = 3f
            setCircleColor(context.resources.getColor(R.color.green_500, null))
            circleHoleColor = context.resources.getColor(R.color.green_500, null)
            setDrawHighlightIndicators(false)
            setDrawValues(true) // 숫자표시
            valueTextColor = context.resources.getColor(R.color.black, null)
            valueFormatter = DefaultValueFormatter(0)  // 소숫점 자릿수 설정
            valueTextSize = 10f
        }

        //차트 전체 설정
        lineChart.apply {
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
            textColor = context.resources.getColor(R.color.black, null)
            textSize = 10f
            labelRotationAngle = 0f
            setLabelCount(10, true)
        }

        scrollView.post {
            scrollView.scrollTo(
                lineChart.width,
                0
            )
        }

        lineChart.apply {
            data = LineData(lineDataSet)
            notifyDataSetChanged() //데이터 갱신
            invalidate() // view갱신
        }
    }

    private fun changeTestDateText(dataList: List<TestData>): List<String> {
        val dataTextList = ArrayList<String>()
        for (data in dataList) {
            dataTextList.add(data.testDate)
        }
        return dataTextList
    }

    private inner class XAxisCustomFormatter(private val xAxisData: List<String>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return xAxisData[value.toInt()]
        }
    }
}