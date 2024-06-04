package kr.hs.emirim.evie.testmateloginpage.util

import android.content.Context
import android.content.res.Resources

// 이미지를 setImageResource 할 때, String 타입 이미지 주소를 int로 전환해주는 class!!
class ImgResourceStringToInt {
    companion object { // java의 static이랑 같은 역할 (이 안의 메서드는 클래스 이름으로 호출 가능)
        fun getResourceId(resName: String?, context: Context): Int {
            val resContext = context.createPackageContext(context.packageName, 0)
            val res: Resources = resContext.resources
            return res.getIdentifier(resName, "drawable", context.packageName)
        }
    }
}