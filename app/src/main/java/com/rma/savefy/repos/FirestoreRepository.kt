package com.rma.savefy.repos

import com.google.firebase.firestore.FirebaseFirestore
import com.rma.savefy.data.sharedprefs.SharedPrefsManager
import com.rma.savefy.helpers.*
import com.rma.savefy.models.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirestoreRepository(private val firestore: FirebaseFirestore) {

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