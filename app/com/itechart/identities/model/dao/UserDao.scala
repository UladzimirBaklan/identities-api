package com.itechart.identities.model.dao

import com.google.inject.{Inject, Singleton}
import com.itechart.identities.model.{AddressDatabase, EmailDatabase, PhoneNumberDatabase, UserIdentityDatabase}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

@Singleton
class UserDao @Inject()(configProvider: DatabaseConfigProvider,
                        protected val userMapping: UserMapping) {
  val profile: JdbcProfile = configProvider.get.profile

  lazy val userIdentityQuery = userMapping.userIdentityQuery
  lazy val emailQuery = userMapping.emailQuery
  lazy val addressQuery = userMapping.addressQuery
  lazy val phoneNumberQuery = userMapping.phoneNumberQuery

  import profile.api._

  def getAllUserIdentitiesWithEmail: DBIO[Seq[(UserIdentityDatabase, EmailDatabase)]] = {
    Compiled(for {
      (uIds, email) <- userIdentityQuery join emailQuery on (_.id === _.userIdentity)
    } yield (uIds, email)).result
  }

  def getAllUserIdentitiesWithAddress: DBIO[Seq[(UserIdentityDatabase, AddressDatabase)]] = {
    Compiled(for {
      (uIds, address) <- userIdentityQuery join addressQuery on (_.id === _.userIdentity)
    } yield (uIds, address)).result
  }

  def getAllUserIdentitiesWithPhoneNumbers: DBIO[Seq[(UserIdentityDatabase, PhoneNumberDatabase)]] = {
    Compiled(for {
      (uIds, phoneNumber) <- userIdentityQuery join phoneNumberQuery on (_.id === _.userIdentity)
    } yield (uIds, phoneNumber)).result
  }
}