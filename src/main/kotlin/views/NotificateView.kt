package views

import tornadofx.*

class NotificateView(message: String) : Fragment("Notification") {

  override val root = borderpane {

    minWidth = 350.0
    minHeight = 150.0

    center = text(message)

    bottom = vbox {
      button("Ok") {
        vboxConstraints {
          marginLeft = 160.0
          marginBottom = 20.0
        }

        action {
          close()
        }
      }
    }
  }
}
