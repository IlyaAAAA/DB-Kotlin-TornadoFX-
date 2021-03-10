package controllers

import model.Authorization
import model.exceptions.DifferentPasswordException
import tornadofx.Controller

class AuthorizationController : Controller() {

  private val authorization = Authorization()

  fun logIn(login: String, password: String): String {
    return authorization.logIn(login, password)
  }

  fun signIn(login: String, password: String, repeatPass: String) {
    if (checkPasswords(password, repeatPass)) {
      authorization.signIn(login, password)
    } else {
      throw DifferentPasswordException("Different passwords")
    }
  }

  private fun checkPasswords(password: String, repeatPass: String) = password.compareTo(repeatPass) == 0
}