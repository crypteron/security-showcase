package com.crypteron.showcase.controller;

import java.security.Security;

import javax.ws.rs.ApplicationPath;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/java")
public class ShowcaseController extends ResourceConfig {
  public ShowcaseController() {
    Security.addProvider(new BouncyCastleProvider());
    packages(this.getClass().getPackage().toString());
  }
}
