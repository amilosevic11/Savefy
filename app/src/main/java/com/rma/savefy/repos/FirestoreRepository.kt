package com.rma.savefy.repos

import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.Date
import com.rma.savefy.data.sharedprefs.SharedPrefsManager
import com.rma.savefy.helpers.*
import com.rma.savefy.models.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class FirestoreRepository(private val firestore: FirebaseFirestore) {

    private var currentHour: String = ""
    private var currentDay: String = ""
    private var currentMonth: String = ""
    private var currentYear: String = ""

    suspend fun addData(type: String, amount: String, description: String, onResult: (Boolean) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                getCurrentTime()
                val res = Results(
                    Type = type,
                    Amount = amount,
                    Description = description,
                    Year = currentYear,
                    Month = currentMonth,
                    Day = currentDay,
                    Hour = currentHour
                )
                SharedPrefsManager().getUserId()?.let { it ->
                    firestore.collection(it)
                        .add(res)
                        .addOnCompleteListener {
                            onResult(true)
                        }
                        .addOnFailureListener {
                            onResult(false)
                            makeToast(it.message.toString(), lengthLong = false)
                        }
                }
            } catch (e: Exception) {
                makeToast(e.message.toString(), lengthLong = false)
            }
        }
    }

    private fun getCurrentTime() {
        val cal: Calendar = Calendar.getInstance()

        val sdfHour = SimpleDateFormat("hh", Locale.ENGLISH)
        val sdfDay = SimpleDateFormat("dd", Locale.ENGLISH)
        val sdfMonth = SimpleDateFormat("MMMM", Locale.ENGLISH)
        val sdfYear = SimpleDateFormat("yyyy", Locale.ENGLISH)

        currentHour = sdfHour.format(cal.time)
        currentDay = sdfDay.format(cal.time)
        currentMonth = sdfMonth.format(cal.time)
        currentYear = sdfYear.format(cal.time)
    }

        suspend fun getAllData(resultData: (List<Results>) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                SharedPrefsManager().getUserId()?.let { id ->
                    firestore.collection(id)
                        .get()
                        .addOnCompleteListener { task ->
                            val listItems = mutableListOf<Results>()
                            task.result.documents.map { item ->
                                listItems.add(
                                    Results(
                                        item[TYPE].toString(),
                                        item[AMOUNT].toString(),
                                        item[DESCRIPTION].toString(),
                                        item[YEAR].toString(),
                                        item[MONTH].toString(),
                                        item[DAY].toString(),
                                        item[HOUR].toString()
                                    )
                                )
                            }
                            resultData(listItems)
                        }
                }
            } catch (e: Exception) {
                makeToast(e.message.toString(), lengthLong = false)
            }
        }
    }
}