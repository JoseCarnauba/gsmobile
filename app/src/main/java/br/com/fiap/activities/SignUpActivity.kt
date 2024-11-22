package br.com.fiap.activities

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.ecopower.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o binding para acessar os elementos do layout
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Configura o botão de cadastro
        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Valida os campos de entrada
            if (validateInputs(email, password)) {
                // Tenta criar o usuário no Firebase
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                            finish() // Fecha a tela de cadastro após sucesso
                        } else {
                            Toast.makeText(this, "Erro ao cadastrar usuário: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    // Função para validar os campos de entrada
    private fun validateInputs(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                Toast.makeText(this, "Por favor, insira um e-mail.", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(password) -> {
                Toast.makeText(this, "Por favor, insira uma senha.", Toast.LENGTH_SHORT).show()
                false
            }
            password.length < 6 -> {
                Toast.makeText(this, "A senha deve ter pelo menos 6 caracteres.", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}
