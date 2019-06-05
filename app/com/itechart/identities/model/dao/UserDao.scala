package com.itechart.identities.model.dao

import com.google.inject.{Inject, Singleton}
import com.itechart.identities.model.{AddressDatabase, EmailDatabase, PhoneNumberDatabase, UserIdentityDatabase}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery

@Singleton
class UserDao @Inject()(protected val configProvider: DatabaseConfigProvider,
                        userIdentityDao: UserIdentityDao,
                        emailDao: EmailDao,
                        addressDao: AddressDao,
                        phoneNumberDao: PhoneNumberDao,
                       ) {
  val profile: JdbcProfile = configProvider.get.profile

  lazy val userIdentityQuery = TableQuery[userIdentityDao.UserIdentityTable]
  lazy val emailQuery = TableQuery[emailDao.EmailTable]
  lazy val addressQuery = TableQuery[addressDao.AddressTable]
  lazy val phoneNumberQuery = TableQuery[phoneNumberDao.PhoneNumberTable]

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