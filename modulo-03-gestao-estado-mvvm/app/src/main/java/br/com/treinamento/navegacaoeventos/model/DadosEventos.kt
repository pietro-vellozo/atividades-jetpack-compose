package br.com.treinamento.navegacaoeventos.model

val listaEventos = listOf(
    Evento(
        id = 1,
        nome = "Compose Day",
        categoria = "Android",
        data = "12/08/2026",
        horario = "19:00",
        local = "Sala 01",
        vagas = 35,
        descricao = "Encontro para praticar telas, estados e componentes em Jetpack Compose."
    ),
    Evento(
        id = 2,
        nome = "Design de Telas",
        categoria = "UI",
        data = "15/08/2026",
        horario = "20:00",
        local = "Laboratorio 02",
        vagas = 24,
        descricao = "Atividade focada em organizacao visual, cards, formularios e hierarquia de textos."
    ),
    Evento(
        id = 3,
        nome = "Navegacao na Pratica",
        categoria = "Fluxo",
        data = "18/08/2026",
        horario = "19:30",
        local = "Auditorio",
        vagas = 40,
        descricao = "Aula pratica para navegar entre telas e enviar parametros de uma tela para outra."
    )
)