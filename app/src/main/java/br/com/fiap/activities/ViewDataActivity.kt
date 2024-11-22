package br.com.fiap.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.ecopower.databinding.ActivityViewDataBinding
import com.google.firebase.database.*

class ViewDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewDataBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o binding para acessar os elementos do layout
        binding = ActivityViewDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa a referência ao banco de dados Firebase
        database = FirebaseDatabase.getInstance().getReference("dados")

        // Adiciona um listener para observar mudanças nos dados
        loadDataFromDatabase()
    }

    private fun loadDataFromDatabase() {
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList = mutableListOf<String>()

                // Itera por cada nó retornado no snapshot
                for (dataSnapshot in snapshot.children) {
                    val data = dataSnapshot.getValue(String::class.java)
                    data?.let { dataList.add(it) }
                }

                // Atualiza o texto no TextView
                if (dataList.isNotEmpty()) {
                    binding.tvData.text = dataList.joinToString("\n")
                } else binding.tvData.text = "Nenhum dado disponivel."
            }

            override fun onCancelled(error: DatabaseError) {
                // Exibe uma mensagem de erro ao usuário
                Toast.makeText(
                    this@ViewDataActivity,
                    "Erro ao carregar dados: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
