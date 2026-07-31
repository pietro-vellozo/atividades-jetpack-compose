package br.com.treinamento.alunos.data.remote

import retrofit2.http.GET

/**
 * Endpoints da API https://countriesnow.space
 * Documentacao: https://documenter.getpostman.com/view/1134062/T1LJjU52
 */
interface CountriesNowApi {

    @GET("api/v0.1/countries/flag/unicode")
    suspend fun listarPaises(): RespostaPaises
}
