package br.com.fiap.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.ecopower.databinding.ActivityEditDataBinding
import com.google.firebase.database.FirebaseDatabase


class EditDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtém o ID do dado para edição
        val dataId = intent.getStringExtra("dataId")

        if (dataId.isNullOrEmpty()) {
            Toast.makeText(this, "ID do dado não fornecido.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Referência ao nó específico no Firebase
        val database = FirebaseDatabase.getInstance().getReference("dados").child(dataId)

        // Botão para salvar alterações
        binding.btnSaveChanges.setOnClickListener {
            val updatedData = binding.etData.text.toString()

            if (updatedData.isNotEmpty()) {
                database.setValue(updatedData).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Falha ao atualizar dados.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, preencha os dados.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
