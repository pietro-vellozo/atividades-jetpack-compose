package br.com.treinamento.navegacaoeventos.navigation

import android.net.Uri

sealed class Tela(val rota: String) {
    object Inicio : Tela("inicio")
    object Eventos : Tela("eventos")

    object Detalhe : Tela("detalhe/{eventoId}") {
        fun criarRota(eventoId: Int): String {
            return "detalhe/$eventoId"
        }
    }

    object Inscricao : Tela("inscricao/{eventoId}") {
        fun criarRota(eventoId: Int): String {
            return "inscricao/$eventoId"
        }
    }

    object Confirmacao : Tela("confirmacao/{eventoNome}/{participante}") {
        fun criarRota(eventoNome: String, participante: String): String {
            val eventoFormatado = Uri.encode(eventoNome)
            val participanteFormatado = Uri.encode(participante)
            return "confirmacao/$eventoFormatado/$participanteFormatado"
        }
    }
}