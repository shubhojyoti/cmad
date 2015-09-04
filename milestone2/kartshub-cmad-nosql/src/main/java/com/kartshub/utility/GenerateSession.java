package com.kartshub.utility;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class GenerateSession {
  private SecureRandom random = new SecureRandom();

  public String nextSessionId() {
    return new BigInteger(130, random).toString(32);
  }
}

