package com.ejemplo.recordatorios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Categoria(val id: Int, val nombre: String)
sealed class Prioridad(val etiqueta: String) {
    data object Alta : Prioridad("Alta")
    data object Media : Prioridad("Media")
    data object Baja : Prioridad("Baja")
}
sealed class Estado(val etiqueta: String) {
    data object Pendiente : Estado("Pendiente")
    data object Completado : Estado("Completado")
}
data class Recordatorio(
    val id: Int,
    val titulo: String,
    val categoria: Categoria,
    val prioridad: Prioridad,
    val estado: Estado,
    val descripcion: String? = null,
    val fechaLimite: String? = null
)

val categorias: Set<Categoria> = setOf(
    Categoria(1, "Estudio"), Categoria(2, "Trabajo"), Categoria(3, "Personal")
)
val etiquetasCategoria: Map<String, String> = mapOf(
    "Estudio" to "Azul", "Trabajo" to "Naranja", "Personal" to "Verde"
)
val recordatorios: List<Recordatorio> = listOf(
    Recordatorio(1, "Preparar examen", Categoria(1, "Estudio"), Prioridad.Alta, Estado.Pendiente, "Repasar Kotlin"),
    Recordatorio(2, "Enviar informe", Categoria(2, "Trabajo"), Prioridad.Media, Estado.Pendiente),
    Recordatorio(3, "Comprar alimentos", Categoria(3, "Personal"), Prioridad.Baja, Estado.Completado),
    Recordatorio(4, "Revisar apuntes", Categoria(1, "Estudio"), Prioridad.Media, Estado.Pendiente)
)
val pendientes = recordatorios.filter { it.estado is Estado.Pendiente }
val urgentes = recordatorios.filter { it.prioridad is Prioridad.Alta }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { PantallaInicial() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicial() {
    Scaffold(topBar = { TopAppBar(title = { Text("Mis recordatorios") }) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(recordatorios, key = { it.id }) { recordatorio ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(recordatorio.titulo, style = MaterialTheme.typography.titleMedium)
                        Text("Categoría: ${recordatorio.categoria.nombre} (${etiquetasCategoria[recordatorio.categoria.nombre] ?: "Sin etiqueta"})")
                        Text("Prioridad: ${recordatorio.prioridad.etiqueta}")
                        Text("Estado: ${recordatorio.estado.etiqueta}")
                    }
                }
            }
        }
    }
}
