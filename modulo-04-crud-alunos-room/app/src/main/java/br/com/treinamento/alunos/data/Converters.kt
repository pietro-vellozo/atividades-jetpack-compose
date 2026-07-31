package br.com.treinamento.alunos.data

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun deListaParaTexto(notas: List<Double>): String {
        return notas.joinToString(separator = ";")
    }

    @TypeConverter
    fun deTextoParaLista(texto: String): List<Double> {
        if (texto.isBlank()) return emptyList()
        return texto.split(";").mapNotNull { valor -> valor.toDoubleOrNull() }
    }
}
