/*
 *
 */

import java.util.Iterator;
import java.util.Map;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * Example annotator that detects room numbers using Java 1.4 regular expressions.
 */
public class NErAnnotatorPosTag extends JCasAnnotator_ImplBase {
  private PosTagNamedEntityRecognizer mPosTagNER;

  Map<Integer, Integer> begin2end;

  /**
   * @see JCasAnnotator_ImplBase#process(JCas)
   */
  public void process(JCas aJCas) {
    // get document text
    String docText = aJCas.getDocumentText();
    String[] lines;
    String word;
    String lineindex;
    String linewoindex;
    int firstblank;

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

      begin2end = mPosTagNER.getGeneSpans(linewoindex);
      Iterator it = begin2end.entrySet().iterator();


      while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry) it.next();
        // System.out.println(pairs.getKey() + " <-> " + pairs.getValue());
        GenTag annotation = new GenTag(aJCas);
        annotation.setLineindex(lineindex);
        
        // count the number of " ".
        int beforestart, beforeend;
        beforestart = beforeend = 0;
        for (int i = 0; i < (Integer) pairs.getKey(); i++)
          if (linewoindex.charAt(i) == ' ')
            beforestart++;
        for (int i = (Integer) pairs.getKey(); i < (Integer) pairs.getValue(); i++)
          if (linewoindex.charAt(i) == ' ')
            beforeend++;
        
        word = linewoindex.substring((Integer) pairs.getKey(), (Integer) pairs.getValue());
        annotation.setBegin((Integer) pairs.getKey() - beforestart);
        annotation.setEnd((Integer) pairs.getValue() - beforestart - beforeend - 1);
        annotation.setMSofa(word);
        annotation.addToIndexes();
      }
    }
  }
}
