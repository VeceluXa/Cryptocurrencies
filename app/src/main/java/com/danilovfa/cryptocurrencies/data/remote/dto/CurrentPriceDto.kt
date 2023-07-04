package com.danilovfa.cryptocurrencies.data.remote.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CurrentPriceDto(
    @SerializedName("aed")
    val aed: Int,
    @SerializedName("ars")
    val ars: Int,
    @SerializedName("aud")
    val aud: Int,
    @SerializedName("bch")
    val bch: Double,
    @SerializedName("bdt")
    val bdt: Int,
    @SerializedName("bhd")
    val bhd: Double,
    @SerializedName("bits")
    val bits: Int,
    @SerializedName("bmd")
    val bmd: Int,
    @SerializedName("bnb")
    val bnb: Double,
    @SerializedName("brl")
    val brl: Int,
    @SerializedName("btc")
    val btc: Int,
    @SerializedName("cad")
    val cad: Int,
    @SerializedName("chf")
    val chf: Int,
    @SerializedName("clp")
    val clp: Int,
    @SerializedName("cny")
    val cny: Int,
    @SerializedName("czk")
    val czk: Int,
    @SerializedName("dkk")
    val dkk: Int,
    @SerializedName("dot")
    val dot: Int,
    @SerializedName("eos")
    val eos: Int,
    @SerializedName("eth")
    val eth: Double,
    @SerializedName("eur")
    val eur: Int,
    @SerializedName("gbp")
    val gbp: Int,
    @SerializedName("hkd")
    val hkd: Int,
    @SerializedName("huf")
    val huf: Int,
    @SerializedName("idr")
    val idr: Int,
    @SerializedName("ils")
    val ils: Int,
    @SerializedName("inr")
    val inr: Int,
    @SerializedName("jpy")
    val jpy: Int,
    @SerializedName("krw")
    val krw: Int,
    @SerializedName("kwd")
    val kwd: Double,
    @SerializedName("link")
    val link: Int,
    @SerializedName("lkr")
    val lkr: Int,
    @SerializedName("ltc")
    val ltc: Double,
    @SerializedName("mmk")
    val mmk: Int,
    @SerializedName("mxn")
    val mxn: Int,
    @SerializedName("myr")
    val myr: Int,
    @SerializedName("ngn")
    val ngn: Int,
    @SerializedName("nok")
    val nok: Int,
    @SerializedName("nzd")
    val nzd: Int,
    @SerializedName("php")
    val php: Int,
    @SerializedName("pkr")
    val pkr: Int,
    @SerializedName("pln")
    val pln: Int,
    @SerializedName("rub")
    val rub: Int,
    @SerializedName("sar")
    val sar: Int,
    @SerializedName("sats")
    val sats: Int,
    @SerializedName("sek")
    val sek: Int,
    @SerializedName("sgd")
    val sgd: Int,
    @SerializedName("thb")
    val thb: Int,
    @SerializedName("try")
    val tryX: Int,
    @SerializedName("twd")
    val twd: Int,
    @SerializedName("uah")
    val uah: Int,
    @SerializedName("usd")
    val usd: Int,
    @SerializedName("vef")
    val vef: Double,
    @SerializedName("vnd")
    val vnd: Int,
    @SerializedName("xag")
    val xag: Double,
    @SerializedName("xau")
    val xau: Double,
    @SerializedName("xdr")
    val xdr: Int,
    @SerializedName("xlm")
    val xlm: Int,
    @SerializedName("xrp")
    val xrp: Int,
    @SerializedName("yfi")
    val yfi: Double,
    @SerializedName("zar")
    val zar: Int
)