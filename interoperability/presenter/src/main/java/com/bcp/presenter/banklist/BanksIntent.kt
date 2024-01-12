package com.bcp.presenter.banklist

sealed class BanksIntent {
    data class LoadInit(val phoneNumber: String) : BanksIntent()
}
//nombre numero