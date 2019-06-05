package com.itechart.identities

import com.google.inject.{Guice, Injector}

object Injector {
  lazy val injector: Injector = Guice.createInjector(new Module)
}
