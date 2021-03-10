package views.authorizationViews

import controllers.AuthorizationController
import javafx.geometry.Orientation
import javafx.scene.control.TextField
import model.exceptions.IncorrectPasswordOrLoginException
import tornadofx.*
import views.ErrorView
import views.MainView

class AuthorizationView : View("Authorization") {

  private val authorizationController: AuthorizationController by inject()
  private val signInView: SignInView by inject()

  private var loginField: TextField by singleAssign()
  private var passField: TextField by singleAssign()

  override val root =
    form {
      fieldset(labelPosition = Orientation.VERTICAL) {
        field("Login") {
          textfield {
            loginField = this
          }
        }
        field("Password") {
          passwordfield {
            passField = this
          }
        }
      }

      vbox {

        button("Log in") {
          action {
            try {
              val login = authorizationController.logIn(loginField.text, passField.text)
              println(login)
              close()
              MainView().openModal(resizable = false)
            } catch (e: IncorrectPasswordOrLoginException) {
              ErrorView("Incorrect login or password").openModal(resizable = false)
            }
          }
        }

        vboxConstraints {
          marginTop = 40.0
          marginBottom = 20.0
          marginLeft = 250.0
        }
      }

      button("Sign in") {
        action {
          signInView.openModal(block = true, resizable = false)
        }

        vboxConstraints {
          marginLeft = 250.0
        }
      }
    }
}