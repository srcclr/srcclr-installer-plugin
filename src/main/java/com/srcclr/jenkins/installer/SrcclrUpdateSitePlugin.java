/*
 * Copyright (c) 2016 -  SourceClear Inc
 */

package com.srcclr.jenkins.installer;

import com.google.common.collect.Lists;
import hudson.Plugin;
import hudson.model.UpdateCenter;
import hudson.model.UpdateSite;
import jenkins.model.Jenkins;

import java.util.List;

public class SrcclrUpdateSitePlugin extends Plugin {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //------------------------ Implements:

  //------------------------ Overrides: Plugin

  public void postInitialize() throws Exception {
    UpdateCenter updateCenter = Jenkins.getInstance().getUpdateCenter();
    List<UpdateSite> sites = Lists.newArrayList(updateCenter.getSites());
    sites.add(new SrcclrUpdateSite());

    updateCenter.getSites().replaceBy(sites);
  }

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------

}
