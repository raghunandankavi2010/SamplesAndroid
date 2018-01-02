package com.ecommerce.ctdib2brecommerce.utils

/**
 * Created by raghu on 1/1/18.
 */

import java.util.regex.Matcher
import java.util.regex.Pattern

class Utility {


    companion object {

        private var pattern: Pattern? = null
        private var matcher: Matcher? = null

        //Email Pattern
        private val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        private val NAME_PATTERN = "[a-zA-Z]+"

        /*
    (			# Start of group
        (?=.*\d)		#   must contains one digit from 0-9
        (?=.*[a-z])		#   must contains one lowercase characters
        (?=.*[A-Z])		#   must contains one uppercase characters
        (?=.*[@#$%])		#   must contains one special symbols in the list "@#$%"
              .		#     match anything with previous condition checking
                {6,20}	#        length at least 6 characters and maximum of 20
    )			# End of group
     */
        private val PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})"

        private val MOBILENUMBER_PATTERN = "([0-9]{10})"

        /*
     * Validate the username with a regex email
     *
     * @param : email address entered in the login / Register screen
     * returns true if it is a valid email address else false     *
     */
        fun validateEmailRegex(email: String): Boolean {
            pattern = Pattern.compile(EMAIL_PATTERN)
            matcher = pattern!!.matcher(email)
            return matcher!!.matches()
        }

        fun validateNameRegex(name: String): Boolean {
            pattern = Pattern.compile(NAME_PATTERN)
            matcher = pattern!!.matcher(name)
            return matcher!!.matches()
        }


        fun validatePhoneNumber(phonenumber: String): Boolean {
            pattern = Pattern.compile(MOBILENUMBER_PATTERN)
            matcher = pattern!!.matcher(phonenumber)
            return matcher!!.matches()
        }


        fun validatePasswordRegex(name: String): Boolean {
            pattern = Pattern.compile(PASSWORD_PATTERN)
            matcher = pattern!!.matcher(name)
            return matcher!!.matches()
        }


    }
}
