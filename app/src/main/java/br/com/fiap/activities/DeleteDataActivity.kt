package br.com.fiap.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.ecopower.databinding.ActivityDeleteDataBinding
import com.google.firebase.database.FirebaseDatabase


class DeleteDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataId = intent.getStringExtra("dataId")

        if (dataId.isNullOrEmpty()) {
            Toast.makeText(this, "ID do dado não fornecido.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val database = FirebaseDatabase.getInstance().getReference("dados").child(dataId)

        binding.btnDelete.setOnClickListener {
            database.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Dado excluído com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Falha ao excluir dado.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
