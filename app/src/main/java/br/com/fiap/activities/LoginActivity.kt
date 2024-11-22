package br.com.fiap.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.ecopower.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configura o botão de login
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Validação de campos
            if (email.isEmpty() || password.isEmpty()) {
                binding.tvError.text = "Por favor, preencha todos os campos."
                return@setOnClickListener
            }

            // Realiza o login com Firebase Auth
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Login bem-sucedido, redireciona para a MainActivity
                        startActivity(Intent(this, br.com.fiap.MainActivity::class.java))
                        finish()
                    } else {
                        // Exibe a mensagem de erro no TextView
                        binding.tvError.text = "Falha no login. Verifique suas credenciais."
                    }
                }
                .addOnFailureListener { exception ->
                    // Mensagem adicional em caso de erro específico
                    Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
        }

        // Configura o redirecionamento para a tela de cadastro
        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
