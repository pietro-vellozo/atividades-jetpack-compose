package br.com.treinamento.alunos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.treinamento.alunos.navigation.AppAlunos
import br.com.treinamento.alunos.ui.theme.AlunosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlunosTheme {
                AppAlunos()
            }
        }
    }
}
