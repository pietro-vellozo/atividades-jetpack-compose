package br.com.treinamento.cadastroform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.treinamento.cadastroform.ui.telas.TelaCadastro
import br.com.treinamento.cadastroform.ui.theme.CadastroFormTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CadastroFormTheme {
                TelaCadastro()
            }
        }
    }
}