package br.com.fiap.firebase

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseStorageHelper {

    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    // Função para fazer upload de arquivos para o Firebase Storage
    fun uploadFile(path: String, fileUri: Uri, onComplete: (Boolean, String?) -> Unit) {
        val storageRef: StorageReference = storage.reference.child(path)
        storageRef.putFile(fileUri)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.storage?.downloadUrl?.addOnSuccessListener { uri ->
                        onComplete(true, uri.toString())
                    }
                } else {
                    onComplete(false, task.exception?.localizedMessage)
                }
            }
    }

    // Função para baixar arquivos do Firebase Storage
    fun downloadFile(path: String, onComplete: (Uri?, String?) -> Unit) {
        val storageRef: StorageReference = storage.reference.child(path)
        storageRef.downloadUrl
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(task.result, null)
                } else {
                    onComplete(null, task.exception?.localizedMessage)
                }
            }
    }

    // Função para deletar um arquivo do Firebase Storage
    fun deleteFile(path: String, onComplete: (Boolean, String?) -> Unit) {
        val storageRef: StorageReference = storage.reference.child(path)
        storageRef.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Arquivo deletado com sucesso")
                } else {
                    onComplete(false, task.exception?.localizedMessage)
                }
            }
    }
}
