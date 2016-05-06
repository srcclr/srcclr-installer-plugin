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

import com.google.common.collect.Lists;
import hudson.Plugin;
import hudson.model.UpdateCenter;
import hudson.model.UpdateSite;
import jenkins.model.Jenkins;

import java.util.List;

public class SrcclrUpdateSitePlugin extends Plugin {


  @Override
  public void postInitialize() throws Exception {
    Jenkins jenkins = Jenkins.getInstance();
    if (jenkins == null) {
      throw new RuntimeException("Jenkins not found");
    }
    UpdateCenter updateCenter = jenkins.getUpdateCenter();
    List<UpdateSite> sites = Lists.newArrayList(updateCenter.getSites());
    sites.add(new SrcclrUpdateSite());

    updateCenter.getSites().replaceBy(sites);
  }

}
