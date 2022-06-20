package com.rma.savefy.repos

import com.rma.savefy.data.room.EmailsDao
import com.rma.savefy.models.Emails

class EmailsRepository(private val emailsDao: EmailsDao) {
    suspend fun insertEmail(email: String) {
        emailsDao.insert(Emails(email))
    }

    suspend fun getAllEmails(): List<String> {
        return emailsDao.getAll()
    }

    suspend fun deleteAll() {
        emailsDao.deleteAll()
    }
}