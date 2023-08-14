/*
 * Copyright Clement Levallois 2021-2023. License Attribution 4.0 Intertnational (CC BY 4.0)
 */
package net.clementlevallois.bibliocoupling.tests;

import java.io.IOException;
import net.clementlevallois.bibliocoupling.controller.BiblioCoupling;
import org.junit.Test;

/**
 *
 * @author LEVALLOIS
 */
public class FetchRefsForOneWorkTest {
 
    
    
    @Test
    public void testFetchingRefs() throws IOException, InterruptedException{
        String articleTitle = "Yahoo! for Amazon: Sentiment Extraction from Small Talk on the Web";
        
        BiblioCoupling bib = new BiblioCoupling();
        String refs = bib.getCommaSeparatedCitedRefsForOneWork(articleTitle);
        
        refs = refs.replaceAll(",",System.lineSeparator());
        
        System.out.println("refs:");
        System.out.println(refs);
    }
}
