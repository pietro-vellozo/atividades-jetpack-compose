package br.com.treinamento.navegacaoeventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.treinamento.navegacaoeventos.navigation.AppEventos
import br.com.treinamento.navegacaoeventos.ui.theme.NavegacaoEventosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavegacaoEventosTheme {
                AppEventos()
            }
        }
    }
}