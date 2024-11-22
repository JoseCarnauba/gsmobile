package br.com.fiap.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.ecopower.databinding.ActivityAddDataBinding
import com.google.firebase.database.FirebaseDatabase


class AddDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveData.setOnClickListener {
            val energyConsumed = binding.etEnergyConsumed.text.toString() // Campo 1
            val appliance = binding.etAppliance.text.toString() // Campo 2

            if (energyConsumed.isNotEmpty() && appliance.isNotEmpty()) {
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("dados").push()

                val dataMap = mapOf(
                    "energyConsumed" to energyConsumed,
                    "appliance" to appliance
                )

                myRef.setValue(dataMap).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Falha ao salvar os dados.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
