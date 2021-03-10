import javafx.stage.Stage
import model.db.DataBaseConnection
import tornadofx.*
import views.MainView
import views.authorizationViews.AuthorizationView

class Application : App(AuthorizationView::class, Styles::class) {
  override fun start(stage: Stage) {
    with(stage) {
//      minWidth = 600.0
//      minHeight = 400.0
//      isResizable = false
      super.start(this)
    }
    System.getProperties().setProperty("oracle.jdbc.J2EE13Compliant", "true")
  }
}

class Styles : Stylesheet() {
  init {
    root {

    }

    fieldset {
//      backgroundColor += Color.RED
      maxHeight = Dimension<Dimension.LinearUnits>(200.0, Dimension.LinearUnits.px)
    }
  }
}