package com.example.composeexample

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeexample.model.*
import com.example.composeexample.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val _latestState = MutableStateFlow<ResponseState>(ResponseState.Loading)
    val latestState: StateFlow<ResponseState>
        get() = _latestState

    val sort = mutableStateOf(SortEnum.AZ_UP)
    private var pollJob: Job? = null

    val coin = mutableStateOf("USD")
    private var rates: Rates? = null
    private var list = listOf<OneCoin>()

    fun changeCoin(coin: String) {
        this.coin.value = coin
        getLatest()

    }

    fun getLatest() {
        pollJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = coinRepository.getLatest(coin.value)
                rates = result.rates
                list = mapToList(result.rates)
                val sortedList = sortList(list)
                _latestState.emit(ResponseState.Data(sortedList))
            } catch (e: Exception) {
                _latestState.emit(ResponseState.Error(e))
            }
        }
    }

    fun changeFilter(sort: SortEnum) {
        this.sort.value = sort
        pollJob = viewModelScope.launch(Dispatchers.IO) {
            _latestState.emit(ResponseState.Data(sortList(list)))
        }
    }

    private fun checkIsFavourite(name: String): Boolean {
        val isFavourite = coinRepository.getCoin(name)
        return isFavourite != null
    }

    fun addToFavourite(name: String) {
        pollJob = viewModelScope.launch(Dispatchers.IO) {
            coinRepository.insertCoin(Coin(name = name))
        }
    }

    fun deleteFromFavourite(name: String) {
        pollJob = viewModelScope.launch(Dispatchers.IO) {
            coinRepository.deleteCoin(name)
        }
    }

    private fun sortList(list: List<OneCoin>): List<OneCoin> {
        return when (sort.value) {
            SortEnum.AZ_UP -> {
                list.sortedBy { it.name }
            }
            SortEnum.AZ_DOWN -> {
                list.sortedBy { it.name }.reversed()
            }
            SortEnum.VALUE_UP -> {
                list.sortedBy { it.value }
            }
            SortEnum.VALUE_DOWN -> {
                list.sortedBy { it.value }.reversed()
            }
        }
    }

    private fun mapToList(rates: Rates) = listOf(
        OneCoin(name = "AED", value = rates.AED, checkIsFavourite("AED")),
        OneCoin(name = "AFN", value = rates.AFN, checkIsFavourite("AFN")),
        OneCoin(name = "ALL", value = rates.ALL, checkIsFavourite("ALL")),
        OneCoin(name = "AMD", value = rates.AMD, checkIsFavourite("AMD")),
        OneCoin(name = "ANG", value = rates.ANG, checkIsFavourite("ANG")),
        OneCoin(name = "AOA", value = rates.AOA, checkIsFavourite("AOA")),
        OneCoin(name = "ARS", value = rates.ARS, checkIsFavourite("ARS")),
        OneCoin(name = "AUD", value = rates.AUD, checkIsFavourite("AUD")),
        OneCoin(name = "AWG", value = rates.AWG, checkIsFavourite("AWG")),
        OneCoin(name = "AZN", value = rates.AZN, checkIsFavourite("AZN")),
        OneCoin(name = "BAM", value = rates.BAM, checkIsFavourite("BAM")),
        OneCoin(name = "BBD", value = rates.BBD, checkIsFavourite("BBD")),
        OneCoin(name = "BDT", value = rates.BDT, checkIsFavourite("BDT")),
        OneCoin(name = "BGN", value = rates.BGN, checkIsFavourite("BGN")),
        OneCoin(name = "BHD", value = rates.BHD, checkIsFavourite("BHD")),
        OneCoin(name = "BIF", value = rates.BIF, checkIsFavourite("BIF")),
        OneCoin(name = "BMD", value = rates.BMD, checkIsFavourite("BMD")),
        OneCoin(name = "BND", value = rates.BND, checkIsFavourite("BND")),
        OneCoin(name = "BOB", value = rates.BOB, checkIsFavourite("BOB")),
        OneCoin(name = "BRL", value = rates.BRL, checkIsFavourite("BRL")),
        OneCoin(name = "BSD", value = rates.BSD, checkIsFavourite("BSD")),
        OneCoin(name = "BTC", value = rates.BTC, checkIsFavourite("BTC")),
        OneCoin(name = "BTN", value = rates.BTN, checkIsFavourite("BTN")),
        OneCoin(name = "BWP", value = rates.BWP, checkIsFavourite("BWP")),
        OneCoin(name = "BYN", value = rates.BYN, checkIsFavourite("BYN")),
        OneCoin(name = "BYR", value = rates.BYR, checkIsFavourite("BYR")),
        OneCoin(name = "BZD", value = rates.BZD, checkIsFavourite("BZD")),
        OneCoin(name = "CAD", value = rates.CAD, checkIsFavourite("CAD")),
        OneCoin(name = "CDF", value = rates.CDF, checkIsFavourite("CDF")),
        OneCoin(name = "CHF", value = rates.CHF, checkIsFavourite("CHF")),
        OneCoin(name = "CLF", value = rates.CLF, checkIsFavourite("CLF")),
        OneCoin(name = "CLP", value = rates.CLP, checkIsFavourite("CLP")),
        OneCoin(name = "CNY", value = rates.CNY, checkIsFavourite("CNY")),
        OneCoin(name = "COP", value = rates.COP, checkIsFavourite("COP")),
        OneCoin(name = "CRC", value = rates.CRC, checkIsFavourite("CRC")),
        OneCoin(name = "CUC", value = rates.CUC, checkIsFavourite("CUC")),
        OneCoin(name = "CUP", value = rates.CUP, checkIsFavourite("CUP")),
        OneCoin(name = "CVE", value = rates.CVE, checkIsFavourite("CVE")),
        OneCoin(name = "CZK", value = rates.CZK, checkIsFavourite("CZK")),
        OneCoin(name = "DJF", value = rates.DJF, checkIsFavourite("DJF")),
        OneCoin(name = "DKK", value = rates.DKK, checkIsFavourite("DKK")),
        OneCoin(name = "DOP", value = rates.DOP, checkIsFavourite("DOP")),
        OneCoin(name = "DZD", value = rates.DZD, checkIsFavourite("DZD")),
        OneCoin(name = "EGP", value = rates.EGP, checkIsFavourite("EGP")),
        OneCoin(name = "ERN", value = rates.ERN, checkIsFavourite("ERN")),
        OneCoin(name = "ETB", value = rates.ETB, checkIsFavourite("ETB")),
        OneCoin(name = "EUR", value = rates.EUR, checkIsFavourite("EUR")),
        OneCoin(name = "FJD", value = rates.FJD, checkIsFavourite("FJD")),
        OneCoin(name = "FKP", value = rates.FKP, checkIsFavourite("FKP")),
        OneCoin(name = "GBP", value = rates.GBP, checkIsFavourite("GBP")),
        OneCoin(name = "GEL", value = rates.GEL, checkIsFavourite("GEL")),
        OneCoin(name = "GGP", value = rates.GGP, checkIsFavourite("GGP")),
        OneCoin(name = "GHS", value = rates.GHS, checkIsFavourite("GHS")),
        OneCoin(name = "GIP", value = rates.GIP, checkIsFavourite("GIP")),
        OneCoin(name = "GMD", value = rates.GMD, checkIsFavourite("GMD")),
        OneCoin(name = "GNF", value = rates.GNF, checkIsFavourite("GNF")),
        OneCoin(name = "GTQ", value = rates.GTQ, checkIsFavourite("GTQ")),
        OneCoin(name = "GYD", value = rates.GYD, checkIsFavourite("GYD")),
        OneCoin(name = "HKD", value = rates.HKD, checkIsFavourite("HKD")),
        OneCoin(name = "HNL", value = rates.HNL, checkIsFavourite("HNL")),
        OneCoin(name = "HRK", value = rates.HRK, checkIsFavourite("HRK")),
        OneCoin(name = "HTG", value = rates.HTG, checkIsFavourite("HTG")),
        OneCoin(name = "HUF", value = rates.HUF, checkIsFavourite("HUF")),
        OneCoin(name = "IDR", value = rates.IDR, checkIsFavourite("IDR")),
        OneCoin(name = "ILS", value = rates.ILS, checkIsFavourite("ILS")),
        OneCoin(name = "IMP", value = rates.IMP, checkIsFavourite("IMP")),
        OneCoin(name = "INR", value = rates.INR, checkIsFavourite("INR")),
        OneCoin(name = "IQD", value = rates.IQD, checkIsFavourite("IQD")),
        OneCoin(name = "IRR", value = rates.IRR, checkIsFavourite("IRR")),
        OneCoin(name = "ISK", value = rates.ISK, checkIsFavourite("ISK")),
        OneCoin(name = "JEP", value = rates.JEP, checkIsFavourite("JEP")),
        OneCoin(name = "JMD", value = rates.JMD, checkIsFavourite("JMD")),
        OneCoin(name = "JOD", value = rates.JOD, checkIsFavourite("JOD")),
        OneCoin(name = "JPY", value = rates.JPY, checkIsFavourite("JPY")),
        OneCoin(name = "KES", value = rates.KES, checkIsFavourite("KES")),
        OneCoin(name = "KGS", value = rates.KGS, checkIsFavourite("KGS")),
        OneCoin(name = "KHR", value = rates.KHR, checkIsFavourite("KHR")),
        OneCoin(name = "KMF", value = rates.KMF, checkIsFavourite("KMF")),
        OneCoin(name = "KPW", value = rates.KPW, checkIsFavourite("KPW")),
        OneCoin(name = "KRW", value = rates.KRW, checkIsFavourite("KRW")),
        OneCoin(name = "KWD", value = rates.KWD, checkIsFavourite("KWD")),
        OneCoin(name = "KYD", value = rates.KYD, checkIsFavourite("KYD")),
        OneCoin(name = "KZT", value = rates.KZT, checkIsFavourite("KZT")),
        OneCoin(name = "LAK", value = rates.LAK, checkIsFavourite("LAK")),
        OneCoin(name = "LBP", value = rates.LBP, checkIsFavourite("LBP")),
        OneCoin(name = "LKR", value = rates.LKR, checkIsFavourite("LKR")),
        OneCoin(name = "LRD", value = rates.LRD, checkIsFavourite("LRD")),
        OneCoin(name = "LSL", value = rates.LSL, checkIsFavourite("LSL")),
        OneCoin(name = "LTL", value = rates.LTL, checkIsFavourite("LTL")),
        OneCoin(name = "LVL", value = rates.LVL, checkIsFavourite("LVL")),
        OneCoin(name = "LYD", value = rates.LYD, checkIsFavourite("LYD")),
        OneCoin(name = "MAD", value = rates.MAD, checkIsFavourite("MAD")),
        OneCoin(name = "MDL", value = rates.MDL, checkIsFavourite("MDL")),
        OneCoin(name = "MGA", value = rates.MGA, checkIsFavourite("MGA")),
        OneCoin(name = "MKD", value = rates.MKD, checkIsFavourite("MKD")),
        OneCoin(name = "MMK", value = rates.MMK, checkIsFavourite("MMK")),
        OneCoin(name = "MNT", value = rates.MNT, checkIsFavourite("MNT")),
        OneCoin(name = "MOP", value = rates.MOP, checkIsFavourite("MOP")),
        OneCoin(name = "MRO", value = rates.MRO, checkIsFavourite("MRO")),
        OneCoin(name = "MUR", value = rates.MUR, checkIsFavourite("MUR")),
        OneCoin(name = "MVR", value = rates.MVR, checkIsFavourite("MVR")),
        OneCoin(name = "MWK", value = rates.MWK, checkIsFavourite("MWK")),
        OneCoin(name = "MXN", value = rates.MXN, checkIsFavourite("MXN")),
        OneCoin(name = "MYR", value = rates.MYR, checkIsFavourite("MYR")),
        OneCoin(name = "MZN", value = rates.MZN, checkIsFavourite("MZN")),
        OneCoin(name = "NAD", value = rates.NAD, checkIsFavourite("NAD")),
        OneCoin(name = "NGN", value = rates.NGN, checkIsFavourite("NGN")),
        OneCoin(name = "NIO", value = rates.NIO, checkIsFavourite("NIO")),
        OneCoin(name = "NOK", value = rates.NOK, checkIsFavourite("NOK")),
        OneCoin(name = "NPR", value = rates.NPR, checkIsFavourite("NPR")),
        OneCoin(name = "NZD", value = rates.NZD, checkIsFavourite("NZD")),
        OneCoin(name = "OMR", value = rates.OMR, checkIsFavourite("OMR")),
        OneCoin(name = "PAB", value = rates.PAB, checkIsFavourite("PAB")),
        OneCoin(name = "PAB", value = rates.PAB, checkIsFavourite("PAB")),
        OneCoin(name = "PEN", value = rates.PEN, checkIsFavourite("PEN")),
        OneCoin(name = "PGK", value = rates.PGK, checkIsFavourite("PGK")),
        OneCoin(name = "PHP", value = rates.PHP, checkIsFavourite("PHP")),
        OneCoin(name = "PKR", value = rates.PKR, checkIsFavourite("PKR")),
        OneCoin(name = "PLN", value = rates.PLN, checkIsFavourite("PLN")),
        OneCoin(name = "PYG", value = rates.PYG, checkIsFavourite("PYG")),
        OneCoin(name = "QAR", value = rates.QAR, checkIsFavourite("QAR")),
        OneCoin(name = "RON", value = rates.RON, checkIsFavourite("RON")),
        OneCoin(name = "RSD", value = rates.RSD, checkIsFavourite("RSD")),
        OneCoin(name = "RUB", value = rates.RUB, checkIsFavourite("RUB")),
        OneCoin(name = "RWF", value = rates.RWF, checkIsFavourite("RWF")),
        OneCoin(name = "SAR", value = rates.SAR, checkIsFavourite("SAR")),
        OneCoin(name = "SBD", value = rates.SBD, checkIsFavourite("SBD")),
        OneCoin(name = "SCR", value = rates.SCR, checkIsFavourite("SCR")),
        OneCoin(name = "SDG", value = rates.SDG, checkIsFavourite("SDG")),
        OneCoin(name = "SEK", value = rates.SEK, checkIsFavourite("SEK")),
        OneCoin(name = "SGD", value = rates.SGD, checkIsFavourite("SGD")),
        OneCoin(name = "SHP", value = rates.SHP, checkIsFavourite("SHP")),
        OneCoin(name = "SLL", value = rates.SLL, checkIsFavourite("SLL")),
        OneCoin(name = "SOS", value = rates.SOS, checkIsFavourite("SOS")),
        OneCoin(name = "SRD", value = rates.SRD, checkIsFavourite("SRD")),
        OneCoin(name = "STD", value = rates.STD, checkIsFavourite("STD")),
        OneCoin(name = "SVC", value = rates.SVC, checkIsFavourite("SVC")),
        OneCoin(name = "SYP", value = rates.SYP, checkIsFavourite("SYP")),
        OneCoin(name = "SZL", value = rates.SZL, checkIsFavourite("SZL")),
        OneCoin(name = "THB", value = rates.THB, checkIsFavourite("THB")),
        OneCoin(name = "TJS", value = rates.TJS, checkIsFavourite("TJS")),
        OneCoin(name = "TMT", value = rates.TMT, checkIsFavourite("TMT")),
        OneCoin(name = "TND", value = rates.TND, checkIsFavourite("TND")),
        OneCoin(name = "TOP", value = rates.TOP, checkIsFavourite("TOP")),
        OneCoin(name = "TRY", value = rates.TRY, checkIsFavourite("TRY")),
        OneCoin(name = "TTD", value = rates.TTD, checkIsFavourite("TTD")),
        OneCoin(name = "TWD", value = rates.TWD, checkIsFavourite("TWD")),
        OneCoin(name = "TZS", value = rates.TZS, checkIsFavourite("TZS")),
        OneCoin(name = "UAH", value = rates.UAH, checkIsFavourite("UAH")),
        OneCoin(name = "UGX", value = rates.UGX, checkIsFavourite("UGX")),
        OneCoin(name = "USD", value = rates.USD, checkIsFavourite("USD")),
        OneCoin(name = "UYU", value = rates.UYU, checkIsFavourite("UYU")),
        OneCoin(name = "UZS", value = rates.UZS, checkIsFavourite("UZS")),
        OneCoin(name = "VEF", value = rates.VEF, checkIsFavourite("VEF")),
        OneCoin(name = "VND", value = rates.VND, checkIsFavourite("VND")),
        OneCoin(name = "VUV", value = rates.VUV, checkIsFavourite("VUV")),
        OneCoin(name = "WST", value = rates.WST, checkIsFavourite("WST")),
        OneCoin(name = "XAF", value = rates.XAF, checkIsFavourite("XAF")),
        OneCoin(name = "XAG", value = rates.XAG, checkIsFavourite("XAG")),
        OneCoin(name = "XAU", value = rates.XAU, checkIsFavourite("XAU")),
        OneCoin(name = "XCD", value = rates.XCD, checkIsFavourite("XCD")),
        OneCoin(name = "XDR", value = rates.XDR, checkIsFavourite("XDR")),
        OneCoin(name = "XOF", value = rates.XOF, checkIsFavourite("XOF")),
        OneCoin(name = "XPF", value = rates.XPF, checkIsFavourite("XPF")),
        OneCoin(name = "YER", value = rates.YER, checkIsFavourite("YER")),
        OneCoin(name = "ZAR", value = rates.ZAR, checkIsFavourite("ZAR")),
        OneCoin(name = "ZMK", value = rates.ZMK, checkIsFavourite("ZMK")),
        OneCoin(name = "ZMW", value = rates.ZMW, checkIsFavourite("ZMW")),
        OneCoin(name = "ZWL", value = rates.ZWL, checkIsFavourite("ZWL"))
    )
}
