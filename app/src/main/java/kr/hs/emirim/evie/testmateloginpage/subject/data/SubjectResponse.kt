package kr.hs.emirim.evie.testmateloginpage.subject.data

import java.io.Serializable


data class SubjectResponse (
    val subjectId: Int,
    val subjectName: String,
    val img: String
) : Serializable
