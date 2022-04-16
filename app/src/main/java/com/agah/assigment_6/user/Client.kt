package com.agah.assigment_6.user

import android.content.Context

class Client(context: Context) {

    companion object {
        private const val PREFERENCES_NAME = "User"

        private const val USER_EMAIL = "UserEmail"
        private const val USER_USERNAME = "UserUsername"
        private const val USER_GENDER = "UserGender"

        private const val REMEMBER_ME = "Done"
    }

    private val sharedPreferences by lazy {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setUserEmail(userEmail : String){
        with(sharedPreferences.edit()){
            userEmail.let {
                putString(USER_EMAIL,it)
                apply()
            }
        }
    }
    fun getUserEmail() = sharedPreferences.getString(USER_EMAIL,"")

    fun setUserUsername(userName : String){
        with(sharedPreferences.edit()){
            userName.let {
                putString(USER_USERNAME,it)
                apply()
            }
        }
    }
    fun getUserUsername() = sharedPreferences.getString(USER_USERNAME,"")

    fun setUserGender(userGender : String){
        with(sharedPreferences.edit()){
            userGender.let {
                putString(USER_GENDER,it)
                apply()
            }
        }
    }
    fun getUserGender() = sharedPreferences.getString(USER_GENDER,"")


    fun clearSharedPref(){
        with(sharedPreferences.edit()){
            clear()
            apply()
        }
    }


    fun setRememberMe(state : Boolean){
        with(sharedPreferences.edit()){
            putBoolean(REMEMBER_ME,state)
            apply()
        }
    }

    fun isRememberMe() = sharedPreferences.getBoolean(REMEMBER_ME,false)

}