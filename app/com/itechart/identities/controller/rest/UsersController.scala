package com.itechart.identities.controller.rest

import com.google.inject.Inject
import com.itechart.identities.controller.services.UsersService
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

class UsersController @Inject()(cc: ControllerComponents,
                                controller: UsersService,
                                protected val dbConfigProvider: DatabaseConfigProvider)
                               (implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  def getUsers(): Action[AnyContent] = Action.async {
    implicit request => {
      val future = controller.getAllUsers()

      future.map(users => Ok(Json.toJson(users)))
    }
  }
}
