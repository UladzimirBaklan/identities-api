package com.itechart.identities.controller.services

import com.google.inject.Inject
import com.itechart.identities.model.{AddressDatabase, EmailDatabase, PhoneNumberDatabase, User, UserIdentityDatabase}
import com.itechart.identities.model.dao._
import play.api.db.slick.DatabaseConfigProvider
import slick.dbio.Effect
import slick.jdbc.JdbcProfile
import slick.sql.FixedSqlStreamingAction

import scala.concurrent.{ExecutionContext, Future}

class UsersService @Inject()(configProvider: DatabaseConfigProvider,
                             userDao: UserDao)(implicit ec: ExecutionContext) {
  private lazy val profile: JdbcProfile = configProvider.get.profile

  import profile.api._

  def getAllUsers(): Future[Seq[User]] = {
    val query = for{
      (ids, emails) <- userDao.userIdentityQuery join userDao.emailQuery on (_.id === _.userIdentity)
      (ids, numbers) <- userDao.userIdentityQuery join userDao.phoneNumberQuery on (_.id === _.userIdentity)
      (ids, addresses) <- userDao.userIdentityQuery join userDao.addressQuery on (_.id === _.userIdentity)
    } yield (ids, emails, numbers, addresses)

    val compiledQuery: DBIO[Seq[(UserIdentityDatabase, EmailDatabase, PhoneNumberDatabase, AddressDatabase)]] = Compiled(query).result

    configProvider.get.db.run(compiledQuery).map(future => future.map(data => User(data)))
//    val query = (for {
//      idsWithEmails <- userDao.getAllUserIdentitiesWithEmail
//      idsWithNumbers <- userDao.getAllUserIdentitiesWithPhoneNumbers
//      idsWithAddresses <- userDao.getAllUserIdentitiesWithAddress
//    } yield (idsWithEmails, idsWithNumbers, idsWithAddresses)).transactionally
//
//    configProvider.get.db.run(query).map(tuples => {
//
//    })
  }
}
