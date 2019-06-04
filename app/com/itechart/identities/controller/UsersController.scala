package com.itechart.identities.controller

import com.google.inject.Inject
import com.itechart.identities.model.dao.{AddressDao, EmailDao, UserIdentityDao}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

class UsersController @Inject()(cc: ControllerComponents,
                                dao: AddressDao,
                                protected val dbConfigProvider: DatabaseConfigProvider)
                               (implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  def getUsers(): Action[AnyContent] = Action.async {
    implicit request => {
      val future = db.run(dao.all())

      future.map(names => Ok(Json.toJson(names)))
    }
  }
}
