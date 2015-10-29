package com.crypteron.scala.generic.scalatra

import java.util.ServiceLoader
import javax.servlet.{ ServletContext, ServletContextEvent, ServletContextListener }
import javax.servlet.annotation.WebListener
import java.security.Security
import scala.collection.JavaConversions._
import org.scalatra._
import org.scalatra.servlet.ScalatraListener
import org.bouncycastle.jce.provider.BouncyCastleProvider
import com.crypteron.scala.generic.controller.EntityController

@WebListener
class ScalatraBootstrap extends ServletContextListener with LifeCycle {
  val scalatraListener = new ScalatraListener

  // Using this in place of web.xml to bootstrap Scalatra
  override def contextInitialized(sce: ServletContextEvent): Unit = {
    // this will set ourselves as the lifecycle class to call back to
    sce.getServletContext.setInitParameter(ScalatraListener.LifeCycleKey, this.getClass.getName)
    // kickoff Scalatra init
    scalatraListener.contextInitialized(sce)
  }

  private def entityControllers = ServiceLoader.load(classOf[EntityController[_, _]]).iterator.toList

  // Defined in LifeCycle trait
  // Scalatra will call this after it is done with init
  override def init(context: ServletContext) {
    Security.addProvider(new BouncyCastleProvider)
    for (controller <- entityControllers) context.mount(handler = controller, urlPattern = controller.urlPattern)
  }

  override def contextDestroyed(sce: ServletContextEvent): Unit = scalatraListener.contextDestroyed(sce)
}
