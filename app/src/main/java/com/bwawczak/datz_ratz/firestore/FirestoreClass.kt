package com.bwawczak.datz_ratz.firestore

import android.util.Log
import com.bwawczak.datz_ratz.AddSnakeFragment
import com.bwawczak.datz_ratz.RegisterActivity
import com.bwawczak.datz_ratz.models.Snake
import com.bwawczak.datz_ratz.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject

class FirestoreClass {


    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {

        //this will create the collection "users" the first time it is run.
        mFireStore.collection("users")
        //Document ID for users fields.
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                activity.userRegistrationSuccess()

            }
            .addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName,"Error while registering the user.",e)
            }
    }

    fun addSnake(userID: String, snake: Snake) {

        mFireStore.collection("users").document(userID)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    //val user = documentSnapshot.toObject<User>()
                    val user = documentSnapshot.toObject(User::class.java)
                    Log.d("FireStore", "user = ${user}")

                    var snakes : ArrayList<Snake> = ArrayList()// user?.snakes?.add(snake)
                    //var snakes = user?.snakes?.add(snake)
                    user?.snakes?.let { snakes.addAll(it) }
                    snakes.add(snake)

                    Log.d("FireStore", "snakes = ${snakes}")

                    mFireStore.collection("users").document(userID)
                        // here should update "users" to add snake rather than update "snakes"
                        //.update("snakes", FieldValue.arrayUnion(snakes))
                        .update(mapOf(
                            "snakes" to snakes
                        ))
                        .addOnSuccessListener {
                            Log.d("FireStore", "update success =========")
                        }
                }
            }

    }

    interface Callback {
        fun onSuccess(snakes:  ArrayList<Snake>)
    }

    fun listSnakes(userID: String, listener : Callback) {

        mFireStore.collection("users").document(userID)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {

                    val user = documentSnapshot.toObject(User::class.java)
                    user?.snakes?.let { listener.onSuccess(it) }

                }
            }

    }




// get CurrentUser from Authentication module
fun getCurrentUserId(): String {
    //gets and instance of the currentUser
    val currentUser = FirebaseAuth.getInstance().currentUser

    var currentUserID = ""
    if (currentUser != null) {
        currentUserID = currentUser.uid
    }

    return currentUserID
}


}