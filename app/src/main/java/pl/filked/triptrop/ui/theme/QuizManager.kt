package pl.filked.triptrop

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.filked.triptrop.data.QuizQuestion

object QuizManager {

    fun loadQuestions(context: Context): List<QuizQuestion> {

        val json = context.resources
            .openRawResource(R.raw.zagadki)
            .bufferedReader()
            .use {
                it.readText()
            }

        val type =
            object : TypeToken<List<QuizQuestion>>() {}.type

        return Gson().fromJson(json, type)
    }
}