package br.com.fiap.firebase

import com.google.firebase.database.*

class FirebaseDatabaseHelper {

    // Instância do Firebase Realtime Database
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    /**
     * Salva dados no Realtime Database
     * @param path Caminho no banco de dados
     * @param data Dados a serem salvos
     * @param onComplete Callback para indicar sucesso ou falha
     */
    fun saveData(path: String, data: Any, onComplete: (Boolean, String?) -> Unit) {
        val ref: DatabaseReference = database.getReference(path)
        val key = ref.push().key // Gera uma chave única para os dados

        if (key == null) {
            onComplete(false, "Erro ao gerar chave para os dados")
            return
        }

        ref.child(key).setValue(data)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Dados salvos com sucesso")
                } else {
                    onComplete(false, task.exception?.localizedMessage ?: "Erro desconhecido ao salvar dados")
                }
            }
    }

    /**
     * Busca todos os dados de um caminho específico
     * @param path Caminho no banco de dados
     * @param onComplete Callback com os dados ou uma mensagem de erro
     */
    fun fetchData(path: String, onComplete: (List<String>?, String?) -> Unit) {
        val ref: DatabaseReference = database.getReference(path)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList = mutableListOf<String>()

                // Itera pelos filhos do snapshot e converte em String
                for (dataSnapshot in snapshot.children) {
                    val data = dataSnapshot.getValue(String::class.java)
                    data?.let { dataList.add(it) }
                }

                onComplete(dataList, null) // Retorna a lista de dados
            }

            override fun onCancelled(error: DatabaseError) {
                onComplete(null, error.message) // Retorna o erro
            }
        })
    }

    /**
     * Atualiza dados no Realtime Database
     * @param path Caminho no banco de dados
     * @param key Chave do item a ser atualizado
     * @param data Dados atualizados
     * @param onComplete Callback para indicar sucesso ou falha
     */
    fun updateData(path: String, key: String, data: Any, onComplete: (Boolean, String?) -> Unit) {
        val ref: DatabaseReference = database.getReference(path).child(key)

        ref.setValue(data)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Dados atualizados com sucesso")
                } else {
                    onComplete(false, task.exception?.localizedMessage ?: "Erro desconhecido ao atualizar dados")
                }
            }
    }

    /**
     * Deleta dados do Realtime Database
     * @param path Caminho no banco de dados
     * @param key Chave do item a ser deletado
     * @param onComplete Callback para indicar sucesso ou falha
     */
    fun deleteData(path: String, key: String, onComplete: (Boolean, String?) -> Unit) {
        val ref: DatabaseReference = database.getReference(path).child(key)

        ref.removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Dados deletados com sucesso")
                } else {
                    onComplete(false, task.exception?.localizedMessage ?: "Erro desconhecido ao deletar dados")
                }
            }
    }
}
