package com.itechart.identities.model

case class UserIdentityDatabase(id: String,
                                userName: String,
                                displayName: String,
                                nickName: String,
                                profileUrl: String,
                                familyName: Option[String],
                                givenName: Option[String],
                                middleName: Option[String],
                                honorificPrefix: Option[String],
                                honorificSuffix: Option[String],
                                formatted: String)

case class EmailDatabase(id: Long,
                         value: String,
                         emailType: String,
                         primary: Boolean,
                         userIdentity: String)

case class AddressDatabase(id: Long,
                           addressType: String,
                           streetAddress: String,
                           locality: String,
                           region: String,
                           postalCode: Int,
                           country: String,
                           formatted: String,
                           primary: Boolean,
                           userIdentity: String)

case class PhoneNumberDatabase(id: Long, value: String, phoneType: String, userIdentity: String)
