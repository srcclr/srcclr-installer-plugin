/*
 * Copyright (c) 2016 -  SourceClear Inc, with original code (c) by Merkushev Kirill
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

import jenkins.util.JSONSignatureValidator;
import org.apache.tools.ant.filters.StringInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExtendedCertJsonSignValidator extends JSONSignatureValidator {

  private static final Logger LOGGER = Logger.getLogger(ExtendedCertJsonSignValidator.class.getName());

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private String cert;

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  

  public ExtendedCertJsonSignValidator(String id, String cert) {
    super(String.format("Update site for %s", id));
    this.cert = cert;
  }

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //------------------------ Implements:

  //------------------------ Overrides: JSONSignatureValidator

  @Override
  protected Set<TrustAnchor> loadTrustAnchors(CertificateFactory cf) throws IOException {

    Set<TrustAnchor> trustAnchors = super.loadTrustAnchors(cf);

    try (InputStream stream = new StringInputStream(cert)){
      Certificate certificate = cf.generateCertificate(stream);
      trustAnchors.add(new TrustAnchor((X509Certificate) certificate, null));
    } catch (CertificateException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }

    return trustAnchors;
  }

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------

}
