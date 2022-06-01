package com.example.CWWPRO.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {
    private val firestoreDB: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getSavedDam(): CollectionReference {
        var collectionReference = firestoreDB.collection("Dam")
        return collectionReference
    }

    fun getSavedRain(): CollectionReference {
        var collectionReference = firestoreDB.collection("Rain")
        return collectionReference
    }
    fun getSavedModel(): CollectionReference {
        var collectionReference = firestoreDB.collection("Model")
        return collectionReference
    }
    fun getSavedMap(): CollectionReference {
        var collectionReference = firestoreDB.collection("Map")
        return collectionReference
    }
}