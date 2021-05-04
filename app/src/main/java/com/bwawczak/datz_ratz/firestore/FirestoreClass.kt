package com.bwawczak.datz_ratz.firestore

import android.util.Log
import com.bwawczak.datz_ratz.AddSnakeFragment
import com.bwawczak.datz_ratz.RegisterActivity
import com.bwawczak.datz_ratz.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

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

    fun addSnake(activity: AddSnakeFragment, userInfo: User) {
        mFireStore.collection("users").document(userInfo.id).collection("snakes")
            .set(userInfo, SetOptions.merge()).addOnSuccessListener {
                activity.
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