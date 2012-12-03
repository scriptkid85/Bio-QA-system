/*
 *  Wittren by Guanyu Wang, andrew ID: guanyuw
 *  For homework 1 of 11791
 */

import java.io.File;
import java.io.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;
import com.aliasi.util.AbstractExternalizable;

/**
 * The Name Entity Recognizer. It use the PosTagNamedEntityRecognizer first to tokinize all texts,
 * then adopts the GeneRuler to judge all these tokens are gene mentions or not. At the same time,
 * it also uses the Lingpipe to do the tagging task. Finally aggregate results from both sides.
 */

public class NErAnnotator extends JCasAnnotator_ImplBase {
  
  private InputStream ois = null;
  //URL url = this.getClass().getClassLoader().getResource("Lingpipe/ne-en-bio-genetag.HmmChunker");
  
  private Chunker chunker;
  
  private File modelFile = new File("Lingpipe/ne-en-bio-genetag.HmmChunker");

  private PosTagNamedEntityRecognizer mPosTagNER;

  private GeneRuler mRuler;

  Map<Integer, Integer> begin2end;

  /**
   * @see JCasAnnotator_ImplBase#process(JCas)
   */
  public void process(JCas aJCas) {
    // get document text
    ois = this.getClass().getClassLoader().getResourceAsStream("Lingpipe/ne-en-bio-genetag.HmmChunker");
 //   System.out.println(this.getClass().getClassLoader().getResource("Lingpipe/ne-en-bio-genetag.HmmChunker"));
    String docText = aJCas.getDocumentText();
    String[] lines;
    String word;
    String lineindex;
    String linewoindex;
    ArrayList<String> wordsoneline = new ArrayList<String>();
    int firstblank;
    mRuler = new GeneRuler();
    Chunk chunk;

    try {
      chunker = (Chunker) AbstractExternalizable.readObject(modelFile);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    try {
      mPosTagNER = new PosTagNamedEntityRecognizer();
    } catch (ResourceInitializationException e) {
      e.printStackTrace();
    }

    lines = docText.split("\n");
    for (String s : lines) {
      firstblank = s.indexOf(" ");
      lineindex = s.substring(0, firstblank);
      linewoindex = s.substring(firstblank + 1, s.length());

      Chunking chunking = chunker.chunk(linewoindex);
      Set<Chunk> chunkresult = chunking.chunkSet();
      Iterator<Chunk> Cit = chunkresult.iterator();

      // count the number of " ".
      int parsepos, beforestart, beforeend;
      parsepos = beforestart = beforeend = 0;
      while (Cit.hasNext()) {
        chunk = Cit.next();
        GenTag annotation = new GenTag(aJCas);
        annotation.setLineindex(lineindex);
        for (int i = parsepos; i < chunk.start(); i++)
          if (linewoindex.charAt(i) == ' ')
            beforestart++;
        for (int i = chunk.start(); i < chunk.end(); i++)
          if (linewoindex.charAt(i) == ' ')
            beforeend++;
        parsepos = chunk.end();
        word = linewoindex.substring(chunk.start(), chunk.end());
        wordsoneline.add(word);

        // compute the start and end value
        annotation.setBegin(chunk.start() - beforestart);
        annotation.setEnd(chunk.end() - beforestart - beforeend - 1);
        annotation.setMSofa(word);
        annotation.addToIndexes();

        beforestart = beforestart + beforeend;
        beforeend = 0;
      }

      begin2end = mPosTagNER.getGeneSpans(linewoindex);
      Iterator it = begin2end.entrySet().iterator();

      while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry) it.next();
        // System.out.println(pairs.getKey() + " <-> " + pairs.getValue());
        word = linewoindex.substring((Integer) pairs.getKey(), (Integer) pairs.getValue());
        if (!wordsoneline.contains(word)) {
          if (mRuler.GeneTest(word)) {
            GenTag annotation = new GenTag(aJCas);
            annotation.setLineindex(lineindex);

            // count the number of " ".
            int beforestart2, beforeend2;
            beforestart2 = beforeend2 = 0;
            for (int i = 0; i < (Integer) pairs.getKey(); i++)
              if (linewoindex.charAt(i) == ' ')
                beforestart2++;
            for (int i = (Integer) pairs.getKey(); i < (Integer) pairs.getValue(); i++)
              if (linewoindex.charAt(i) == ' ')
                beforeend2++;

            annotation.setBegin((Integer) pairs.getKey() - beforestart2);
            annotation.setEnd((Integer) pairs.getValue() - beforestart2 - beforeend2 - 1);
            annotation.setMSofa(word);
            annotation.addToIndexes();
          }
        }
      }
    }
    wordsoneline.clear();
  }
}
