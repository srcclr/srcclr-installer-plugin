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

import com.google.common.collect.ImmutableMap;
import hudson.model.UpdateSite;
import jenkins.util.JSONSignatureValidator;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import java.util.Properties;

public class SrcclrUpdateSite extends UpdateSite {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


  private static final ImmutableMap.Builder<String, String> BUILDER = new ImmutableMap.Builder<>();

  static {
    try {
      Properties p = new Properties();
      p.load(SrcclrUpdateSite.class.getResourceAsStream("/site.properties"));
      for (String key : p.stringPropertyNames()) {
        BUILDER.put(key, p.getProperty(key));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private static final ImmutableMap<String, String> PROPERTIES = BUILDER.build();

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private static String getSiteCert() {
    return PROPERTIES.get("srcclr.site.ca");
  }

  private static String getSiteId() {
    return PROPERTIES.get("srcclr.site.id");
  }

  private static String getSiteUrl() {
    return PROPERTIES.get("srcclr.site.url");
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
