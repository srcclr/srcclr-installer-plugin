/*
 * Copyright (c) 2016 -  SourceClear Inc
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.srcclr.jenkins.installer;

import hudson.model.UpdateSite;
import jenkins.util.JSONSignatureValidator;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;

public class SrcclrUpdateSite extends UpdateSite {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private static String tempCert = null;

  static {
    try {
      tempCert = IOUtils.toString(SrcclrUpdateSite.class.getResourceAsStream("/srcclr-jenkins-update-site-ca.crt"));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  //
  // If no other values are provided, the defaults are used. These are primed for the internal QA environment.
  //
  //

  private static final String DEFAULT_UPDATE_SITE_ID = "srcclr-jenkins-update";

  private static final String DEFAULT_UPDATE_SITE_URL = "https://jenkins.srcclr.com/update-center.json";

  private final static String DEFAULT_SRCCLR_CERT = tempCert;

  private static final String ENV_UPDATE_SITE_CA = "SRCCLR-JENKINS-UPDATE-SITE-CA";

  private static final String ENV_UPDATE_SITE_ID = "SRCCLR-JENKINS-UPDATE-SITE-ID";

  private static final String ENV_UPDATE_SITE_URL = "SRCCLR-JENKINS-UPDATE-SITE-URL";


  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private static String getSiteCert() {
    String cert = System.getenv(ENV_UPDATE_SITE_CA);
    return cert != null ? cert : DEFAULT_SRCCLR_CERT;
  }

  private static String getSiteId() {
    String id = System.getenv(ENV_UPDATE_SITE_ID);
    return id != null ? id : DEFAULT_UPDATE_SITE_ID;
  }

  private static String getSiteUrl() {
    String url = System.getenv(ENV_UPDATE_SITE_URL);
    return url != null ? url : DEFAULT_UPDATE_SITE_URL;
  }

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  

  public SrcclrUpdateSite() {
    super(getSiteId(), getSiteUrl());
  }

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //------------------------ Implements:

  //------------------------ Overrides: UpdateSite

  /**
   * Verifier for the signature of downloaded update-center.json.
   *
   * @return JSONSignatureValidator object with additional cert as anchor if enabled
   */
  @Nonnull
  @Override
  protected JSONSignatureValidator getJsonSignatureValidator()
  {
    return new ExtendedCertJsonSignValidator(getId(), getSiteCert());
  }


  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------

}
