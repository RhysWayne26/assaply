package com.example.assaply.data.domain.usecases.app_entry

import com.example.assaply.data.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }

}