package com.itechart.identities.controller

import com.google.inject.Inject
import play.api.mvc._

class HelloController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def hello(): Action[AnyContent] = {
    Action { implicit request: Request[AnyContent] =>
      Ok("Hello")
    }
  }
}
