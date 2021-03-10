package views.authorizationViews

import controllers.AuthorizationController
import javafx.geometry.Orientation
import javafx.scene.control.TextField
import model.exceptions.DifferentPasswordException
import tornadofx.*
import views.ErrorView
import java.sql.SQLIntegrityConstraintViolationException

class SignInView : View("Sign in") {

  private var loginField: TextField by singleAssign()
  private var passField: TextField by singleAssign()
  private var repeatPassField: TextField by singleAssign()

  private val authorizationController: AuthorizationController by inject()

  override val root = form {
    fieldset(labelPosition = Orientation.VERTICAL) {
      field("New login") {
        textfield {
          loginField = this
        }
      }

      field("New password") {
        passwordfield {
          passField = this
        }
      }

      field("Repeat password") {
        passwordfield {
          repeatPassField = this
        }
      }

    }

    vbox {

      button("Sign in") {
        action {
          try {
            authorizationController.signIn(loginField.text, passField.text, repeatPassField.text)
            close()
          } catch (e: DifferentPasswordException) {
            ErrorView("You need to input the same passwords").openModal(resizable = false)

          } catch (e: SQLIntegrityConstraintViolationException) {
            ErrorView("This login is already used").openModal(resizable = false)
          }

          vboxConstraints {
            marginLeft = 250.0
          }
        }
      }
    }
  }
}
