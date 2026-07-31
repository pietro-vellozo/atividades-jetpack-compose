package br.com.treinamento.alunos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Aluno::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AlunoDatabase : RoomDatabase() {

    abstract fun alunoDao(): AlunoDao

    companion object {
        @Volatile
        private var instancia: AlunoDatabase? = null

        fun get(context: Context): AlunoDatabase {
            return instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    AlunoDatabase::class.java,
                    "alunos.db"
                ).build().also { instancia = it }
            }
        }
    }
}
