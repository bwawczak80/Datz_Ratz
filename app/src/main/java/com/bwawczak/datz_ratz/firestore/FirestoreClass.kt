package com.bwawczak.datz_ratz.firestore

import android.app.Activity
import android.util.Log
import com.bwawczak.datz_ratz.LoginActivity
import com.bwawczak.datz_ratz.RegisterActivity
import com.bwawczak.datz_ratz.models.LogItem
import com.bwawczak.datz_ratz.models.Snake
import com.bwawczak.datz_ratz.models.User
import com.bwawczak.datz_ratz.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    interface Callback {
        fun onSuccess(snakes: ArrayList<Snake>?)
    }

    interface RecentLogItemCallback {
        fun onSuccess(logItems: ArrayList<LogItem>, user: User)
    }

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {

        //this will create the collection "users" the first time it is run.
        mFireStore.collection(Constants.USERS)
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

        mFireStore.collection(Constants.USERS).document(userID)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val user = documentSnapshot.toObject(User::class.java)
                    Log.d("FireStore", "user = $user")

                    val snakes: ArrayList<Snake> = ArrayList()

                    user?.snakes?.let { snakes.addAll(it) }
                    snakes.add(snake)

                    Log.d("FireStore", "snakes = $snakes")

                    mFireStore.collection(Constants.USERS).document(userID)

                        // update "snakes"
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

        mFireStore.collection(Constants.USERS).document(userID)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val user = documentSnapshot.toObject(User::class.java)
                    Log.d("FireStore", "user = $user")

                    val snakes: ArrayList<Snake> = ArrayList()

                    user?.snakes?.let { snakes.addAll(it) }

                    snakes[index] = snake


                    Log.d("FireStore", "snakes = $snakes")

                    mFireStore.collection(Constants.USERS).document(userID)


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

        mFireStore.collection(Constants.USERS).document(userID)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {

                    val user = documentSnapshot.toObject(User::class.java)
                    user?.snakes?.let { listener.onSuccess(it) }

                }
            }

    }


    fun listRecentLogItems(userID: String, listener: RecentLogItemCallback) {

        mFireStore.collection(Constants.USERS).document(userID)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {

                    val user = documentSnapshot.toObject(User::class.java)

                    val arrayRecentLogs: ArrayList<LogItem> = arrayListOf()

                    for (snake: Snake in user?.snakes!!) {
                        val recentLogItem = snake.logs.last()
                        // if snake is not in shed, add to arrayRecentLogs
                        if (!recentLogItem.isInShed) {

                            arrayRecentLogs.add(recentLogItem)

                        }
                    }

                    listener.onSuccess(arrayRecentLogs, user)
                }
            }

    }

    fun getUserDetails(activity: Activity) {
        // Pass the collection name from which we want the data
        mFireStore.collection(Constants.USERS)
            //get the document id
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())
                // Here we get the document snapshot which is converted into the User Data model object
                val user = document.toObject(User::class.java)!!
                //pass the result to the Login Activity
                when (activity) {
                    is LoginActivity -> {
                        //Call a function to transfer results
                        activity.userLoggedInSuccess(user)

                    }
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