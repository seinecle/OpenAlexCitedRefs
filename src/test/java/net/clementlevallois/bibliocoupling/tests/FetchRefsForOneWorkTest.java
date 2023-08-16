/*
 * Copyright Clement Levallois 2021-2023. License Attribution 4.0 Intertnational (CC BY 4.0)
 */
package net.clementlevallois.bibliocoupling.tests;

import java.io.IOException;
import net.clementlevallois.bibliocoupling.controller.BiblioCoupling;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author LEVALLOIS
 */
public class FetchRefsForOneWorkTest {
 
    
    
    @Test
    public void testFetchingRefs() throws IOException, InterruptedException{
        
        boolean hasInternetAccess = java.net.InetAddress.getByName("www.google.com").isReachable(1000);
        
        if (!hasInternetAccess){
            System.out.println("test skipped because there is no Internet access");
            return;
        }

        
        String articleTitle = "Hello Brand let's take a selfie";
        
        BiblioCoupling bib = new BiblioCoupling();
        String refs = bib.getCommaSeparatedCitedRefsForOneWorkViaTitle(articleTitle);
        if (refs.isBlank()){
            articleTitle = articleTitle.replaceAll("'", "’");
            refs = bib.getCommaSeparatedCitedRefsForOneWorkViaTitle(articleTitle);
        }
        refs = refs.replaceAll(",",System.lineSeparator());
        Assert.assertTrue(!refs.isBlank());

        String doi = "https://doi-org.em-lyon.idm.oclc.org/10.1016/j.ecolecon.2010.06.020";
        
        refs = bib.getCommaSeparatedCitedRefsForOneWorkViaDOI(doi);
        refs = refs.replaceAll(",",System.lineSeparator());
        Assert.assertTrue(!refs.isBlank());
    }
}
