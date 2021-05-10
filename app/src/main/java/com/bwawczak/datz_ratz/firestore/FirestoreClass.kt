package com.bwawczak.datz_ratz.firestore

import android.util.Log
import com.bwawczak.datz_ratz.RegisterActivity
import com.bwawczak.datz_ratz.models.LogItem
import com.bwawczak.datz_ratz.models.Snake
import com.bwawczak.datz_ratz.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    interface Callback {
        fun onSuccess(snakes: ArrayList<Snake>?)
    }

    interface RecentLogItemCallback {
        fun onSuccess(logItems: ArrayList<LogItem>)
    }

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
                Log.e(activity.javaClass.simpleName, "Error while registering the user.", e)
            }
    }

    fun addSnake(userID: String, snake: Snake) {

        mFireStore.collection("users").document(userID)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    //val user = documentSnapshot.toObject<User>()
                    val user = documentSnapshot.toObject(User::class.java)
                    Log.d("FireStore", "user = $user")

                    val snakes: ArrayList<Snake> = ArrayList()// user?.snakes?.add(snake)
                    //var snakes = user?.snakes?.add(snake)
                    user?.snakes?.let { snakes.addAll(it) }
                    snakes.add(snake)

                    Log.d("FireStore", "snakes = $snakes")

                    mFireStore.collection("users").document(userID)

                        // update("snakes"
                        .update(
                            mapOf(
                                "snakes" to snakes
                            )
                        )
                        .addOnSuccessListener {
                            Log.d("FireStore", "update success =========")
                        }
                }
            }

    }

    fun updateLogItem(userID: String, index: Int, snake: Snake, listener: Callback) {

        mFireStore.collection("users").document(userID)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val user = documentSnapshot.toObject(User::class.java)
                    Log.d("FireStore", "user = $user")

                    val snakes: ArrayList<Snake> = ArrayList()

                    user?.snakes?.let { snakes.addAll(it) }

                    snakes[index] = snake


                    Log.d("FireStore", "snakes = $snakes")

                    mFireStore.collection("users").document(userID)


                        .update(
                            mapOf(
                                "snakes" to snakes
                            )
                        )
                        .addOnSuccessListener {
                            Log.d("FireStore", "update success =========")
                            listener.onSuccess(null)
                        }
                }
            }

    }


    fun listSnakes(userID: String, listener: Callback) {

        mFireStore.collection("users").document(userID)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {

                    val user = documentSnapshot.toObject(User::class.java)
                    user?.snakes?.let { listener.onSuccess(it) }

                }
            }

    }


    fun listRecentLogItems(userID: String, listener: RecentLogItemCallback) {

        mFireStore.collection("users").document(userID)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {

                    val user = documentSnapshot.toObject(User::class.java)

                    val arrayRecentLogs: ArrayList<LogItem> = arrayListOf()

                    for (snake: Snake in user?.snakes!!) {
                        val recentLogItem = snake.logs.last()
                        arrayRecentLogs.add(recentLogItem)
                    }
                    listener.onSuccess(arrayRecentLogs)
                }
            }

    }


    // get CurrentUser from Authentication module
    fun getCurrentUserId(): String {

        //gets an instance of the currentUser
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }


}