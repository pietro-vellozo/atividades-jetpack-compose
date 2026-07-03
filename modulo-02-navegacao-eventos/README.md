# Modulo 02 - Navegacao e Fluxo de Telas

Aplicativo simples de eventos criado com Jetpack Compose e Material 3.

## O que foi praticado

- `Scaffold` como estrutura principal da aplicacao.
- `NavHost` e `NavController` para navegar entre telas.
- Passagem de parametros pela rota, como `eventoId`, `eventoNome` e `participante`.
- Separacao em `model`, `navigation`, `ui/componentes`, `ui/telas` e `ui/theme`.
- Estado local com `remember { mutableStateOf(...) }` no formulario de inscricao.

## Telas

- Inicio
- Lista de eventos
- Detalhe do evento
- Inscricao
- Confirmacao

## Como abrir

Abra esta pasta no Android Studio:

```text
modulo-02-navegacao-eventos
```

## Validacao

```powershell
.\gradlew.bat :app:compileDebugKotlin
```