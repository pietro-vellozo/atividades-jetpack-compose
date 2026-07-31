package br.com.treinamento.alunos.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountriesNowClient {

    private const val BASE_URL = "https://countriesnow.space/"

    val api: CountriesNowApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesNowApi::class.java)
    }
}
